package com.stanleycen.facepunch.card;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.model.ICardItem;

/**
 * Created by scen on 2/16/14.
 */
public abstract class Card implements ICardItem {
    @Override
    public int getViewType() {
        //TODO: temp
        return 0;
    }

    public View getView(LayoutInflater inflater, View convertView, int position, Context context) {
        ViewGroup v = (ViewGroup) convertView;
        ViewStub innerView = null;

        if (v == null) {
            v = (ViewGroup) inflater.inflate(R.layout.card, null);
        }
        else {
            innerView = (ViewStub) v.findViewById(R.id.contents); // TODO: do we have to replace it? is there a better way
            // it was replaced
            if (innerView == null) {
                v = (ViewGroup) inflater.inflate(R.layout.card, null);
            }
        }

        if (innerView == null) innerView = (ViewStub) v.findViewById(R.id.contents);

        getInnerView(inflater, innerView, position, context);


        return v;
    }
}
