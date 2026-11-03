package com.joyhonest.joycamera.sdk;

/* JADX INFO: loaded from: classes.dex */
public class jh_dowload_callback {
    public int nError;
    public int nPercentage;
    public String sError_Info;
    public String sFileName;

    public jh_dowload_callback(int i, String str, int i2) {
        this.sFileName = str;
        this.nPercentage = i;
        this.nError = i2;
        this.sError_Info = "";
    }

    public jh_dowload_callback(int i, String str) {
        this.sFileName = "";
        this.nPercentage = i;
        this.nError = 255;
        this.sError_Info = str;
    }
}
