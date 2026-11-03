package com.joyhonest.Permission;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PermissionPageUtils {
    private final String TAG;
    private Context mContext;
    private String packageName;

    public PermissionPageUtils(Context context, String str) {
        this.TAG = "PermissionPageManager";
        this.mContext = context;
        this.packageName = str;
    }

    public PermissionPageUtils(Context context) {
        this.TAG = "PermissionPageManager";
        this.packageName = "com.joyhonest.wifi_check";
        this.mContext = context;
    }

    public void jumpPermissionPage() throws Throwable {
        String str = Build.MANUFACTURER;
        Log.e("PermissionPageManager", "jumpPermissionPage --- name : " + str);
        str.hashCode();
        switch (str) {
            case "Coolpad":
                goCoolpadMainager();
                break;
            case "Xiaomi":
                goXiaoMiMainager();
                break;
            case "LG":
                goLGMainager();
                break;
            case "OPPO":
                goOppoMainager();
                break;
            case "Sony":
                goSonyMainager();
                break;
            case "vivo":
                goVivoMainager();
                break;
            case "Meizu":
                goMeizuMainager();
                break;
            case "samsung":
                goSangXinMainager();
                break;
            case "HUAWEI":
                goHuaWeiMainager();
                break;
            default:
                goIntentSetting();
                break;
        }
    }

    private void goLGMainager() {
        try {
            Intent intent = new Intent(this.packageName);
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
            this.mContext.startActivity(intent);
        } catch (Exception unused) {
            goIntentSetting();
        }
    }

    private void goSonyMainager() {
        try {
            Intent intent = new Intent(this.packageName);
            intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
            this.mContext.startActivity(intent);
        } catch (Exception unused) {
            goIntentSetting();
        }
    }

    private void goHuaWeiMainager() {
        try {
            Intent intent = new Intent(this.packageName);
            intent.setFlags(268435456);
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            this.mContext.startActivity(intent);
        } catch (Exception unused) {
            goIntentSetting();
        }
    }

    private static String getMiuiVersion() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.miui.ui.version.name").getInputStream()), 1024);
            try {
                try {
                    String line = bufferedReader.readLine();
                    bufferedReader.close();
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return line;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        bufferedReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return null;
            }
        } catch (IOException e5) {
            e = e5;
            bufferedReader = null;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            bufferedReader.close();
            throw th;
        }
    }

    private void goXiaoMiMainager() throws Throwable {
        String miuiVersion = getMiuiVersion();
        Log.e("PermissionPageManager", "goMiaoMiMainager --- rom : " + miuiVersion);
        Intent intent = new Intent();
        if ("V6".equals(miuiVersion) || "V7".equals(miuiVersion)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", this.packageName);
        } else if ("V8".equals(miuiVersion) || "V9".equals(miuiVersion)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", this.packageName);
        } else {
            goIntentSetting();
        }
        this.mContext.startActivity(intent);
    }

    private void goMeizuMainager() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra("packageName", this.packageName);
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            goIntentSetting();
        }
    }

    private void goSangXinMainager() {
        goIntentSetting();
    }

    private void goIntentSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
        try {
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goOppoMainager() {
        doStartApplicationWithPackageName("com.coloros.safecenter");
    }

    private void goCoolpadMainager() {
        doStartApplicationWithPackageName("com.yulong.android.security:remote");
    }

    private void goVivoMainager() {
        doStartApplicationWithPackageName("com.bairenkeji.icaller");
    }

    private Intent getAppDetailSettingIntent() {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", this.mContext.getPackageName(), null));
        return intent;
    }

    private void doStartApplicationWithPackageName(String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = this.mContext.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(packageInfo.packageName);
        List<ResolveInfo> listQueryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(intent, 0);
        Log.e("PermissionPageManager", "resolveinfoList" + listQueryIntentActivities.size());
        for (int i = 0; i < listQueryIntentActivities.size(); i++) {
            Log.e("PermissionPageManager", listQueryIntentActivities.get(i).activityInfo.packageName + listQueryIntentActivities.get(i).activityInfo.name);
        }
        ResolveInfo next = listQueryIntentActivities.iterator().next();
        if (next != null) {
            String str2 = next.activityInfo.packageName;
            String str3 = next.activityInfo.name;
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.LAUNCHER");
            intent2.setComponent(new ComponentName(str2, str3));
            try {
                this.mContext.startActivity(intent2);
            } catch (Exception e2) {
                goIntentSetting();
                e2.printStackTrace();
            }
        }
    }
}
