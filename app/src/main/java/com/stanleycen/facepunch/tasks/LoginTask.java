package com.stanleycen.facepunch.tasks;

import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;
import android.util.Pair;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.stanleycen.facepunch.events.LoginResponseEvent;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/11/14.
 */
public class LoginTask extends AsyncTask<Pair<String, String>, Void, Void> {
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @DebugLog
    @Override
    protected Void doInBackground(Pair<String, String>... params) {
        String passwordMD5 = Util.hashMD5(params[0].second);

        RequestParams requestParams = new RequestParams();
        requestParams.put("vb_login_username", params[0].first);
        requestParams.put("vb_login_password_hint", "Password");
        requestParams.put("vb_login_password", "");
        requestParams.put("s", "");
        requestParams.put("cookieuser", "1");
        requestParams.put("securitytoken", "guest");
        requestParams.put("do", "login");
        requestParams.put("vb_login_md5password", passwordMD5);
        requestParams.put("vb_login_md5password_utf", passwordMD5);

        API.post("login.php?do=login", requestParams, new AsyncHttpResponseHandler() {
            @Override
            @DebugLog
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                super.onSuccess(statusCode, headers, responseBody);

                String html = new String(responseBody);

                Document doc = Jsoup.parse(html);

                EventBus.getDefault().post(new LoginResponseEvent());
            }
        });
        return null;
    }
}
