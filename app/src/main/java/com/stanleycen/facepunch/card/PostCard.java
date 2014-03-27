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
import com.stanleycen.facepunch.event.OpenThreadEvent;
import com.stanleycen.facepunch.model.fp.FPForum;
import com.stanleycen.facepunch.model.fp.FPPost;
import com.stanleycen.facepunch.model.fp.FPThread;
import com.stanleycen.facepunch.util.RobotoFont;
import com.stanleycen.facepunch.util.Util;

import java.io.Serializable;

import de.greenrobot.event.EventBus;

/**
 * Created by scen on 2/16/14.
 */
public class PostCard extends Card implements Serializable {
    FPPost post;
    boolean selectable;

    public PostCard(FPPost post, boolean selectable) {
        this.post = post;
        this.selectable = selectable;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public void onClick() {
//        EventBus.getDefault().post(new (thread));
    }

    @Override
    public void getInnerView(LayoutInflater inflater, ViewStub stub, int position, Context context) {
        stub.setLayoutResource(R.layout.card_post);
        ViewGroup v = (ViewGroup) stub.inflate();
//
//        TextView title = (TextView) v.findViewById(R.id.title);
//        TextView author = (TextView) v.findViewById(R.id.author);
//        TextView reading = (TextView) v.findViewById(R.id.reading);
//        TextView replies = (TextView) v.findViewById(R.id.replies);
//        TextView pages = (TextView) v.findViewById(R.id.pages);
//
//        title.setText(thread.title);
//        title.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));
//
//
//        if (thread.sticky) {
//            title.setBackgroundColor(context.getResources().getColor(R.color.thread_sticky));
//        }
//
//        author.setText(thread.author.name);
//        author.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));
//
//        reading.setText(String.format("%s reading • %s views", Util.getFormattedInt(thread.reading), Util.getFormattedInt(thread.views)));
//        reading.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));
//
//        replies.setText(Util.getFormattedInt(thread.replies) + " replies");
//        replies.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));
//
//        pages.setText(Util.getFormattedInt(thread.maxPages) + " pages");
//        pages.setTypeface(RobotoFont.obtainTypeface(context, RobotoFont.ROBOTO_LIGHT));
    }
}
