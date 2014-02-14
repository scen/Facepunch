package com.stanleycen.facepunch.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.Volley;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/11/14.
 */
public class API {
    public static final String BASE_URL = "http://facepunch.com/";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36";

    public static final int API_TIMEOUT_MS = 10000;

    public static RequestQueue queue;
    public static DefaultHttpClient client;
    public static PersistentCookieStore store;

    public static void logout() {
        store.clear();
    }

    public static boolean isLoggedIn() {
        int count = 0;

        for (Cookie c : store.getCookies()) {
            String name = c.getName();
            if (name.equals("bb_sessionhash") || name.equals("bb_userid") || name.equals("bb_password")) {
                count++;
                if (c.isExpired(Util.now())) {
                    logout();
                    return false;
                }
            }
        }
        if (count != 3) {
            logout();
            return false;
        } else return true;
    }

    @DebugLog
    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void init(Context context) {
        store = new PersistentCookieStore(context);

        client = new DefaultHttpClient();
        client.setCookieStore(store);
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);

        queue = Volley.newRequestQueue(context, new HttpClientStack(client));

        store.clearExpired(Util.now());

        isLoggedIn();
    }

}
