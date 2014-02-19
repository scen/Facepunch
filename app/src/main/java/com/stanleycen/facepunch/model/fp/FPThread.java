package com.stanleycen.facepunch.model.fp;

import java.io.Serializable;
import java.util.ArrayList;

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

    public ArrayList<FPPost> posts;

    public FPThread(int id, int page, String title) {
        this.id = id;
        this.page = page;
        this.title = title;
    }
}
