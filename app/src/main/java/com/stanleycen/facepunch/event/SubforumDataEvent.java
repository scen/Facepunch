package com.stanleycen.facepunch.event;

import com.stanleycen.facepunch.model.fp.FPForum;

/**
 * Created by scen on 2/18/14.
 */
public class SubforumDataEvent {
    public boolean success;
    public FPForum forum;

    public SubforumDataEvent() {}
    public SubforumDataEvent(boolean success, FPForum forum) {
        this.success = success;
        this.forum = forum;
    }
}

