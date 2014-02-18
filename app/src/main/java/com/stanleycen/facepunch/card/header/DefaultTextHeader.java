package com.stanleycen.facepunch.card.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.card.CardItemTypes;
import com.stanleycen.facepunch.util.RobotoFont;

import java.io.Serializable;

/**
 * Created by scen on 2/17/14.
 */
public class DefaultTextHeader extends Header implements Serializable {
    String text;

    @Override
    public int getViewType() {
        return CardItemTypes.DEFAULT_TEXT_HEADER.ordinal();
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    public DefaultTextHeader(String text) {
        this.text = text;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, int position, Context context) {
        ViewGroup v = (ViewGroup) convertView;

        if (v == null) {
            v = (ViewGroup) inflater.inflate(R.layout.header_default_text, null);
        }

        TextView text = (TextView) v.findViewById(R.id.text);
        text.setText(this.text);

        text.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_CONDENSED_BOLD_ITALIC));

        return v;
    }
}
