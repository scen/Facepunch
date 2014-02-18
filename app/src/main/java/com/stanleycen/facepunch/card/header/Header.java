package com.stanleycen.facepunch.card.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.stanleycen.facepunch.model.ICardListItem;

import java.io.Serializable;

/**
 * Created by scen on 2/17/14.
 */
public abstract class Header extends ICardListItem implements Serializable {
    @Override
    public boolean isHeader() {
        return true;
    }
}
