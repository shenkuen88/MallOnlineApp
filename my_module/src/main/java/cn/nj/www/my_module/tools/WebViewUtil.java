package cn.nj.www.my_module.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseApplication;
import de.greenrobot.event.EventBus;

/**
 * Created by wangtao on 2015/11/25.
 */
public class WebViewUtil
{
    public static void initWebView(final Context context, final WebView webView, String url)
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        //        webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(false);
        // webView.setInitialScale(25);
        if (BaseApplication.cookieStore != null)
        {
            synCookies(context, url);
        }
        webView.loadUrl(url);// 载入网页
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.addJavascriptInterface(new JavaScriptinterface(context), "android");
        webView.setWebViewClient(new WebViewClient()
        {

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url)
            {
                if (BaseApplication.cookieStore != null)
                {
                    synCookies(context, url);
                }
                view.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        view.loadUrl(url.trim());// 载入网页
                    }
                }, 500);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_WEB_VIEW_ERROR));
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_WEB_VIEW_START));
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url)
            {
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_WEB_VIEW_FINISH));
                super.onPageFinished(view, url);
            }
        });
    }

    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String url)
    {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie();
        StringBuilder sbCookie = new StringBuilder();
//        sbCookie.append(CookieUtils.getInstance(context).getCookie(BaseApplication.cookieStore));
//        sbCookie.append(String.format("domain=%s", URLUtil.DEFAULT_WEB_COOKIE));
//        sbCookie.append(String.format(";path=%s", URLUtil.SERVER_BASE));
        String cookieValue = sbCookie.toString();
        CMLog.i("info", "cookieValue:" + cookieValue);
        cookieManager.setCookie(url, cookieValue);
        CookieSyncManager.getInstance().sync();
    }
}
