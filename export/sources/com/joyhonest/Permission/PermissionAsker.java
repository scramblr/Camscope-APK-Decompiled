package com.joyhonest.Permission;

import android.app.Activity;
import androidx.core.app.ActivityCompat;

/* JADX INFO: loaded from: classes.dex */
public class PermissionAsker {
    private static final Runnable RUN = new Runnable() { // from class: com.joyhonest.Permission.PermissionAsker.1
        @Override // java.lang.Runnable
        public void run() {
        }
    };
    private Runnable mDeniRun;
    private Runnable mOkRun;
    private int mReqCode;

    public PermissionAsker() {
        Runnable runnable = RUN;
        this.mOkRun = runnable;
        this.mDeniRun = runnable;
        this.mReqCode = 1;
    }

    public PermissionAsker(int i, Runnable runnable, Runnable runnable2) {
        this.mReqCode = i;
        this.mOkRun = runnable;
        this.mDeniRun = runnable2;
    }

    public void setReqCode(int i) {
        this.mReqCode = i;
    }

    public void setSuccedCallback(Runnable runnable) {
        this.mOkRun = runnable;
    }

    public void setFailedCallback(Runnable runnable) {
        this.mDeniRun = runnable;
    }

    public PermissionAsker askPermission(Activity activity, String... strArr) {
        int iCheckSelfPermission = 0;
        for (String str : strArr) {
            iCheckSelfPermission += ActivityCompat.checkSelfPermission(activity, str);
        }
        if (iCheckSelfPermission == 0) {
            this.mOkRun.run();
        } else {
            ActivityCompat.requestPermissions(activity, strArr, this.mReqCode);
        }
        return this;
    }

    public void onRequestPermissionsResult(int[] iArr) {
        boolean z = true;
        for (int i : iArr) {
            z &= i == 0;
        }
        if (iArr.length > 0 && z) {
            this.mOkRun.run();
        } else {
            this.mDeniRun.run();
        }
    }
}
