package com.stanleycen.facepunch.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.stanleycen.facepunch.R;
import com.stanleycen.facepunch.event.OpenSubforumEvent;
import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.util.RobotoFont;

import java.io.Serializable;

import de.greenrobot.event.EventBus;

/**
 * Created by scen on 2/16/14.
 */
public class SubforumCard extends Card implements Serializable {
    FPForum forum;
    boolean selectable;

    public SubforumCard(FPForum forum, boolean selectable) {
        this.forum = forum;
        this.selectable = selectable;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public void onClick() {
        EventBus.getDefault().post(new OpenSubforumEvent(forum));
    }

    @Override
    public void getInnerView(LayoutInflater inflater, ViewStub stub, int position, Context context) {
        stub.setLayoutResource(R.layout.card_subforum);
        ViewGroup v = (ViewGroup) stub.inflate();

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView desc = (TextView) v.findViewById(R.id.desc);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);

        title.setText(forum.name);
        title.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));

        if (forum.desc != null) {
            desc.setText(forum.desc);
            desc.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_CONDENSED_LIGHT_ITALIC));
        }
        else {
            desc.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
