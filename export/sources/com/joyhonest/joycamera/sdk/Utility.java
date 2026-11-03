package com.joyhonest.joycamera.sdk;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioTrack;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class Utility {
    private static final String TAG = "Utility";
    private static AudioTrack audioTrack = null;
    static boolean bJavaUdp = false;
    private static Bitmap bmpG = null;
    private static Bitmap bmpPre = null;
    static ByteBuffer mDirectBuffer = null;
    static ByteBuffer mDirectUsbCameraYuvBuffer = null;
    public static String sCameraModel = "";

    private static native void _naSetFileName(String str);

    static native boolean naGetCameraisConnected();

    static native int naStartRecordV(int i, int i2);

    static native void naStopRecordV(int i);

    private static int InitVideoMediacode(int i, int i2, int i3, int i4) {
        return GP4225_Device.InitVideo(i, i2, i3, i4);
    }

    private static int Init_MediaConvert(int i, int i2, int i3, int i4) {
        return JoyAudioRecord.Init_MediaConvert(i, i2, i3, i4);
    }

    private static void VidoeDataEncoder(byte[] bArr) {
        JoyAudioRecord.VidoeDataEncoder(bArr);
    }

    private static void DownloadFile_callback(int i, String str, int i2) {
        EventBus.getDefault().post(new jh_dowload_callback(i, str, i2), "DownloadFile");
    }

    private static void onGetLedPwm(int i) {
        Integer numValueOf = Integer.valueOf(i);
        EventBus.getDefault().post(numValueOf, "onGetLed");
        EventBus.getDefault().post(numValueOf, "onGetLedPWM");
    }

    private static void onAdjFocus(int i) {
        EventBus.getDefault().post(Integer.valueOf(i), "onAdjFocus");
    }

    private static void onGetKey(int i) {
        JoyLog.e(TAG, "Get Key = " + i);
        EventBus.getDefault().post(Integer.valueOf(i), "onGetKey");
    }

    private static void onGetFrame(int i, int i2) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        mDirectBuffer.rewind();
        bitmapCreateBitmap.copyPixelsFromBuffer(mDirectBuffer);
        EventBus.getDefault().post(bitmapCreateBitmap, "onGetFrame");
    }

    private static void onUdpRevData(byte[] bArr, int i) {
        if (i == 20001 && (bJavaUdp || !GP4225_Device.GP4225_PressData(bArr))) {
            EventBus.getDefault().post(bArr, "onGet20001PortData");
        }
        if (i == 20000) {
            if (bJavaUdp) {
                EventBus.getDefault().post(bArr, "onGet20000PortData");
            } else {
                if (GP4225_Device.GP4225_PressData_20000(bArr)) {
                    return;
                }
                EventBus.getDefault().post(bArr, "onGet20000PortData");
            }
        }
    }

    static void OnSnaporRecrodOK(String str, int i) {
        if (str != null) {
            String str2 = String.format(Locale.ENGLISH, "%02d%s", Integer.valueOf(i), str);
            if (i == 0) {
                if (sCameraModel.length() > 2) {
                    try {
                        ExifInterface exifInterface = new ExifInterface(str);
                        exifInterface.setAttribute("Model", sCameraModel);
                        exifInterface.saveAttributes();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (wifiCamera.photodest == 1) {
                    F_Save2ToGallery(str);
                    wifiCamera.photodest = -1;
                    str2 = String.format(Locale.ENGLISH, "%02d%s", 3, str);
                }
            } else if (wifiCamera.videodest == 1) {
                F_Save2ToGallery(str);
                wifiCamera.videodest = -1;
                str2 = String.format(Locale.ENGLISH, "%02d%s", 3, str);
            }
            EventBus.getDefault().post(str2, "SavePhotoOK");
        }
    }

    private static void onStatusChange(int i) {
        EventBus.getDefault().post(Integer.valueOf(i), "onCameraStatusChange");
    }

    private static void OnPlayStatus(int i) {
        EventBus.getDefault().post(Integer.valueOf(i), "OnPlayStatus");
    }

    private static void SentPlayDuration(long j) {
        EventBus.getDefault().post(Integer.valueOf((int) j), "onDuration");
    }

    private static void SentPlayTime(long j) {
        EventBus.getDefault().post(Integer.valueOf((int) j), "onPlaytime");
    }

    public static boolean isAndroidQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public static void ClearTempDir() {
        File externalFilesDir;
        if (wifiCamera.getApplicationContext() == null || (externalFilesDir = wifiCamera.getApplicationContext().getExternalFilesDir("temp")) == null || !externalFilesDir.exists()) {
            return;
        }
        externalFilesDir.delete();
    }

    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    static void F_CreateLocalDir(String str) {
        if (wifiCamera.getApplicationContext() == null) {
            return;
        }
        wifiCamera.sAlbum = str;
        if (isAndroidQ()) {
            ClearTempDir();
            File externalFilesDir = wifiCamera.getApplicationContext().getExternalFilesDir(str);
            if (externalFilesDir != null) {
                wifiCamera.sLocalPath = externalFilesDir.getAbsolutePath();
                return;
            }
            return;
        }
        try {
            String str2 = String.format("%s/%s", getNormalSDCardPath(), str);
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            wifiCamera.sLocalPath = str2;
        } catch (Exception unused) {
        }
    }

    public static String getFileNameFromDate(boolean z) {
        String str;
        if (wifiCamera.sLocalPath == null) {
            return null;
        }
        String str2 = new SimpleDateFormat("yyyy-MM-dd-HHmmssSSS", Locale.getDefault()).format(new Date());
        if (wifiCamera.sFileNamePre.length() > 0) {
            str2 = wifiCamera.sFileNamePre + "-" + str2;
        }
        if (z) {
            str = "mp4";
        } else {
            str = "jpg";
        }
        _naSetFileName(str2 + "." + str);
        String str3 = wifiCamera.sLocalPath;
        File file = new File(str3);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str3 + "/" + str2 + "." + str;
    }

    public static List<Uri> F_GetAllLocalFiles(boolean z) {
        Cursor cursorQuery;
        if (wifiCamera.getApplicationContext() == null) {
            return null;
        }
        String str = Environment.DIRECTORY_DCIM + File.separator + wifiCamera.sAlbum;
        String str2 = wifiCamera.sLocalPath;
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = wifiCamera.getApplicationContext().getApplicationContext().getContentResolver();
        if (!str.endsWith("/")) {
            str = str + "/";
        }
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (!z) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        if (isAndroidQ()) {
            cursorQuery = contentResolver.query(uri, new String[]{"_id"}, "relative_path = ?", new String[]{str}, null);
        } else {
            cursorQuery = contentResolver.query(uri, new String[]{"_id"}, "_data like ?", new String[]{"%" + str2 + "/%"}, null);
        }
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                arrayList.add(ContentUris.withAppendedId(uri, cursorQuery.getLong(0)));
            }
            cursorQuery.close();
        }
        return arrayList;
    }

    public static Uri F_CheckIsExit(String str) {
        if (wifiCamera.getApplicationContext() == null) {
            return null;
        }
        String strSubstring = str.substring(str.lastIndexOf(".") + 1);
        return F_CheckIsExit(str, (strSubstring.equalsIgnoreCase("mp4") || strSubstring.equalsIgnoreCase("avi") || strSubstring.equalsIgnoreCase("mov")) ? false : true);
    }

    private static Uri F_CheckIsExit(String str, boolean z) {
        Cursor cursorQuery;
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String str2 = Environment.DIRECTORY_DCIM + File.separator + wifiCamera.sAlbum;
        ContentResolver contentResolver = wifiCamera.getApplicationContext().getContentResolver();
        if (!str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (!z) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        if (isAndroidQ()) {
            cursorQuery = contentResolver.query(uri, new String[]{"_id"}, "relative_path=? and _display_name=?", new String[]{str2, strSubstring}, null);
        } else {
            cursorQuery = contentResolver.query(uri, new String[]{"_id"}, "_data like ?", new String[]{"%" + wifiCamera.sLocalPath + "/" + strSubstring + "%"}, null);
        }
        if (cursorQuery != null) {
            if (cursorQuery.moveToNext()) {
                Uri uriWithAppendedId = ContentUris.withAppendedId(uri, cursorQuery.getLong(0));
                cursorQuery.close();
                return uriWithAppendedId;
            }
            cursorQuery.close();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v9 */
    public static int F_Save2ToGallery(String str) throws Throwable {
        Uri uriInsert;
        ?? r2;
        ?? r3;
        if (wifiCamera.getApplicationContext() == null) {
            return -1;
        }
        File file = new File(str);
        if (!file.exists()) {
            return -2;
        }
        boolean z = true;
        String strSubstring = str.substring(str.lastIndexOf("/") + 1);
        String strSubstring2 = str.substring(str.lastIndexOf(".") + 1);
        if (!strSubstring2.equalsIgnoreCase("jpg") && !strSubstring2.equalsIgnoreCase("png") && !strSubstring2.equalsIgnoreCase("bmp")) {
            if (!strSubstring2.equalsIgnoreCase("mp4") && !strSubstring2.equalsIgnoreCase("mov") && !strSubstring2.equalsIgnoreCase("avi")) {
                return -3;
            }
            z = false;
        }
        String str2 = Environment.DIRECTORY_DCIM + File.separator + wifiCamera.sAlbum;
        ContentResolver contentResolver = wifiCamera.getApplicationContext().getContentResolver();
        ContentValues contentValues = new ContentValues();
        String str3 = "video/mov";
        if (isAndroidQ()) {
            if (F_CheckIsExit(str) != null) {
                return -4;
            }
            if (z) {
                contentValues.put("_display_name", strSubstring);
                boolean zEqualsIgnoreCase = strSubstring2.equalsIgnoreCase("png");
                if (zEqualsIgnoreCase) {
                    contentValues.put("mime_type", "image/png");
                } else {
                    contentValues.put("mime_type", "image/jpeg");
                }
                contentValues.put("relative_path", str2);
                uriInsert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                r2 = zEqualsIgnoreCase;
            } else {
                contentValues.put("_display_name", strSubstring);
                if (strSubstring2.equalsIgnoreCase("mov")) {
                    contentValues.put("mime_type", "video/mov");
                } else if (strSubstring2.equalsIgnoreCase("avi")) {
                    contentValues.put("mime_type", "video/avi");
                } else {
                    contentValues.put("mime_type", "video/mp4");
                }
                contentValues.put("relative_path", str2);
                uriInsert = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                r2 = str3;
            }
            if (uriInsert == null) {
                return 0;
            }
            try {
                try {
                    OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                    try {
                        r2 = file;
                        try {
                            FileInputStream fileInputStream = new FileInputStream((File) r2);
                            byte[] bArr = new byte[102400];
                            while (true) {
                                int i = fileInputStream.read(bArr);
                                if (i == -1) {
                                    break;
                                }
                                outputStreamOpenOutputStream.write(bArr, 0, i);
                            }
                            fileInputStream.close();
                            r3 = r2;
                            if (outputStreamOpenOutputStream != null) {
                                outputStreamOpenOutputStream.close();
                                r3 = r2;
                            }
                        } catch (Throwable th) {
                            th = th;
                            Throwable th2 = th;
                            if (outputStreamOpenOutputStream == null) {
                                throw th2;
                            }
                            try {
                                outputStreamOpenOutputStream.close();
                                throw th2;
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                                throw th2;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        r2 = file;
                    }
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    r3 = r2;
                    return !r3.isFile() ? 0 : 0;
                }
            } catch (IOException e2) {
                e = e2;
                r2 = file;
                e.printStackTrace();
                r3 = r2;
            }
            if (!r3.isFile() && r3.exists()) {
                r3.delete();
                return 0;
            }
        }
        try {
            if (z) {
                if (strSubstring2.equalsIgnoreCase("png")) {
                    contentValues.put("mime_type", "image/png");
                } else {
                    contentValues.put("mime_type", "image/jpeg");
                }
                contentValues.put("mime_type", "image/jpeg");
                contentValues.put("_data", str);
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                return 0;
            }
            if (strSubstring2.equalsIgnoreCase("mov")) {
                contentValues.put("mime_type", "video/mov");
            } else if (strSubstring2.equalsIgnoreCase("avi")) {
                contentValues.put("mime_type", "video/avi");
            } else {
                contentValues.put("mime_type", "video/mp4");
            }
            contentValues.put("_data", str);
            contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            return 0;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
            return 0;
        }
    }

    public static void DeleteImages(String[] strArr, Activity activity) {
        String string;
        if (wifiCamera.getApplicationContext() == null || strArr == null || strArr.length == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = wifiCamera.getApplicationContext().getApplicationContext().getContentResolver();
        int i = 0;
        if (isAndroidQ()) {
            if (Build.VERSION.SDK_INT >= 30) {
                int length = strArr.length;
                while (i < length) {
                    arrayList.add(Uri.parse(strArr[i]));
                    i++;
                }
                try {
                    activity.startIntentSenderForResult(MediaStore.createDeleteRequest(contentResolver, arrayList).getIntentSender(), 202201, null, 0, 0, 0);
                    return;
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                    return;
                }
            }
            int length2 = strArr.length;
            while (i < length2) {
                try {
                    contentResolver.delete(Uri.parse(strArr[i]), null, null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                i++;
            }
            return;
        }
        for (String str : strArr) {
            Uri uri = Uri.parse(str);
            try {
                Cursor cursorQuery = contentResolver.query(uri, new String[]{"_data"}, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst() && (string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"))) != null) {
                    File file = new File(string);
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                }
            } catch (Exception unused) {
            }
            try {
                contentResolver.delete(uri, null, null);
            } catch (Exception unused2) {
            }
        }
    }

    public static int DeleteImage(String str) {
        boolean z;
        String string;
        if (wifiCamera.getApplicationContext() == null) {
            return -1;
        }
        Uri uri = Uri.parse(str);
        ContentResolver contentResolver = wifiCamera.getApplicationContext().getApplicationContext().getContentResolver();
        if (isAndroidQ()) {
            try {
                contentResolver.delete(uri, null, null);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        boolean z2 = true;
        try {
            Cursor cursorQuery = contentResolver.query(uri, new String[]{"_data"}, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst() && (string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"))) != null) {
                File file = new File(string);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            z = true;
        } catch (Exception unused) {
            z = false;
        }
        try {
            contentResolver.delete(uri, null, null);
        } catch (Exception unused2) {
            z2 = false;
        }
        return (z && z2) ? 0 : -1;
    }

    public static void F_StartPlayAudio(int i, int i2) {
        AudioTrack audioTrack2 = audioTrack;
        if (audioTrack2 != null) {
            try {
                audioTrack2.stop();
                audioTrack.release();
                audioTrack = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            AudioTrack audioTrack3 = new AudioTrack(Integer.MIN_VALUE, i, 12, i2, AudioTrack.getMinBufferSize(i, 12, i2), 1);
            audioTrack = audioTrack3;
            audioTrack3.play();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void F_StopPlayAudio() {
        AudioTrack audioTrack2 = audioTrack;
        if (audioTrack2 != null) {
            try {
                audioTrack2.stop();
                audioTrack.release();
                audioTrack = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void WriteAudioData(byte[] bArr) {
        AudioTrack audioTrack2 = audioTrack;
        if (audioTrack2 != null) {
            try {
                audioTrack2.write(bArr, 0, bArr.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
