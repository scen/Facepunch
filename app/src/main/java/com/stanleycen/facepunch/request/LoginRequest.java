package com.stanleycen.facepunch.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.stanleycen.facepunch.event.LoginResponseEvent;
import com.stanleycen.facepunch.request.base.ParamStringRequest;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.Util;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 */
public class LoginRequest extends ParamStringRequest {
    static Response.Listener<String> makeListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                EventBus.getDefault().post(new LoginResponseEvent(true));
            }
        };
    }

    static Response.ErrorListener makeErrorListener() {
        return new Response.ErrorListener() {
            @Override
            @DebugLog
            public void onErrorResponse(VolleyError volleyError) {
                EventBus.getDefault().post(new LoginResponseEvent(false));
            }
        };
    }

    static HashMap<String, String> makeParams(String username, String password) {
        String passwordMD5 = Util.hashMD5(password);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("vb_login_username", username);
        params.put("vb_login_password_hint", "Password");
        params.put("vb_login_password", "");
        params.put("s", "");
        params.put("cookieuser", "1");
        params.put("securitytoken", "guest");
        params.put("do", "login");
        params.put("vb_login_md5password", passwordMD5);
        params.put("vb_login_md5password_utf", passwordMD5);
        return params;
    }

    public LoginRequest(String username, String password) {
        super(Method.POST, API.getAbsoluteUrl("login.php?do=login"), makeParams(username, password), makeListener(), makeErrorListener());
    }

}