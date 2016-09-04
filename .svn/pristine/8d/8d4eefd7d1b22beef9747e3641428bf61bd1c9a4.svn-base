package cn.nj.www.my_module.tools;

import android.content.Context;
import android.text.TextUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.util.List;

public class CookieUtils
{
    private static CookieUtils cookieUtils;

    private static PersistentCookieStore myCookieStore;

    private CookieUtils()
    {
    }

    public static CookieUtils getInstance(Context context)
    {
        if (cookieUtils == null)
        {
            cookieUtils = new CookieUtils();
            myCookieStore = new PersistentCookieStore(context);
        }
        return cookieUtils;
    }

    public void saveCookies(CookieStore cookieStore)
    {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies)
        {
            myCookieStore.addCookie(cookie);
        }
    }

    public PersistentCookieStore getCookies()
    {
        return myCookieStore;
    }

    /**
     * <打印cookie>
     * <功能详细描述>
     */
    public String getCookie(CookieStore cookieStore)
    {
        List<Cookie> cookies = cookieStore.getCookies();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++)
        {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName) && !TextUtils.isEmpty(cookieValue))
            {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        CMLog.i("info", "---" + sb.toString());
        return sb.toString();
    }
}
