package com.stanleycen.facepunch.card.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.card.CardItemTypes;
import com.stanleycen.facepunch.util.RobotoFont;

/**
 * Created by scen on 2/17/14.
 */
public class DefaultTextHeader extends Header {
    @Override
    public int getViewType() {
        return CardItemTypes.DEFAULT_TEXT_HEADER.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, int position, Context context) {
        ViewGroup v = (ViewGroup) convertView;

        if (v == null) {
            v = (ViewGroup) inflater.inflate(R.layout.header_default_text, null);
        }

        TextView text = (TextView) v.findViewById(R.id.text);
        text.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT_ITALIC));

        return v;
    }
}
