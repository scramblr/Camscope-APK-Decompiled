package com.joyhonest.joycamera.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import java.io.File;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class wifiCamera {
    private static final int BMP_Len = 50333696;
    public static final int CAMERA_FILELIST_MODE = 1;
    public static final int CAMERA_NORMAL_MODE = 0;
    private static final int CmdLen = 2048;
    public static final int DEST_GALLERY = 1;
    public static final int DEST_SNADBOX = 0;
    private static final String TAG = "wifiCamera";
    public static final int TYPE_BOTH_PHONE_SD = 2;
    public static final int TYPE_CONVERT = 3;
    public static final int TYPE_DEST_GALLERY = 1;
    public static final int TYPE_DEST_SNADBOX = 0;
    public static final int TYPE_ONLY_PHONE = 0;
    public static final int TYPE_ONLY_SD = 1;
    public static final int TYPE_PHOTO = 3;
    public static final int TYPE_VIDEO = 1;
    private static Context applicationContext;
    static int photoType;
    static int photodest;
    public static String sAlbum;
    public static String sFileNamePre;
    public static String sLocalPath;
    static int videoType;
    static int videodest;

    private static native int Joy_Convert(String str, String str2);

    private static native boolean StartPlayAudioNative();

    private static native void StopPlayAudioNative();

    public static native void eglchangeLayout(int i, int i2);

    public static native void egldrawFrame();

    public static native void eglinit();

    public static native void eglrelease();

    public static native boolean isPhoneRecording();

    public static native void na4225_FormatSD();

    public static native void na4225_ReadFireWareVer();

    public static native void na4225_ReadOsd();

    public static native void na4225_ReadRecordTime();

    public static native void na4225_ReadReversal();

    public static native void na4225_ReadTime();

    public static native void na4225_ResetDevice();

    public static native void na4225_SetOsd(boolean z);

    public static native void na4225_SetRecordTime(int i);

    public static native void na4225_SetReversal(boolean z);

    public static native void na4225_SyncTime(byte[] bArr, int i);

    public static native void naAddUsbYuvBuffer(ByteBuffer byteBuffer, int i, int i2);

    public static native int naConvert(String str, String str2);

    public static native int naDeleteSDFile(String str);

    public static native int naEliminateBlackBorder(int i, int i2, int i3, int i4);

    public static native void naEnableSensor(boolean z);

    public static native int naGetAdjFocusValue();

    public static native void naGetBattery();

    public static native void naGetBatteryInfo();

    public static native String naGetCameraIP();

    public static native void naGetCameraPara();

    public static native void naGetDeviceCategory();

    public static native void naGetLedPWM();

    public static native int naGetSdFliesList(int i, int i2, int i3);

    public static native int naGetSensorSensitivity();

    public static native void naGetSystemControlData();

    private static native int naGetVideoThumbnailB(String str, Bitmap bitmap);

    public static native void naGetWifiPassword();

    public static native void naGetWifiResolution();

    public static native void naGetWifiSSID();

    public static native int naGetZoomeFocus();

    private static native int naInitA(String str);

    public static native boolean naIsJoyCamera();

    public static native int naPlayPuse();

    public static native int naPlayResume();

    public static native int naPlayVideo(String str);

    public static native void naReadDeviceInfo();

    public static native void naReadDeviceStatus();

    public static native int naReadIR();

    public static native int naReadPIR();

    public static native void naReadStatusLedDisp();

    public static native void naRebootDevice();

    public static native void naResetDeviceDefault();

    public static native int naSeek(int i);

    public static native int naSentCmd(byte[] bArr, int i);

    public static native void naSet3DDenoiser(boolean z);

    public static native void naSet3DDenoiserParams(float f, float f2, float f3, float f4);

    public static native int naSetAdjFocusValue(int i);

    public static native void naSetAdjGsensorData(boolean z);

    public static native void naSetBlueChannel(int i);

    public static native void naSetBrightness(float f);

    public static native void naSetCameraDataRota(int i);

    public static native void naSetCameraStation(boolean z);

    public static native int naSetCheckTransferProtocol(boolean z);

    public static native void naSetCircul(boolean z);

    public static native void naSetColortemperature(boolean z);

    public static native void naSetColortemperatureValue(int i);

    public static native void naSetContrast(float f);

    public static native int naSetDeviceMode(int i);

    private static native void naSetDirectBuffer(Object obj, int i);

    public static native void naSetEV(int i);

    public static native void naSetEnableEQ(boolean z);

    public static native void naSetEnableRotate(boolean z);

    public static native void naSetFilterRotate(float f);

    public static native void naSetFlip(boolean z);

    public static native void naSetGreenChannel(int i);

    public static native void naSetGsensor2SDK(int i, int i2, int i3);

    public static native void naSetGsensorType(int i);

    public static native int naSetIR(int i);

    public static native void naSetLedPWM(byte b);

    public static native void naSetLightFreq(boolean z);

    public static native void naSetMirror(boolean z);

    public static native void naSetOsdFileName(boolean z);

    public static native void naSetOsdFontFilePath(String str);

    public static native void naSetOsdTextColor(int i, int i2);

    public static native void naSetOsdTextOffset(int i, int i2);

    public static native void naSetOsdTextSize(int i);

    public static native int naSetPIR(boolean z);

    public static native int naSetPicWaterMark(String str, boolean z, float f, float f2);

    public static native void naSetReadStatusDelay(int i);

    public static native void naSetReceiveBmp(boolean z);

    public static native int naSetRecordWH(int i, int i2);

    public static native void naSetRedChannel(int i);

    public static native void naSetSaturation(float f);

    public static native void naSetScal(float f);

    public static native void naSetSensorSensitivity(int i, int i2);

    public static native void naSetSharpeningLevel(float f);

    public static native void naSetStatusLedDisp(boolean z);

    public static native void naSetStyle(int i);

    public static native void naSetSystemControlData(byte[] bArr);

    public static native void naSetTimeOsd(int i, int i2);

    public static native void naSetTypeInspectis(boolean z);

    public static native void naSetVR(boolean z);

    public static native void naSetVRwhiteBack(boolean z);

    public static native int naSetWifiPassword(String str);

    public static native void naSetWifiResolution(int i);

    public static native void naSetWifiSSID(String str);

    public static native void naSetWorkNedId(int i);

    public static native int naSetZoomFocus(int i);

    public static native void naSetsquare(boolean z);

    private static native int naSnapPhotoA(String str, int i, int i2);

    public static native void naStartAdjFocus(int i, int i2);

    public static native int naStartDonwPlay(String str, int i);

    public static native int naStartDownLoad(String str, int i, String str2);

    public static native int naStartUdpService(boolean z);

    private static native int naStopA();

    public static native int naStopDownLoadOrPlay();

    public static native boolean naTimeWaterMarkYUV(byte[] bArr, int i, int i2, int i3);

    public static void naSetApplicationContext(Context context) {
        applicationContext = context.getApplicationContext();
    }

    static Context getApplicationContext() {
        return applicationContext;
    }

    static {
        System.loadLibrary("joycamera");
        Utility.mDirectBuffer = ByteBuffer.allocateDirect(50335744);
        naSetDirectBuffer(Utility.mDirectBuffer, 50335744);
        JoyLog.SetDebug(true);
        photoType = -1;
        photodest = -1;
        videoType = -1;
        videodest = -1;
        sFileNamePre = "";
    }

    public static int naInit(String str) {
        naStop();
        return naInitA(str);
    }

    public static int naStop() {
        StopPlayAudioNative();
        Utility.F_StopPlayAudio();
        naStopRecordAll();
        naStopA();
        return 0;
    }

    public static int naSnapPhoto(String str, int i, int i2) {
        String strTrim;
        int iLastIndexOf;
        photoType = i;
        photodest = i2;
        if (i2 == 1) {
            String fileNameFromDate = Utility.getFileNameFromDate(false);
            if (str != null && (iLastIndexOf = (strTrim = str.trim()).lastIndexOf("/")) >= 0) {
                fileNameFromDate = sLocalPath + "/" + strTrim.substring(iLastIndexOf + 1);
            }
            return naSnapPhotoA(fileNameFromDate, i, i2);
        }
        return naSnapPhotoA(str, i, i2);
    }

    public static int naStartRecord(String str, int i, int i2, boolean z) {
        String str2 = str + "_.tmp";
        videoType = i;
        videodest = i2;
        if (i2 == 1) {
            str = Utility.getFileNameFromDate(true);
            str2 = str + "_.tmp";
        }
        int iStartRecord = GP4225_Device.StartRecord(str2, i, i2, z);
        if (iStartRecord == 0) {
            GP4225_Device.sRecordFileName = str;
        }
        return iStartRecord;
    }

    public static long naGetRecordTimems() {
        return GP4225_Device.getRecordTimems();
    }

    public static int naStopRecord(int i) {
        return GP4225_Device.StopRecord(i);
    }

    public static int naStopRecordAll() {
        return JoyAudioRecord.StopRecord(2);
    }

    public static int F_Convert(String str, String str2) {
        File file = new File(str2);
        if (file.exists()) {
            file.delete();
        }
        if (new File(str).exists()) {
            return Joy_Convert(str, str2);
        }
        return -100;
    }

    public static Bitmap naGetVideoThumbnail(String str) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        if (naGetVideoThumbnailB(str, bitmapCreateBitmap) == 0) {
            return bitmapCreateBitmap;
        }
        return null;
    }

    public static void naGetFirewareVer() {
        na4225_ReadFireWareVer();
    }

    public static void naCreateLocalDir(String str) {
        Utility.F_CreateLocalDir(str);
    }

    public static void naStartPlayAudio(int i, int i2) {
        Utility.F_StartPlayAudio(i, i2);
        StartPlayAudioNative();
    }

    public static void naStopPlayAudio() {
        StopPlayAudioNative();
        Utility.F_StopPlayAudio();
    }

    public static void naSetCameraModel2SnapPhoto(String str) {
        Utility.sCameraModel = str;
    }
}
