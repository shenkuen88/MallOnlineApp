package cn.nj.www.my_module.network;


import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.URLUtil;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.tools.ACache;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.klog.KLog;
import de.greenrobot.event.EventBus;

/**
 * <网络工具类> <功能详细描述>
 *
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NetWork {
    private static final String CHARSET = "UTF-8";

    private static final String TAG = "Http";

    private static ExecutorService mExecutor = Executors.newCachedThreadPool();

    private HttpTask mRequestHandle;

    private static ConcurrentLinkedQueue<HttpTask> mPendingTasks = new ConcurrentLinkedQueue<HttpTask>();
    private static Context mContext = null;

    //当需要缓存时,给Context赋值
    public NetWork needCache(Context context) {
        this.mContext = context;
        return this;
    }

    public NetWork() {
        mContext = null;
    }

    /**
     * 取消当前网络网络请求
     */
    public final void cancel() {
        if (null != mRequestHandle) {
            mRequestHandle.cancel();
        }
    }

    public synchronized static void shutdown() {
        if (mExecutor != null) {
            if (!mExecutor.isShutdown()) {
                mExecutor.shutdown();
            }
            mExecutor = null;
        }
    }

    /**
     * <网络层交互 纯文本> <功能详细描述>
     */
    public void startPost(final String url, final Map<String, String> map, String tag) {
        HttpTask task = new HttpTask(tag, url, map, null);
        mRequestHandle = task;
        mPendingTasks.offer(task);
        task.mFuture = mExecutor.submit(task);
    }
    /**
     * <网络层交互 纯文本> <功能详细描述>
     */
    public void startPost2(final String url, final Map<String, Object> map, String tag) {
        HttpTask task = new HttpTask(tag, url, map);
        mRequestHandle = task;
        mPendingTasks.offer(task);
        task.mFuture = mExecutor.submit(task);
    }
    /**
     * <网络层交互 文件上传> <功能详细描述>
     */
    public void startPost(final String url, final Map<String, String> map,
                          final Map<String, List<File>> fileParameters, String tag) {
        HttpTask task = new HttpTask(tag, url, map, fileParameters);
        mRequestHandle = task;
        mPendingTasks.offer(task);
        task.mFuture = mExecutor.submit(task);
    }

    /**
     * <网络层交互 崩溃日志文件上传-永远在后台>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void startPostForCrash(final String url, final Map<String, String> map,
                                  final Map<String, List<File>> fileParameters, String tag) {
        HttpTask task = new HttpTask(tag, url, map, fileParameters);
        task.mFuture = mExecutor.submit(task);
    }

    private static final class HttpTask implements Runnable {

        private String tag;

        private Map<String, String> mParamMap;

        private String mUri;

        private Future<?> mFuture;

        private DefaultHttpClient defaultHttpClient;

        private HttpPost httpPost;

        private Map<String, List<File>> mfileParameters;
        private Map<String, Object> objectParamMap;

        private HttpTask(String tag, String uri, Map<String, String> paramMap, Map<String, List<File>> fileParameters) {
            this.tag = tag;
            mUri = uri;
            mParamMap = paramMap;
            mfileParameters = fileParameters;
        }
        private HttpTask(String tag, String uri, Map<String, Object> objectMap) {
            this.tag = tag;
            mUri = uri;
            objectParamMap = objectMap;
        }
        private void cancel() {
            if (null != mFuture) {
                mFuture.cancel(true);
            }

            if (null != defaultHttpClient) {
                defaultHttpClient.getConnectionManager().closeExpiredConnections();
                defaultHttpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
            }
        }

        @Override
        public void run() {
            String result = null;
            try {
                if (mContext != null) {
                    ACache mCache = ACache.get(mContext);
                    if (GeneralUtils.isNullOrZeroLenght(result)) {
                        result = mCache.getAsString(mUri + ":" + GsonHelper.toJson(mParamMap));
                        CMLog.e("hq",mUri + ":" + GsonHelper.toJson(mParamMap));
                        if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                            EventBus.getDefault().post(new NetResponseEvent(result, tag, NetResponseEvent.Cache.isCache));
                        }
                    }
                }
                if(objectParamMap!=null){
                    result= doPost2(mUri, objectParamMap, mfileParameters);
                }else{
                    result = doPost(mUri, mParamMap, mfileParameters);
                }

            } catch (ConnectTimeoutException e) {
                CMLog.i(TAG, "ConnectTimeoutException" + e.getMessage());
            } catch (IOException e) {
                CMLog.i(TAG, "IOException " + e.getMessage());
            } catch (RuntimeException e) {
                CMLog.i(TAG, "RuntimeException " + e.getMessage());
            } catch (InterruptedException e) {
                CMLog.i(TAG, "InterruptedException " + e.getMessage());
            } finally {
                mPendingTasks.remove(this);
                //出现过  IOException Connection to http://221.226.118.110:18080 refused, 调试发现根本没有通知到社区页面，所以一直显示加载页
                if (result == null || result.equals("")) {
                    EventBus.getDefault().post(new NetResponseEvent("", tag, NetResponseEvent.Cache.isNetWork));
                } else if (!Thread.interrupted()) {//线程未被中断
                    EventBus.getDefault().post(new NetResponseEvent(result, tag, NetResponseEvent.Cache.isNetWork));
                }
            }
        }

        /**
         * <网络请求> <功能详细描述>
         *
         * @param url
         * @param map
         * @return
         * @see [类、类#方法、类#成员]
         */
        private String doPost(String url, Map<String, String> map, Map<String, List<File>> fileParameters)
                throws ConnectTimeoutException, IOException, InterruptedException {
            CMLog.i(TAG, "参数名     " + url);
            try {
                //                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(url);
                HttpParams httpParams = new BasicHttpParams();
                HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(httpParams, CHARSET);
                HttpProtocolParams.setUseExpectContinue(httpParams, false);

                HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);
                httpPost.setParams(httpParams);
                //设备类型ID,由服务端为每类客户端分配不同的设备类型ID及相应的私有密码。
                httpPost.setHeader("x-s-deviceID", BaseApplication.DEVICE_TOKEN);
                //服务端分配的设备请求密码。需要使用SHA256算法进行加密传输。
                httpPost.setHeader("x-s-x-s-password", Global.getXS_PASSWORD_WORD());
                String pass = Global.getXS_PASSWORD_WORD();
                //时间戳
                httpPost.setHeader("x-s-timestamp", GeneralUtils.formatDate(new Date(), GeneralUtils.DATE_PATTERN));
                String time = GeneralUtils.formatDate(new Date(), GeneralUtils.DATE_PATTERN);
                //校验码
                httpPost.setHeader("x-s-security", Global.getXSSecurity());
                String se = Global.getXSSecurity();
                //登录账户
                httpPost.setHeader("x-s-loginName",Global.getUserName());
                //客户端版本号
                httpPost.setHeader("x-s-clientVersion", ""+ Constants.VERSION_NAME);
                String ver = Constants.VERSION_NAME+"";
                SchemeRegistry schReg = new SchemeRegistry();
                schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(httpParams, schReg);
                defaultHttpClient = new DefaultHttpClient(conMgr, httpParams);
                SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());

                if (GeneralUtils.isNotNull(BaseApplication.cookieStore)) {
                    defaultHttpClient.setCookieStore(BaseApplication.cookieStore);
                }

