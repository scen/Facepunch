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

public class ListForumsTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @DebugLog
    @Override
    protected Void doInBackground(Void... params) {
        API.post("", null, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
                // EventBus.getDefault().post(new LoginResponseEvent(false));
            }

            @Override
            @DebugLog
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                super.onSuccess(statusCode, headers, responseBody);

                // EventBus.getDefault().post(new LoginResponseEvent(true));
            }
        });
        return null;
    }
}
