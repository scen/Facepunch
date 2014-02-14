package com.stanleycen.facepunch.util;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.cookie.Cookie;

import java.util.Date;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/11/14.
 */
public class API {
    public static final String BASE_URL = "http://facepunch.com/";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1664.3 Safari/537.36";

    public static final int API_TIMEOUT_MS = 10000;

    public static AsyncHttpClient client;
    public static PersistentCookieStore store;

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

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
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void init(Context context) {
        client = new AsyncHttpClient();
        store = new PersistentCookieStore(context);
        client.setCookieStore(API.store);
        client.setUserAgent(USER_AGENT);

        store.clearExpired(Util.now());

        isLoggedIn();
    }
}
