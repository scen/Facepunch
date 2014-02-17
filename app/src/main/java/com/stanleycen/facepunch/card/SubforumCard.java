package com.stanleycen.facepunch.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewStub;

import com.stanleycen.facepunch.R;

/**
 * Created by scen on 2/16/14.
 */
public class SubforumCard extends Card {
    @Override
    public void getInnerView(LayoutInflater inflater, ViewStub stub, int position, Context context) {
        stub.setLayoutResource(R.layout.card_subforum);
        stub.inflate();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
