package com.stanleycen.facepunch.model.fp;

import java.io.Serializable;

/**
 * Created by scen on 2/18/14.
 */
public class FPPost implements Serializable {
    public long id;
    public FPUser author;
    public String date;
    public String content;

    public FPPost(long id, FPUser author, String date, String content) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.content = content;
    }
}
