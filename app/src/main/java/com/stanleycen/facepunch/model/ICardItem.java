package com.stanleycen.facepunch.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

/**
 * Created by scen on 2/16/14.
 */
public interface ICardItem {
    public void getInnerView(LayoutInflater inflater, ViewStub stub, int position, Context context);
}
