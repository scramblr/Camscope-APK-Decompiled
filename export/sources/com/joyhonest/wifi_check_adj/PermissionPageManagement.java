package com.joyhonest.wifi_check_adj;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class PermissionPageManagement {
    private static final String MANUFACTURER_HUAWEI = "HUAWEI";
    private static final String MANUFACTURER_LENOVO = "LENOVO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_MEIZU = "Meizu";
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_SAMSUNG = "samsung";
    private static final String MANUFACTURER_SONY = "Sony";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";
    private static final String MANUFACTURER_YULONG = "YuLong";
    private static final String MANUFACTURER_ZTE = "ZTE";
    private static final String TAG = "JumpPermissionManagement";

    public static void goToSetting(Activity activity) {
        String str = Build.MANUFACTURER;
        str.hashCode();
        switch (str) {
            case "Xiaomi":
                Xiaomi(activity);
                break;
            case "LG":
                LG(activity);
                break;
            case "OPPO":
                OPPO(activity);
                break;
            case "Sony":
                Sony(activity);
                break;
            case "vivo":
                VIVO(activity);
                break;
            case "Meizu":
                Meizu(activity);
                break;
            case "HUAWEI":
                Huawei(activity);
                break;
            default:
                ApplicationInfo(activity);
                Log.e("goToSetting", "目前暂不支持此系统");
                break;
        }
    }

    public static void Huawei(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.putExtra("packageName", activity.getApplicationInfo().packageName);
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    public static void Meizu(Activity activity) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra("packageName", activity.getPackageName());
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    public static void Xiaomi(Activity activity) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.putExtra("extra_pkgname", activity.getPackageName());
            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity"));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    public static void Sony(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.putExtra("packageName", activity.getPackageName());
            intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    public static void OPPO(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            intent.putExtra("packageName", activity.getPackageName());
            intent.setComponent(new ComponentName("com.coloros.securitypermission", "com.coloros.securitypermission.permission.PermissionAppAllPermissionActivity"));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    public static void VIVO(Activity activity) {
        if ((Build.MODEL.contains("Y85") && !Build.MODEL.contains("Y85A")) || Build.MODEL.contains("vivo Y53L")) {
            Intent intent = new Intent();
            intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
            intent.putExtra("packagename", activity.getPackageName());
            intent.putExtra("tabId", "1");
            activity.startActivity(intent);
            return;
        }
        Intent intent2 = new Intent();
        intent2.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        intent2.setAction("secure.intent.action.softPermissionDetail");
        intent2.putExtra("packagename", activity.getPackageName());
        activity.startActivity(intent2);
    }

    public static void LG(Activity activity) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setFlags(268435456);
            intent.putExtra("packageName", activity.getPackageName());
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    public static void _360(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(268435456);
        intent.putExtra("packageName", activity.getPackageName());
        intent.setComponent(new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity"));
        activity.startActivity(intent);
    }

    public static void ApplicationInfo(Activity activity) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }

    public static void SystemConfig(Activity activity) {
        activity.startActivity(new Intent("android.settings.SETTINGS"));
    }

    private static void goIntentSetting(Activity activity) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
