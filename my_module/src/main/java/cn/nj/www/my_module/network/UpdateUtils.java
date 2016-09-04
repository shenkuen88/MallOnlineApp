package cn.nj.www.my_module.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.update.CodeBean;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.dialog.UpdateDialog;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import de.greenrobot.event.EventBus;

/**
 * Created by jwei on 2016/7/16.
 */
public class UpdateUtils {
    private static final String sjFule = "updateapk.apk";
    private static String path = Environment.getExternalStorageDirectory() + "/" + sjFule;
    private Context context;
    private UpdateDialog updateDialog;

    public void showDialog(Context context,final CodeBean codebean) {
        this.context = context;
        updateDialog = new UpdateDialog(context, R.style.dialog, codebean, 1);
        updateDialog.show();
        //点击dialog进行更新
        updateDialog.setGXClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
                if (codebean != null) {//如果有更新信息
                    new Update_Vode(codebean.getUrl()).execute();
                }
            }
        });
        updateDialog.setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_USER_EXIT_APP));
                dialogCancel();
            }
        });
    }


    public void dialogCancel() {
        if (null != updateDialog) {
            updateDialog.dismiss();
        }
    }

    //判断内存中是否有升级包，有则删除
    private static void check() {
        try {
            // 如果内存中存在程序升级包就删除掉
            File file_apk = new File(path);
            if (file_apk.exists()) {
                file_apk.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    InputStream is = null;
    HttpURLConnection conn = null;
    BufferedInputStream bis = null;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;

    public static boolean createIfNotExist(String filePath) {
        boolean exist = false;
        try {
            File f = new File(filePath);
            if (f.exists()) {
                exist = true;
            } else {
                FileWriter fw = new FileWriter(filePath);
                fw.write("");
                fw.flush();
                fw.close();
                exist = true;
                fw = null;
            }
            f = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 更新版本，传入参数是新版本地址
     */
    class Update_Vode extends AsyncTask<String, Integer, String> {
        String downUrl;

        Update_Vode(String downUrl) {
            this.downUrl = downUrl;
            CMLog.e(Constants.LOCAL_TAG, "downUrl :" + downUrl);
        }

        @SuppressLint("WorldReadableFiles")
        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            CMLog.e(Constants.LOCAL_TAG, "path:" + path);
            try {
                createIfNotExist(path);
                URL url = new URL(downUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(100000);
                conn.setRequestProperty("Connection", "close");
                conn.connect();
                url = null;
                is = conn.getInputStream();
                int status = conn.getResponseCode();
                int len = conn.getContentLength();
                if (status == 200) {
                    int progress = 0;
                    bis = new BufferedInputStream(is, 16384);
                    fos = new FileOutputStream(path);
                    bos = new BufferedOutputStream(fos, 16384);
                    int i = -1;
                    int num = 0;
                    byte data[] = new byte[8192];
                    while ((i = bis.read(data)) != -1) {
                        num++;
                        bos.write(data, 0, i);
                        progress = progress + i;
                        if (num % 5 == 0) {
                            publishProgress((int) ((progress / (float) len) * 100));
                        }
                    }
                    bos.flush();
                    fos.flush();
                    if (is != null)
                        is.close();
                    if (bis != null)
                        bis.close();
                    if (fos != null)
                        fos.close();
                    if (bos != null)
                        bos.close();
                    if (conn != null)
                        conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null)
                        is.close();
                    if (bis != null)
                        bis.close();
                    if (fos != null)
                        fos.close();
                    if (bos != null)
                        bos.close();
                    if (conn != null)
                        conn.disconnect();
                    is = null;
                    bis = null;
                    conn = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            final int prog = (int) (progress[0]);
            ((BaseActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateDialog.setProgressNum(Integer.parseInt(prog + ""));
                }
            });

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
//                if (GeneralUtils.isActivityISNull((BaseActivity)context)) {
//                    return;
//                }
                File files = new File(path);
                CMLog.e(Constants.LOCAL_TAG, "files.length() is:" + files.length());
                if (files.length() > 1000) {
                    dialogCancel();
                    GeneralUtils.installApk(context, new File(path));
                } else {
                    dialogCancel();
                    Toast.makeText(context, "下载失败，请检查网络",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
