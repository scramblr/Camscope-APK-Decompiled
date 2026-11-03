package com.joyhonest.joycamera.sdk;

/* JADX INFO: loaded from: classes.dex */
public class Worker extends Thread {
    private static final String TAG = "Worker";
    public boolean isRunning = false;

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        JoyAudioRecord.pts = 0L;
        this.isRunning = true;
        while (this.isRunning) {
            JoyLog.e(TAG, "read = " + JoyAudioRecord.audioRecord.read(JoyAudioRecord.mBuffer, 0, JoyAudioRecord.BUFFFER_SIZE) + " buffersize = " + JoyAudioRecord.BUFFFER_SIZE);
            JoyAudioRecord.AudioDataEncoder(JoyAudioRecord.mBuffer);
        }
    }
}
