package com.joyhonest.wifi_check_adj;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class Storage {
    public static final String VENDOR = "JH-WIFI-FPV";

    public static String getPhoneCardPath() {
        return Environment.getDataDirectory().getPath();
    }

    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getSDCardPathEx() {
        String str = new String();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (!line.contains("secure") && !line.contains("asec") && (line.contains("fat") || line.contains("fuse"))) {
                    String[] strArrSplit = line.split(" ");
                    if (strArrSplit != null && strArrSplit.length > 1 && getTotalSize(strArrSplit[1]) > 1073741824 && !getNormalSDCardPath().equals(strArrSplit[1])) {
                        str = str.concat(strArrSplit[1] + ";");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return str;
    }

    public static long getTotalSize(String str) {
        try {
            StatFs statFs = new StatFs(new File(str).getPath());
            return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static long getAvailableSize(String str) {
        try {
            StatFs statFs = new StatFs(new File(str).getPath());
            return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float dip2pxF(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().density;
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
