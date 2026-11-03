package com.joyhonest.joycamera.sdk;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class JoyLog {
    private static boolean DEBUG = false;

    public static void SetDebug(boolean z) {
        DEBUG = z;
    }

    public static void e(String str, String str2) {
        if (DEBUG) {
            Log.e(str, str2);
        }
    }

    public static void i(String str, String str2) {
        if (DEBUG) {
            Log.i(str, str2);
        }
    }

    public static void v(String str, String str2) {
        if (DEBUG) {
            Log.v(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (DEBUG) {
            Log.d(str, str2);
        }
    }
}
