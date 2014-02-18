package com.stanleycen.facepunch.card.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stanleycen.facepunch.model.ICardListItem;

/**
 * Created by scen on 2/17/14.
 */
public abstract class Header implements ICardListItem {
    @Override
    public boolean isHeader() {
        return true;
    }
}
