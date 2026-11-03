package com.joyhonest.wifi_check_adj;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.joyhonest.joycamera.sdk.wifiCamera;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MyApp extends Application implements Application.ActivityLifecycleCallbacks {
    public static int Brow_Photo = 0;
    public static int Brow_Video = 1;
    public static boolean bBrowPhoto = true;
    public static boolean bNeedAsk = true;
    public static boolean bUsbCamera = false;
    private static int music_Alert = -1;
    private static int music_butn = -1;
    private static int music_photo = -1;
    public static int nBrowType;
    public static int nResolutionSel;
    public static int nStatus;
    public static String sLocalPhoto;
    public static String sLocalVideo;
    private static MyApp singleton;
    private static SoundPool soundPool;
    private int activityAount = 0;
    public static List<Uri> dispList = new ArrayList();
    public static int dispListInx = 0;
    public static int Para_Brightness = 50;
    public static int Para_Contrast = 50;
    public static int Para_Saturation = 50;
    public static int Para_R = 50;
    public static int Para_G = 50;
    public static int Para_B = 50;
    private static int streamID_Alert = 0;
    public static boolean bGotsystemActivity = false;

    public static void F_PlayBtnVoice() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        singleton = this;
        registerActivityLifecycleCallbacks(this);
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(3);
        AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
        builder2.setLegacyStreamType(3);
        builder.setAudioAttributes(builder2.build());
        SoundPool soundPoolBuild = builder.build();
        soundPool = soundPoolBuild;
        music_butn = soundPoolBuild.load(this, R.raw.button46, 1);
        music_photo = soundPool.load(this, R.raw.photo_m, 1);
        music_Alert = soundPool.load(this, R.raw.buzzsound, 1);
    }

    public MyApp getSingleton() {
        return singleton;
    }

    public static void F_CreateLocalDir(String str) {
        if (isAndroidQ()) {
            Context applicationContext = singleton.getApplicationContext();
            File externalFilesDir = applicationContext.getExternalFilesDir(str + "_Photo");
            if (externalFilesDir != null) {
                sLocalPhoto = externalFilesDir.getAbsolutePath();
            }
            File externalFilesDir2 = applicationContext.getExternalFilesDir(str + "_Video");
            if (externalFilesDir2 != null) {
                sLocalVideo = externalFilesDir2.getAbsolutePath();
                return;
            }
            return;
        }
        if (str.length() != 0) {
            try {
                String normalSDCardPath = Storage.getNormalSDCardPath();
                if (normalSDCardPath.length() == 0) {
                    normalSDCardPath = Storage.getNormalSDCardPath();
                }
                String str2 = String.format("%s/%s/" + str + "_Video", normalSDCardPath, str);
                sLocalVideo = str2;
                File file = new File(str2);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String str3 = String.format("%s/%s/" + str + "_Photo", normalSDCardPath, str);
                sLocalPhoto = str3;
                File file2 = new File(str3);
                if (file2.exists()) {
                    return;
                }
                file2.mkdirs();
            } catch (Exception unused) {
            }
        }
    }

    public static void checkDeviceHasNavigationBar(Context context) {
        Window window = ((Activity) context).getWindow();
        if (window == null) {
            return;
        }
        window.setFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        window.setFlags(134217728, 134217728);
        window.setFlags(128, 128);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.getDecorView().setBackgroundColor(-1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes2 = window.getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            window.setAttributes(attributes2);
            window.getDecorView().setSystemUiVisibility(5894);
        }
        window.setAttributes(attributes);
    }

    public static void F_GetSettings() {
        nResolutionSel = singleton.getSharedPreferences("Wifi-Check%&$", 0).getInt("nResolutionSel", 1);
    }

    public static void F_SavwSettings() {
        SharedPreferences.Editor editorEdit = singleton.getSharedPreferences("Wifi-Check%&$", 0).edit();
        editorEdit.putInt("nResolutionSel", nResolutionSel);
        editorEdit.apply();
    }

    public static boolean F_CheckIsExit(String str, String str2, boolean z) {
        Cursor cursorQuery;
        Context applicationContext = singleton.getApplicationContext();
        if (applicationContext == null) {
            return false;
        }
        ContentResolver contentResolver = applicationContext.getContentResolver();
        if (!str.endsWith("/")) {
            str = str + "/";
        }
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (!z) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        Uri uri2 = uri;
        if (z) {
            cursorQuery = contentResolver.query(uri2, new String[]{"_id"}, "relative_path=? and _display_name=?", new String[]{str, str2}, null);
        } else {
            cursorQuery = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "relative_path=? and _display_name=?", new String[]{str, str2}, null);
        }
        if (cursorQuery == null) {
            return false;
        }
        int count = cursorQuery.getCount();
        cursorQuery.close();
        return count > 0;
    }

    public static void F_Save2ToGallery(String str, boolean z) {
        Uri uriInsert;
        Context applicationContext = singleton.getApplicationContext();
        if (isAndroidQ()) {
            File file = new File(str);
            if (!file.exists()) {
                return;
            }
            String parent = file.getParent();
            String strSubstring = parent.substring(parent.lastIndexOf("/") + 1);
            String strSubstring2 = str.substring(str.lastIndexOf("/") + 1);
            String strSubstring3 = str.substring(str.lastIndexOf(".") + 1);
            ContentResolver contentResolver = applicationContext.getContentResolver();
            ContentValues contentValues = new ContentValues();
            String str2 = Environment.DIRECTORY_DCIM + File.separator + strSubstring;
            if (F_CheckIsExit(str2, strSubstring2, z)) {
                return;
            }
            if (z) {
                contentValues.put("_display_name", strSubstring2);
                if (strSubstring3.equalsIgnoreCase("png")) {
                    contentValues.put("mime_type", "image/png");
                } else {
                    contentValues.put("mime_type", "image/jpeg");
                }
                contentValues.put("relative_path", str2);
                uriInsert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                new File(str);
                contentValues.put("_display_name", strSubstring2);
                contentValues.put("mime_type", "video/mp4");
                contentValues.put("relative_path", str2);
                uriInsert = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
            if (uriInsert == null) {
                return;
            }
            try {
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                FileInputStream fileInputStream = new FileInputStream(new File(str));
                byte[] bArr = new byte[512000];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i != -1) {
                        outputStreamOpenOutputStream.write(bArr, 0, i);
                    } else {
                        fileInputStream.close();
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ContentResolver contentResolver2 = applicationContext.getContentResolver();
                ContentValues contentValues2 = new ContentValues();
                if (z) {
                    contentValues2.put("mime_type", "image/jpeg");
                    contentValues2.put("_data", str);
                    contentResolver2.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);
                } else {
                    contentValues2.put("mime_type", "video/mp4");
                    contentValues2.put("_data", str);
                    contentResolver2.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues2);
                }
                applicationContext.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void DeleteImage_android9(String str) {
        Cursor cursorQuery;
        if (isAndroidQ()) {
            return;
        }
        Context applicationContext = singleton.getApplicationContext();
        String strSubstring = str.substring(str.lastIndexOf(".") + 1);
        ContentResolver contentResolver = applicationContext.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (strSubstring.equalsIgnoreCase("jpg") || strSubstring.equalsIgnoreCase("png")) {
            cursorQuery = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{str}, null);
        } else {
            cursorQuery = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{str}, null);
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    contentResolver.delete(ContentUris.withAppendedId(uri, cursorQuery.getLong(0)), null, null);
                }
            } catch (Exception unused) {
            }
            File file = new File(str);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    public static void DeleteImage(String str) {
        Cursor cursorQuery;
        Cursor cursorQuery2;
        Context applicationContext = singleton.getApplicationContext();
        if (applicationContext == null) {
            return;
        }
        if (isAndroidQ()) {
            String strSubstring = str.substring(str.lastIndexOf(".") + 1);
            ContentResolver contentResolver = applicationContext.getContentResolver();
            String strSubstring2 = str.substring(str.lastIndexOf("/") + 1);
            File file = new File(str);
            if (file.exists()) {
                String parent = file.getParent();
                String strSubstring3 = parent.substring(parent.lastIndexOf("/") + 1);
                boolean z = strSubstring.equalsIgnoreCase("jpg") || strSubstring.equalsIgnoreCase("png");
                String str2 = Environment.DIRECTORY_DCIM + File.separator + strSubstring3;
                if (!str2.endsWith("/")) {
                    str2 = str2 + "/";
                }
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                if (z) {
                    cursorQuery2 = contentResolver.query(uri, new String[]{"_id"}, "relative_path=? and _display_name=?", new String[]{str2, strSubstring2}, null);
                } else {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    cursorQuery2 = contentResolver.query(uri, new String[]{"_id"}, "relative_path=? and _display_name=?", new String[]{str2, strSubstring2}, null);
                }
                if (cursorQuery2 != null) {
                    while (cursorQuery2.moveToFirst()) {
                        try {
                            contentResolver.delete(ContentUris.withAppendedId(uri, cursorQuery2.getLong(0)), null, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    File file2 = new File(str);
                    if (file2.isFile() && file2.exists()) {
                        file2.delete();
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        String strSubstring4 = str.substring(str.lastIndexOf(".") + 1);
        ContentResolver contentResolver2 = applicationContext.getContentResolver();
        Uri uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (strSubstring4.equalsIgnoreCase("jpg") || strSubstring4.equalsIgnoreCase("png")) {
            cursorQuery = contentResolver2.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{str}, null);
        } else {
            cursorQuery = contentResolver2.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=?", new String[]{str}, null);
            uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        if (cursorQuery != null) {
            try {
                if (cursorQuery.moveToFirst()) {
                    contentResolver2.delete(ContentUris.withAppendedId(uri2, cursorQuery.getLong(0)), null, null);
                }
            } catch (Exception unused) {
            }
            File file3 = new File(str);
            if (file3.isFile() && file3.exists()) {
                file3.delete();
            }
        }
    }

    public static String getFileNameFromDate(boolean z, boolean z2) {
        String str;
        String str2;
        String str3 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        if (z2) {
            str = sLocalPhoto;
            if (z) {
                str = sLocalVideo;
            }
        } else {
            str = null;
        }
        if (str == null) {
            return null;
        }
        if (z) {
            str2 = ".mp4";
        } else {
            str2 = ".png";
        }
        String str4 = str + "/";
        File file = new File(str4);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str4 + str3 + str2;
    }

    public static void StopAlert() {
        int i;
        if (music_Alert == -1 || (i = streamID_Alert) == 0) {
            return;
        }
        soundPool.stop(i);
        streamID_Alert = 0;
    }

    public static void PlayAlertMusic() {
        int i = music_Alert;
        if (i != -1) {
            streamID_Alert = soundPool.play(i, 1.0f, 1.0f, 0, -1, 1.0f);
        }
    }

    public static void F_PhotoMusic() {
        int i = music_photo;
        if (i != -1) {
            soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public static void F_OpenStream() {
        bUsbCamera = false;
        nStatus = 0;
        wifiCamera.naSetReceiveBmp(true);
        wifiCamera.naSetsquare(false);
        wifiCamera.naSetCircul(false);
        wifiCamera.naSetEnableRotate(false);
        wifiCamera.naEnableSensor(false);
        wifiCamera.naInit("");
        wifiCamera.naSetEnableEQ(true);
    }

    public static void F_StopStream() {
        nStatus = 0;
        wifiCamera.naStopRecordAll();
        wifiCamera.naStop();
    }

    public static boolean CheckResolutionSupport(int i, int i2) {
        MediaCodecInfo.CodecCapabilities capabilitiesForType;
        MediaCodecInfo.VideoCapabilities videoCapabilities;
        int codecCount = MediaCodecList.getCodecCount();
        for (int i3 = 0; i3 < codecCount; i3++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i3);
            if (codecInfoAt.isEncoder()) {
                for (String str : codecInfoAt.getSupportedTypes()) {
                    if (str.equalsIgnoreCase("video/avc") && (capabilitiesForType = codecInfoAt.getCapabilitiesForType("video/avc")) != null && (videoCapabilities = capabilitiesForType.getVideoCapabilities()) != null) {
                        return videoCapabilities.isSizeSupported(i, i2);
                    }
                }
            }
        }
        return false;
    }

    public static void forceSendRequestByWifiData(boolean z) {
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) singleton.getSystemService("connectivity");
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            if (!z) {
                connectivityManager.bindProcessToNetwork(null);
            } else {
                builder.addTransportType(1);
                connectivityManager.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() { // from class: com.joyhonest.wifi_check_adj.MyApp.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        Log.i("test", "已根据功能和传输类型找到合适的网络");
                        connectivityManager.bindProcessToNetwork(null);
                        connectivityManager.bindProcessToNetwork(network);
                        connectivityManager.unregisterNetworkCallback(this);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String int2ip(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i & 255).append(".");
        sb.append((i >> 8) & 255).append(".");
        sb.append((i >> 16) & 255);
        return sb.toString();
    }

    public static String getLocalIpAddress(Context context) {
        try {
            return int2ip(((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getIpAddress());
        } catch (Exception unused) {
            return "";
        }
    }

    public static boolean F_IsVaildModel(Context context, String... strArr) {
        boolean z = strArr != null;
        String[] strArr2 = {"192.168.25", "192.168.26", "192.168.27", "192.168.28", "192.168.29", "192.168.30", "192.168.31", "192.168.32", "192.168.33", "192.168.34", "192.168.123"};
        String localIpAddress = getLocalIpAddress(context);
        if (!z) {
            for (int i = 0; i < 11; i++) {
                if (!localIpAddress.equalsIgnoreCase(strArr2[i])) {
                }
            }
            return false;
        }
        for (String str : strArr) {
            if (!localIpAddress.equalsIgnoreCase(str)) {
            }
        }
        return false;
        return true;
    }

    public static boolean isAndroidP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    public static boolean isAndroidQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        EventBus.getDefault().register(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        EventBus.getDefault().unregister(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.activityAount++;
        bGotsystemActivity = false;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i = this.activityAount - 1;
        this.activityAount = i;
        if (i != 0 || bGotsystemActivity) {
            return;
        }
        EventBus.getDefault().post("", "Go2Background");
    }

    public static void F_ReadParaSet() {
        SharedPreferences sharedPreferences = singleton.getSharedPreferences("WifiCheck_Adj#2024_06_17", 0);
        Para_Brightness = sharedPreferences.getInt("Para_Brightness", 50);
        Para_Contrast = sharedPreferences.getInt("Para_Contrast", 50);
        Para_Saturation = sharedPreferences.getInt("Para_Saturation", 50);
        Para_R = sharedPreferences.getInt("Para_R", 50);
        Para_G = sharedPreferences.getInt("Para_G", 50);
        Para_B = sharedPreferences.getInt("Para_B", 50);
    }

    public static void F_SaveParaSet() {
        SharedPreferences.Editor editorEdit = singleton.getSharedPreferences("WifiCheck_Adj#2024_06_17", 0).edit();
        editorEdit.putInt("Para_Brightness", Para_Brightness);
        editorEdit.putInt("Para_Contrast", Para_Contrast);
        editorEdit.putInt("Para_Saturation", Para_Saturation);
        editorEdit.putInt("Para_R", Para_R);
        editorEdit.putInt("Para_G", Para_G);
        editorEdit.putInt("Para_B", Para_B);
        editorEdit.apply();
    }

    public static void F_SaveCheckFirstRun() {
        SharedPreferences.Editor editorEdit = singleton.getSharedPreferences("WifiCheck_Adj#2024_06_17", 0).edit();
        editorEdit.putBoolean("bFirstRun", false);
        editorEdit.apply();
    }

    public static boolean F_CheckFirstRun() {
        return singleton.getSharedPreferences("WifiCheck_Adj#2024_06_17", 0).getBoolean("bFirstRun", true);
    }
}