//                if (fileParameters != null && fileParameters.size() > 0)
//                {
//                    MultipartEntity multipartEntity =
//                            new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
//                    if (map != null)
//                    {
//                        String hashValue = "";
//                        ArrayList<String> keys = GeneralUtils.doSort(map);
//                        for (int i = 0; i < keys.size(); i++)
//                        {
//                            if ("hash".equals(keys.get(i)))
//                            {
//                                continue;
//                            }
//                            String key = keys.get(i);
//                            String value = map.get(keys.get(i));
//                            CMLog.i(TAG, "参数名     " + key);
//                            CMLog.i(TAG, "参数值     " + value);
//                            BasicNameValuePair valuePair = new BasicNameValuePair(key, value);
//                            StringBody strBody =
//                                    new StringBody(String.valueOf(valuePair.getValue()), Charset.forName("UTF-8"));
//                            multipartEntity.addPart(valuePair.getName(), strBody);
//                            hashValue += value;
//                        }

//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash",GsonHelper.toJson(map));
//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash", SecurityUtils.get32MD5Str(hashValue));
//                        CMLog.i(TAG, "hash " + "加密前:" + GsonHelper.toJson(map));
//                        StringBody strBody =
//                                new StringBody(String.valueOf(valuePair.getValue()), Charset.forName("UTF-8"));
//                        multipartEntity.addPart(valuePair.getName(), strBody);
//                    }

