package com.stanleycen.facepunch.model.fp;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stanleycen.facepunch.event.SubforumDataEvent;
import com.stanleycen.facepunch.event.ThreadDataEvent;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.ResponseParser;
import com.stanleycen.facepunch.util.Util;

import java.io.Serializable;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by scen on 2/15/14.
 */
public class FPThread implements Serializable {
    public String title;
    public int id;
    public int page;
    public int maxPages = 0;
    public boolean fetched = false;
    public boolean sticky = false;
    public boolean locked = false;
    public int views = 0;
    public int replies = 0;
    public int reading = 0;

    public FPUser author;
    public FPUser lastPoster;

    public ArrayList<FPPost> posts;
    public String lastPostTime;

    public void fetch() {
        fetched = false;
        posts.clear();
        String url = String.format("http://facepunch.com/showthread.php?t=%d&page=%d", id, page);
        API.addToQueue(new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Util.executeAsyncTask(new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        EventBus.getDefault().post(new ThreadDataEvent(true, ResponseParser.parseThread(params[0], FPThread.this)));
                        return null;
                    }
                }, s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("ok", volleyError.toString());
                EventBus.getDefault().post(new ThreadDataEvent(false, null));
            }
        }
        ));
    }


    public FPThread(int id, int page, String title) {
        this.id = id;
        this.page = page;
        this.title = title;

        posts = new ArrayList<>();
    }
}
