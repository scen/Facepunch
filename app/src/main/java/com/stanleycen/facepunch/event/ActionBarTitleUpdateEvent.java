package com.stanleycen.facepunch.event;

/**
 * Created by scen on 2/15/14.
 */
public class ActionBarTitleUpdateEvent {
    public String title;
    public ActionBarTitleUpdateEvent() {}
    public ActionBarTitleUpdateEvent(String t) {
        title = t;
    }
}
