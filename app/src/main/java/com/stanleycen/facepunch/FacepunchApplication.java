package com.stanleycen.facepunch;

import android.app.Application;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.stanleycen.facepunch.util.API;

/**
 * Created by scen on 2/11/14.
 */
public class FacepunchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        API.init(this);
    }
}
