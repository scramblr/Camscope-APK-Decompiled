package com.joyhonest.joycamera.sdk;

/* JADX INFO: loaded from: classes.dex */
public class GP4225_Device {
    public static byte CustermerType = 0;
    public static byte DeviceType = 0;
    public static int LockedCount = 0;
    public static byte[] MacAddress = null;
    public static int PhotoCount = 0;
    public static int VideosCount = 0;
    public static boolean bAdjfocus = false;
    public static boolean bSD = false;
    public static boolean bSDRecording = false;
    public static int nBattery = 0;
    public static int nFuncMask = 0;
    public static int nMode = 0;
    public static long nSDAllSize = 0;
    public static long nSDAvaildSize = 0;
    public static int nSDRecordTime = 0;
    static String sRecordFileName = null;
    public static String sVer = "";

    static boolean GP4225_PressData(byte[] bArr) {
        return JoyProcessData.PressData(bArr);
    }

    static boolean GP4225_PressData_20000(byte[] bArr) {
        return JoyProcessData.PressData_20000(bArr);
    }

    static int StartRecord(String str, int i, int i2, boolean z) {
        return JoyAudioRecord.StartRecord(str, i, i2, z);
    }

    static long getRecordTimems() {
        return JoyAudioRecord.getRecordTimems();
    }

    static int StopRecord(int i) {
        return JoyAudioRecord.StopRecord(i);
    }

    static int InitVideo(int i, int i2, int i3, int i4) {
        return JoyAudioRecord.InitVideo(i, i2, i3, i4);
    }
}
