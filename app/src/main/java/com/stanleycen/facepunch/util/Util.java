package com.stanleycen.facepunch.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.stanleycen.facepunch.fragment.HomeFragment;
import com.stanleycen.facepunch.fragment.SubforumFragment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by scen on 2/11/14.
 */
public class Util {
    private static final DecimalFormat decimalFormat = new DecimalFormat();

    public static String getFormattedInt(int i) {
        return decimalFormat.format(i);
    }

    public static boolean isKitKat() {
        return getBuildVersion() >= Build.VERSION_CODES.KITKAT;
    }

    public static int getBuildVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @TargetApi(11)
    public static <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        if (getBuildVersion() >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }
        else {
            task.execute(params);
        }
    }

    public static void saveListViewState(Bundle outState, ListView cardListView) {
        try {
            int idx = cardListView.getFirstVisiblePosition();
            View v = cardListView.getChildAt(0);
            outState.putInt("card_list_view_idx", idx);
            outState.putInt("card_list_view_top", (v == null) ? 0 : v.getTop());
        }
        catch (Throwable t) {
            t.printStackTrace();
            outState.putInt("card_list_view_idx", -1);
        }
    }

    public static void restoreListViewState(Bundle savedInstanceState, ListView cardListView) {
        int idx = savedInstanceState.getInt("card_list_view_idx", -1);
        int top = savedInstanceState.getInt("card_list_view_top", -1);
        if (idx != -1 && top != -1) {
            cardListView.setSelectionFromTop(idx, top);
        }
    }

    @TargetApi(19)
    public static void setInsets(Activity context, View view) {
        if (Util.isKitKat()) {
            SystemBarTintManager tintManager = new SystemBarTintManager(context);
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + config.getPixelInsetTop(true), view.getPaddingRight(), view.getPaddingBottom());
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + config.getPixelInsetTop(true), view.getPaddingRight() + config.getPixelInsetRight(), view.getPaddingBottom() + config.getPixelInsetBottom());
        }
    }

    @TargetApi(13)
    private static Point getScreenSize13(Display d) {
        Point p = new Point();
        d.getSize(p);
        return p;
    }

    public static Point getScreenSize(Display d) {
        if (getBuildVersion() >= 13) {
            return getScreenSize13(d);
        }
        else {
            return new Point(d.getWidth(), d.getHeight());
        }
    }

    // if its the first time showing something, e.g. a ShowcaseView
    public static boolean isFirstTime(Context context, String pref) {
        return !getSharedPrefs(context).getBoolean(pref, false);
    }

    public static void setFirstTimeDone(Context context, String pref, boolean val) {
        getSharedPrefs(context).edit().putBoolean(pref, val).commit();
    }

    public static void setFirstTimeDone(Context context, String pref) {
        setFirstTimeDone(context, pref, true);
    }

    public static String hashMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", (int) (b & 0xff)));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date now() {
        return new Date();
    }

    public static void toast(final Activity ctx, final String str) {
        if (ctx == null) return;
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void eventBusRegister(Object obj) {
        if (!EventBus.getDefault().isRegistered(obj)) EventBus.getDefault().register(obj);
    }

    public static void eventBusUnregister(Object obj) {
        if (EventBus.getDefault().isRegistered(obj)) EventBus.getDefault().unregister(obj);
    }
}
