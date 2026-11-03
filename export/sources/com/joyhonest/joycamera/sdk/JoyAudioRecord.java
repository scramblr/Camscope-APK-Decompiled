package com.joyhonest.joycamera.sdk;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.SystemClock;
import android.view.Surface;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import kotlin.time.DurationKt;

/* JADX INFO: loaded from: classes.dex */
class JoyAudioRecord {
    private static final int AUDIO_FORMAT = 2;
    public static int BUFFFER_SIZE = 2048;
    private static final int CHANNEL_MODE = 16;
    private static final int KEY_CHANNEL_COUNT = 1;
    private static final int KEY_SAMPLE_RATE = 16000;
    private static final String TAG = "JoyAudioRecord";
    public static byte[] mBuffer;
    private static final int nCt = (2048 * DurationKt.NANOS_IN_MILLIS) / 32000;
    private static int fps = 0;
    private static boolean bGAudio = false;
    private static boolean bRecording = false;
    private static boolean bMuxerStart = false;
    private static MediaMuxer mediaMuxer = null;
    public static AudioRecord audioRecord = null;
    private static MediaCodec videoMediaCode = null;
    private static MediaCodec audioMediaCode = null;
    public static long pts = 0;
    private static long pts_a = 0;
    private static long pts_ = 0;
    private static long nCountFrame = 0;
    private static long nCountFrameAudio = 0;
    private static int vIndex = -1;
    private static int aIndex = -1;
    private static Worker audioWorker = null;
    private static boolean bCanStartWrite = false;

    JoyAudioRecord() {
    }