//                    String fileExtension = null;
//                    String mineType = null;
//                    for (Map.Entry<String, List<File>> entry : fileParameters.entrySet())
//                    {
//                        List<File> files = entry.getValue();
//                        for (File file : files)
//                        {
//                            fileExtension = FileExtensionUtil.getFileExtensionFromName(file.getName());
//                            mineType = MimeTypeUtil.getSingleton().getMimeTypeFromExtension(fileExtension);
//                            if (StringUtil.isNotEmpty(mineType))
//                            {
//                                multipartEntity.addPart(entry.getKey(), new FileBody(file, mineType)); // 添加文件参数,带上mimeType
//                            } else
//                            {
//                                multipartEntity.addPart(entry.getKey(), new FileBody(file)); // 添加文件参数
//                            }
//                        }
//                    }
//                    httpPost.setEntity(multipartEntity);
//
//                } else
//                {
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//                    List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
//                    if (map != null)
//                    {
//                        String hashValue = "";
//                        ArrayList<String> keys = GeneralUtils.doSort(map);
//                        for (int i = 0; i < keys.size(); i++)
//                        {
//                            if ("hash".equals(keys.get(i)))
//                            {
//                                continue;
//                            }
//                            String key = keys.get(i);
//                            String value = map.get(keys.get(i));
//                            CMLog.i(TAG, "参数名     " + key);
//                            CMLog.i(TAG, "参数值     " + value);
//                            BasicNameValuePair valuePair = new BasicNameValuePair(key, value);
//                            nameValuePairs.add(valuePair);
//                            hashValue += value;
//                        }
//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash", GsonHelper.toJson(map));
//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash", SecurityUtils.get32MD5Str(hashValue));
//                        CMLog.i(TAG, "hash " + SecurityUtils.get32MD5Str(hashValue));
//                        CMLog.i(TAG, "hash " + "加密前:" +  GsonHelper.toJson(map));
//                        nameValuePairs.add(valuePair);
//                    }
                CMLog.e("HTTP", "参数：\n" + GsonHelper.toJson(map));
                HttpEntity httpEntity = new StringEntity(GsonHelper.toJson(map), HTTP.UTF_8);
                httpPost.setEntity(httpEntity);

//            }
                HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
                if (httpResponse == null) {
                    CMLog.i(TAG, "http response result null");
                    return null;
                }

                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    if (url.contains(URLUtil.LOGIN)) {
                        BaseApplication.cookieStore = defaultHttpClient.getCookieStore();
                    }
                    String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                    CMLog.i(TAG, "http response sucessful");
                    if (result == null || "".equals(result) || result.indexOf("<html>") > -1) {
                        CMLog.i(TAG, "request was sucessful, but paser value was null or empty");
                        return null;
                    }
