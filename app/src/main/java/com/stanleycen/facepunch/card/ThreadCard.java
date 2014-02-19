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
import com.stanleycen.facepunch.model.fp.FPThread;
import com.stanleycen.facepunch.util.RobotoFont;
import com.stanleycen.facepunch.util.Util;

import java.io.Serializable;

import de.greenrobot.event.EventBus;

/**
 * Created by scen on 2/16/14.
 */
public class ThreadCard extends Card implements Serializable {
    FPThread thread;
    boolean selectable;

    public ThreadCard(FPThread thread, boolean selectable) {
        this.thread = thread;
        this.selectable = selectable;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public void onClick() {
    }

    @Override
    public void getInnerView(LayoutInflater inflater, ViewStub stub, int position, Context context) {
        stub.setLayoutResource(R.layout.card_thread);
        ViewGroup v = (ViewGroup) stub.inflate();

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView info = (TextView) v.findViewById(R.id.info);

        title.setText(thread.title);
        title.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));


        if (thread.sticky) {
            title.setBackgroundColor(context.getResources().getColor(R.color.thread_sticky));
        }

        info.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));
//
//        if (forum.desc != null) {
//            desc.setText(forum.desc);
//            desc.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_CONDENSED_LIGHT_ITALIC));
//        }
//        else {
//            desc.setVisibility(View.GONE);
//        }
    }
}
