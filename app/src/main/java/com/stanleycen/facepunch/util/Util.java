package com.stanleycen.facepunch.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Display;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by scen on 2/11/14.
 */
public class Util {
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    @TargetApi(13)
    private static Point getScreenSize13(Display d) {
        Point p = new Point();
        d.getSize(p);
        return p;
    }

    public static Point getScreenSize(Display d) {
        if (Build.VERSION.SDK_INT >= 13) {
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

    public static void toast(Context ctx, String str) {
        Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
    }
}
