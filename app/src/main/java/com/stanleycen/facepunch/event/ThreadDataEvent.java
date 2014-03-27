package com.stanleycen.facepunch.event;

import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPThread;

/**
 * Created by scen on 2/18/14.
 */
public class ThreadDataEvent {
    public boolean success;
    public FPThread thread;

    public ThreadDataEvent() {}
    public ThreadDataEvent(boolean success, FPThread thread) {
        this.success = success;
        this.thread = thread;
    }
}

