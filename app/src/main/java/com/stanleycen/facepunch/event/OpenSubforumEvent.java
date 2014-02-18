package com.stanleycen.facepunch.event;

import com.stanleycen.facepunch.model.fp.FPForum;

/**
 * Created by scen on 2/18/14.
 */
public class OpenSubforumEvent {
    public FPForum forum;
    public OpenSubforumEvent() {}
    public OpenSubforumEvent(FPForum f) {
        forum = f;
    }
}
