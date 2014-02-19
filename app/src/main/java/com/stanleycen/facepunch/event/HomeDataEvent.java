package com.stanleycen.facepunch.event;

import com.stanleycen.facepunch.model.fp.FPForum;

import java.util.ArrayList;

/**
 * Created by scen on 2/18/14.
 */
public class HomeDataEvent {
    public ArrayList<FPForum> forums;
    public HomeDataEvent() {}
    public HomeDataEvent(ArrayList<FPForum> forums) {
        this.forums = forums;
    }
}