    private static MediaFormat F_GetMediaFormat(int i, int i2, int i3, int i4, int i5) {
        MediaCodec mediaCodec = videoMediaCode;
        if (mediaCodec != null) {
            mediaCodec.stop();
            videoMediaCode.release();
            videoMediaCode = null;
        }
        fps = i4;
        pts_a = 0L;
        pts = 0L;
        pts_ = 0L;
        nCountFrame = 0L;
        nCountFrameAudio = 0L;
        try {
            videoMediaCode = MediaCodec.createEncoderByType("video/avc");
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat("video/avc", i, i2);
            mediaFormatCreateVideoFormat.setInteger("width", i);
            mediaFormatCreateVideoFormat.setInteger("height", i2);
            mediaFormatCreateVideoFormat.setInteger("bitrate", i3);
            mediaFormatCreateVideoFormat.setInteger("i-frame-interval", 1);
            mediaFormatCreateVideoFormat.setInteger("frame-rate", fps);
            mediaFormatCreateVideoFormat.setInteger("color-format", i5);
            mediaFormatCreateVideoFormat.setInteger("bitrate-mode", 1);
            try {
                videoMediaCode.configure(mediaFormatCreateVideoFormat, (Surface) null, (MediaCrypto) null, 1);
                return mediaFormatCreateVideoFormat;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (IOException | IllegalArgumentException | NullPointerException e2) {
            e2.printStackTrace();
            videoMediaCode = null;
            return null;
        }
    }

    static int InitVideo(int i, int i2, int i3, int i4) {
        int i5 = 21;
        if (F_GetMediaFormat(i, i2, i3, i4, 21) == null) {
            i5 = 19;
            if (F_GetMediaFormat(i, i2, i3, i4, 19) == null) {
                i5 = 0;
            }
        }
        if (i5 != 0) {
            videoMediaCode.start();
        } else {
            videoMediaCode.release();
            videoMediaCode = null;
        }
        return i5;
    }

    static long getRecordTimems() {
        int i;
        if (bRecording && (i = fps) > 0) {
            return (long) (nCountFrame * (1000.0f / i));
        }
        return 0L;
    }

    static int StartRecord(String str, int i, int i2, boolean z) {
        if (!Utility.naGetCameraisConnected()) {
            return -1;
        }
        if (bRecording) {
            return -2;
        }
        bCanStartWrite = false;
        if (i == 3) {
            StartRecord_A(str, false);
            Utility.naStartRecordV(i, i2);
            bRecording = true;
            return 0;
        }
        if (i == 0 || i == 2) {
            StartRecord_A(str, z);
            Utility.naStartRecordV(i, i2);
            bRecording = true;
        }
        if (i == 1) {
            Utility.naStartRecordV(i, i2);
        }
        return 0;
    }

    private static void StartRecord_A(String str, boolean z) {
        bGAudio = z;
        int i = 0;
        if (bRecording) {
            try {
                bRecording = false;
                bMuxerStart = false;
                MediaCodec mediaCodec = videoMediaCode;
                if (mediaCodec != null) {
                    mediaCodec.stop();
                    videoMediaCode.release();
                    videoMediaCode = null;
                }
                MediaCodec mediaCodec2 = audioMediaCode;
                if (mediaCodec2 != null) {
                    mediaCodec2.stop();
                    audioMediaCode.release();
                    audioMediaCode = null;
                }
                MediaMuxer mediaMuxer2 = mediaMuxer;
                if (mediaMuxer2 != null) {
                    mediaMuxer2.stop();
                    mediaMuxer.release();
                    mediaMuxer = null;
                }
            } catch (Exception unused) {
            }
        }
        try {
            mediaMuxer = new MediaMuxer(str, 0);
            bMuxerStart = false;
        } catch (Exception unused2) {
        }
        if (bGAudio && !isCanRecordAudio()) {
            bGAudio = false;
        }
        if (bGAudio) {
            try {
                audioMediaCode = MediaCodec.createEncoderByType("audio/mp4a-latm");
                MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", KEY_SAMPLE_RATE, 1);
                mediaFormatCreateAudioFormat.setInteger("bitrate", KEY_SAMPLE_RATE);
                mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
                audioMediaCode.configure(mediaFormatCreateAudioFormat, (Surface) null, (MediaCrypto) null, 1);
                audioMediaCode.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            StartRecordAudio(true);
            while (aIndex < 0) {
                SystemClock.sleep(10L);
                i++;
                if (i > 50) {
                    return;
                }
            }
            return;
        }
        StartRecordAudio(false);
    }

    private static void StartRecordAudio(boolean z) {
        if (z) {
            AudioRecord audioRecord2 = audioRecord;
            if (audioRecord2 != null) {
                try {
                    audioRecord2.stop();
                    audioRecord.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            int iMax = Math.max(BUFFFER_SIZE, AudioRecord.getMinBufferSize(KEY_SAMPLE_RATE, 16, 2));
            BUFFFER_SIZE = iMax;
            mBuffer = new byte[iMax];
            if (wifiCamera.getApplicationContext() != null) {
                if (ActivityCompat.checkSelfPermission(wifiCamera.getApplicationContext(), "android.permission.RECORD_AUDIO") != 0) {
                    AudioRecord audioRecord3 = new AudioRecord(1, KEY_SAMPLE_RATE, 16, 2, BUFFFER_SIZE);
                    audioRecord = audioRecord3;
                    audioRecord3.startRecording();
                    Worker worker = audioWorker;
                    if (worker != null && worker.isRunning) {
                        audioWorker.isRunning = false;
                        SystemClock.sleep(20L);
                    }
                    Worker worker2 = new Worker();
                    audioWorker = worker2;
                    worker2.start();
                    return;
                }
                return;
            }
            AudioRecord audioRecord4 = new AudioRecord(1, KEY_SAMPLE_RATE, 16, 2, BUFFFER_SIZE);
            audioRecord = audioRecord4;
            audioRecord4.startRecording();
            Worker worker3 = audioWorker;
            if (worker3 != null && worker3.isRunning) {
                audioWorker.isRunning = false;
                SystemClock.sleep(20L);
            }
            Worker worker4 = new Worker();
            audioWorker = worker4;
            worker4.start();
            return;
        }
        AudioRecord audioRecord5 = audioRecord;
        if (audioRecord5 != null) {
            try {
                audioRecord5.stop();
                audioRecord.release();
            } catch (Exception unused) {
            }
            audioRecord = null;
        }
        Worker worker5 = audioWorker;
        if (worker5 != null) {
            worker5.isRunning = false;
            SystemClock.sleep(50L);
            audioWorker = null;
        }
    }

    private static boolean isCanRecordAudio() {
        boolean z = false;
        try {
            int minBufferSize = AudioRecord.getMinBufferSize(KEY_SAMPLE_RATE, 16, 2) * 2;
            if (wifiCamera.getApplicationContext() != null) {
                if (ActivityCompat.checkSelfPermission(wifiCamera.getApplicationContext(), "android.permission.RECORD_AUDIO") != 0) {
                    return true;
                }
            } else {
                AudioRecord audioRecord2 = new AudioRecord(1, KEY_SAMPLE_RATE, 16, 2, minBufferSize);
                audioRecord = audioRecord2;
                audioRecord2.startRecording();
                audioRecord.stop();
                audioRecord.release();
                audioRecord = null;
                z = true;
            }
        } catch (Exception unused) {
        }
        return z;
    }

    private static void writeSampleData(boolean z, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        if (bMuxerStart) {
            if (z && !bCanStartWrite && (bufferInfo.flags & 1) != 0) {
                bCanStartWrite = true;
            }
            if (bCanStartWrite) {
                if (z) {
                    if (vIndex < 0 || (bufferInfo.flags & 2) != 0) {
                        return;
                    }
                    try {
                        bufferInfo.presentationTimeUs = (nCountFrame * 1000000) / ((long) fps);
                        mediaMuxer.writeSampleData(vIndex, byteBuffer, bufferInfo);
                        nCountFrame++;
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (aIndex >= 0) {
                    try {
                        if (bufferInfo.size > 10) {
                            bufferInfo.presentationTimeUs = nCountFrameAudio * ((long) nCt);
                            mediaMuxer.writeSampleData(aIndex, byteBuffer, bufferInfo);
                            nCountFrameAudio++;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private static void Addtrack(MediaFormat mediaFormat, boolean z) {
        MediaMuxer mediaMuxer2 = mediaMuxer;
        if (mediaMuxer2 == null || mediaFormat == null) {
            return;
        }
        if (z) {
            if (vIndex < 0) {
                vIndex = mediaMuxer2.addTrack(mediaFormat);
            }
            if (vIndex >= 0) {
                if (bGAudio) {
                    if (aIndex < 0 || bMuxerStart) {
                        return;
                    }
                    mediaMuxer.start();
                    bMuxerStart = true;
                    bCanStartWrite = false;
                    JoyLog.e(TAG, "Start --- vide = " + vIndex + " audio = " + aIndex);
                    return;
                }
                if (bMuxerStart) {
                    return;
                }
                mediaMuxer.start();
                bMuxerStart = true;
                bCanStartWrite = false;
                JoyLog.e(TAG, "Start --- vide = " + vIndex + " audio = " + aIndex);
                return;
            }
            return;
        }
        if (aIndex < 0) {
            int iAddTrack = mediaMuxer2.addTrack(mediaFormat);
            aIndex = iAddTrack;
            if (iAddTrack < 0 || vIndex < 0 || bMuxerStart) {
                return;
            }
            mediaMuxer.start();
            bMuxerStart = true;
            bCanStartWrite = false;
            JoyLog.e(TAG, "Start --- audio  vide = " + vIndex + " audio = " + aIndex);
        }
    }

    public static void VidoeDataEncoder(byte[] bArr) {
        int iDequeueInputBuffer;
        MediaCodec mediaCodec = videoMediaCode;
        if (mediaCodec != null && bRecording && (iDequeueInputBuffer = mediaCodec.dequeueInputBuffer(5000L)) >= 0) {
            if (fps <= 0) {
                fps = 25;
            }
            long j = pts;
            pts_ = ((long) (DurationKt.NANOS_IN_MILLIS / fps)) * j;
            pts = j + 1;
            ByteBuffer inputBuffer = videoMediaCode.getInputBuffer(iDequeueInputBuffer);
            inputBuffer.clear();
            inputBuffer.put(bArr);
            videoMediaCode.queueInputBuffer(iDequeueInputBuffer, 0, bArr.length, pts_, 0);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = videoMediaCode.dequeueOutputBuffer(bufferInfo, 10000L);
            if (iDequeueOutputBuffer == -2) {
                Addtrack(videoMediaCode.getOutputFormat(), true);
            }
            if (iDequeueOutputBuffer >= 0) {
                ByteBuffer outputBuffer = videoMediaCode.getOutputBuffer(iDequeueOutputBuffer);
                if (vIndex >= 0) {
                    try {
                        writeSampleData(true, outputBuffer, bufferInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                videoMediaCode.releaseOutputBuffer(iDequeueOutputBuffer, false);
            }
        }
    }

    public static void AudioDataEncoder(byte[] bArr) {
        int iDequeueInputBuffer;
        MediaCodec mediaCodec = audioMediaCode;
        if (mediaCodec != null && bRecording && (iDequeueInputBuffer = mediaCodec.dequeueInputBuffer(5000L)) >= 0) {
            long j = pts_a;
            long j2 = j * ((long) nCt);
            pts_a = j + 1;
            ByteBuffer inputBuffer = audioMediaCode.getInputBuffer(iDequeueInputBuffer);
            inputBuffer.clear();
            inputBuffer.put(bArr);
            audioMediaCode.queueInputBuffer(iDequeueInputBuffer, 0, bArr.length, j2, 0);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = audioMediaCode.dequeueOutputBuffer(bufferInfo, 10000L);
            if (iDequeueOutputBuffer == -2) {
                Addtrack(audioMediaCode.getOutputFormat(), false);
            }
            if (iDequeueOutputBuffer >= 0) {
                ByteBuffer outputBuffer = audioMediaCode.getOutputBuffer(iDequeueOutputBuffer);
                if (aIndex >= 0) {
                    try {
                        writeSampleData(false, outputBuffer, bufferInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                audioMediaCode.releaseOutputBuffer(iDequeueOutputBuffer, false);
            }
        }
    }

    public static int StopRecord(int i) {
        if (i == 1) {
            Utility.naStopRecordV(1);
        }
        if (i == 2 || i == 0) {
            if (i == 2) {
                Utility.naStopRecordV(2);
            }
            bRecording = false;
            JoyLog.e(TAG, "Count = " + nCountFrame);
            Utility.naStopRecordV(i);
            try {
                mediaMuxer.stop();
                mediaMuxer.release();
                File file = new File(GP4225_Device.sRecordFileName + "_.tmp");
                File file2 = new File(GP4225_Device.sRecordFileName);
                if (file2.exists() && file2.isFile()) {
                    file2.delete();
                }
                if (file.exists() && file.isFile()) {
                    file.renameTo(file2);
                }
                Utility.OnSnaporRecrodOK(GP4225_Device.sRecordFileName, 1);
                GP4225_Device.sRecordFileName = null;
            } catch (Exception unused) {
            }
            mediaMuxer = null;
            bCanStartWrite = false;
            Worker worker = audioWorker;
            if (worker != null) {
                worker.isRunning = false;
                try {
                    audioWorker.join(100L);
                } catch (Exception unused2) {
                }
                audioWorker = null;
            }
            if (bGAudio) {
                try {
                    audioRecord.stop();
                    audioRecord.release();
                } catch (Exception unused3) {
                }
                try {
                    audioMediaCode.stop();
                    audioMediaCode.release();
                } catch (Exception unused4) {
                }
            }
            try {
                videoMediaCode.stop();
                videoMediaCode.release();
            } catch (Exception unused5) {
            }
            videoMediaCode = null;
            audioMediaCode = null;
            audioRecord = null;
            mediaMuxer = null;
            bMuxerStart = false;
            aIndex = -1;
            vIndex = -1;
        }
        return 0;
    }

    public static int Init_MediaConvert(int i, int i2, int i3, int i4) {
        if (bRecording) {
            try {
                bRecording = false;
                bMuxerStart = false;
                MediaCodec mediaCodec = videoMediaCode;
                if (mediaCodec != null) {
                    mediaCodec.stop();
                    videoMediaCode.release();
                    videoMediaCode = null;
                }
                MediaCodec mediaCodec2 = audioMediaCode;
                if (mediaCodec2 != null) {
                    mediaCodec2.stop();
                    audioMediaCode.release();
                    audioMediaCode = null;
                }
                MediaMuxer mediaMuxer2 = mediaMuxer;
                if (mediaMuxer2 != null) {
                    mediaMuxer2.stop();
                    mediaMuxer.release();
                    mediaMuxer = null;
                }
            } catch (Exception unused) {
            }
        }
        return InitVideo(i, i2, i3, i4);
    }
}
