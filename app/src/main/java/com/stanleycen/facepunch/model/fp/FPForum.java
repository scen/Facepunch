package com.stanleycen.facepunch.model.fp;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stanleycen.facepunch.util.API;
import com.stanleycen.facepunch.util.ResponseParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FPForum implements Serializable {
    public int id;
    public String name;
    public String desc;
    public int page;
    public int maxPages;

    public boolean fetched = false;

	public List<FPForum> subforums = new ArrayList<>();
    public List<FPThread> threads = new ArrayList<>();

    public void fetch(final Callback callback) {
        fetched = false;
        subforums.clear();
        threads.clear();
        String url = String.format("http://facepunch.com/forumdisplay.php?f=%d&page=%d", id, page);
        API.addToQueue(new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onResult(false, null);
            }
        }));
    }

    public FPForum(int id, int page, String name) {
        this.id = id;
        this.name = name;
        this.page = page;
    }

    public interface Callback {
        public void onResult(boolean success, FPForum forum);
    }
}