//                    CMLog.i(TAG, "respnse result:" + result);
                    KLog.json(TAG, result);
                    return result;
                } else {
                    CMLog.i(TAG, "http response code:" + httpResponse.getStatusLine().getStatusCode());
                    return null;
                }
            } catch (ClientProtocolException e) {
                CMLog.i(TAG, "client protocol exception" + e.getMessage());
            }
            return null;
        }

        private String doPost2(String url, Map<String, Object> map, Map<String, List<File>> fileParameters)
                throws ConnectTimeoutException, IOException, InterruptedException {
            CMLog.i(TAG, "参数名     " + url);
            try {
                //                defaultHttpClient = new DefaultHttpClient();
                httpPost = new HttpPost(url);
                HttpParams httpParams = new BasicHttpParams();
                HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(httpParams, CHARSET);
                HttpProtocolParams.setUseExpectContinue(httpParams, false);

                HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
                HttpConnectionParams.setSoTimeout(httpParams, 30000);
                httpPost.setParams(httpParams);
                //设备类型ID,由服务端为每类客户端分配不同的设备类型ID及相应的私有密码。
                httpPost.setHeader("x-s-deviceID", BaseApplication.DEVICE_TOKEN);
                //服务端分配的设备请求密码。需要使用SHA256算法进行加密传输。
                httpPost.setHeader("x-s-x-s-password", Global.getXS_PASSWORD_WORD());
                String pass = Global.getXS_PASSWORD_WORD();
                //时间戳
                httpPost.setHeader("x-s-timestamp", GeneralUtils.formatDate(new Date(), GeneralUtils.DATE_PATTERN));
                String time = GeneralUtils.formatDate(new Date(), GeneralUtils.DATE_PATTERN);
                //校验码
                httpPost.setHeader("x-s-security", Global.getXSSecurity());
                String se = Global.getXSSecurity();
                //登录账户
                httpPost.setHeader("x-s-loginName",Global.getUserName());
                //客户端版本号
                httpPost.setHeader("x-s-clientVersion",""+ Constants.VERSION_NAME);
                SchemeRegistry schReg = new SchemeRegistry();
                schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(httpParams, schReg);
                defaultHttpClient = new DefaultHttpClient(conMgr, httpParams);
                SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());

                if (GeneralUtils.isNotNull(BaseApplication.cookieStore)) {
                    defaultHttpClient.setCookieStore(BaseApplication.cookieStore);
                }

//                if (fileParameters != null && fileParameters.size() > 0)
//                {
//                    MultipartEntity multipartEntity =
//                            new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
//                    if (map != null)
//                    {
//                        String hashValue = "";
//                        ArrayList<String> keys = GeneralUtils.doSort(map);
//                        for (int i = 0; i < keys.size(); i++)
//                        {
//                            if ("hash".equals(keys.get(i)))
//                            {
//                                continue;
//                            }
//                            String key = keys.get(i);
//                            String value = map.get(keys.get(i));
//                            CMLog.i(TAG, "参数名     " + key);
//                            CMLog.i(TAG, "参数值     " + value);
//                            BasicNameValuePair valuePair = new BasicNameValuePair(key, value);
//                            StringBody strBody =
//                                    new StringBody(String.valueOf(valuePair.getValue()), Charset.forName("UTF-8"));
//                            multipartEntity.addPart(valuePair.getName(), strBody);
//                            hashValue += value;
//                        }

//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash",GsonHelper.toJson(map));
//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash", SecurityUtils.get32MD5Str(hashValue));
//                        CMLog.i(TAG, "hash " + "加密前:" + GsonHelper.toJson(map));
//                        StringBody strBody =
//                                new StringBody(String.valueOf(valuePair.getValue()), Charset.forName("UTF-8"));
//                        multipartEntity.addPart(valuePair.getName(), strBody);
//                    }

//                    String fileExtension = null;
//                    String mineType = null;
//                    for (Map.Entry<String, List<File>> entry : fileParameters.entrySet())
//                    {
//                        List<File> files = entry.getValue();
//                        for (File file : files)
//                        {
//                            fileExtension = FileExtensionUtil.getFileExtensionFromName(file.getName());
//                            mineType = MimeTypeUtil.getSingleton().getMimeTypeFromExtension(fileExtension);
//                            if (StringUtil.isNotEmpty(mineType))
//                            {
//                                multipartEntity.addPart(entry.getKey(), new FileBody(file, mineType)); // 添加文件参数,带上mimeType
//                            } else
//                            {
//                                multipartEntity.addPart(entry.getKey(), new FileBody(file)); // 添加文件参数
//                            }
//                        }
//                    }
//                    httpPost.setEntity(multipartEntity);
//
//                } else
//                {
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//                    List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
//                    if (map != null)
//                    {
//                        String hashValue = "";
//                        ArrayList<String> keys = GeneralUtils.doSort(map);
//                        for (int i = 0; i < keys.size(); i++)
//                        {
//                            if ("hash".equals(keys.get(i)))
//                            {
//                                continue;
//                            }
//                            String key = keys.get(i);
//                            String value = map.get(keys.get(i));
//                            CMLog.i(TAG, "参数名     " + key);
//                            CMLog.i(TAG, "参数值     " + value);
//                            BasicNameValuePair valuePair = new BasicNameValuePair(key, value);
//                            nameValuePairs.add(valuePair);
//                            hashValue += value;
//                        }
//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash", GsonHelper.toJson(map));
//                        BasicNameValuePair valuePair =
//                                new BasicNameValuePair("hash", SecurityUtils.get32MD5Str(hashValue));
//                        CMLog.i(TAG, "hash " + SecurityUtils.get32MD5Str(hashValue));
//                        CMLog.i(TAG, "hash " + "加密前:" +  GsonHelper.toJson(map));
//                        nameValuePairs.add(valuePair);
//                    }
                CMLog.e("HTTP", "参数2：\n" + GsonHelper.toJson(map));
                HttpEntity httpEntity = new StringEntity(GsonHelper.toJson(map), HTTP.UTF_8);
                httpPost.setEntity(httpEntity);

//            }
                HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
                if (httpResponse == null) {
                    CMLog.i(TAG, "http response result null");
                    return null;
                }

                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    if (url.contains(URLUtil.LOGIN)) {
                        BaseApplication.cookieStore = defaultHttpClient.getCookieStore();
                    }
                    String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                    CMLog.i(TAG, "http response sucessful");
                    if (result == null || "".equals(result) || result.indexOf("<html>") > -1) {
                        CMLog.i(TAG, "request was sucessful, but paser value was null or empty");
                        return null;
                    }
//                    CMLog.i(TAG, "respnse result:" + result);
                    KLog.json(TAG, result);
                    return result;
                } else {
                    CMLog.i(TAG, "http response code:" + httpResponse.getStatusLine().getStatusCode());
                    return null;
                }
            } catch (ClientProtocolException e) {
                CMLog.i(TAG, "client protocol exception" + e.getMessage());
            }
            return null;
        }
    }

}
