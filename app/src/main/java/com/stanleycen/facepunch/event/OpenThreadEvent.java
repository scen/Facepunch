package com.stanleycen.facepunch.event;

import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPThread;

/**
 * Created by scen on 2/19/14.
 */
public class OpenThreadEvent {
    public FPThread thread;
    public OpenThreadEvent() {}
    public OpenThreadEvent(FPThread thread) {
        this.thread = thread;
    }
}
