package com.stanleycen.facepunch.util;

import android.os.Build;

/**
 * Created by scen on 2/11/14.
 */
public class Util {
    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
