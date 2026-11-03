package com.joyhonest.wifi_check_adj;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.internal.view.SupportMenu;
import com.joyhonest.Permission.PermissionAsker;
import com.joyhonest.Permission.PermissionPageUtils;
import com.joyhonest.joycamera.sdk.GP4225_Device;
import com.joyhonest.joycamera.sdk.wifiCamera;
import com.joyhonest.wifi_check_adj.databinding.ActivityMainBinding;
import java.util.Locale;
import kotlin.UByte;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static Handler RecHandler = null;
    private static String TAG = "Camscope";
    static final int nNoSelectColor = -16777216;
    private static int nRev = 0;
    static final int nSelectColor = -16678289;
    static Handler openHandler;
    private AlertDialog alertDialog;
    ActivityMainBinding binding;
    private PermissionAsker mAsker;
    private Handler mCheckScreenHandler;
    MyDialog myDialog;
    private HandlerThread thread1;
    private float scal = 1.0f;
    boolean bMirro = false;
    int nScal = 0;
    int nRota = 0;
    int nResolutionSel = 0;
    Handler openCameraHandler = null;
    private final int TYPE_CHECK = 0;
    private final int TYPE_TC = 1;
    private final int TYPE_WIFI_TEST = 2;
    boolean bSorpot500M = false;
    private int nAsk = -1;
    boolean bChecScreen = false;
    String strRecTime = "00:00";
    Runnable RecRunnable = new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity.3
        boolean bhalf = true;
        int nStep = 0;

        @Override // java.lang.Runnable
        public void run() {
            int i;
            if ((MyApp.nStatus & 2) != 0) {
                int iNaGetRecordTimems = (int) (wifiCamera.naGetRecordTimems() / 1000);
                int i2 = iNaGetRecordTimems / 60;
                if (i2 > 59) {
                    i = i2 / 60;
                    i2 %= 60;
                } else {
                    i = 0;
                }
                int i3 = iNaGetRecordTimems % 60;
                if (i == 0) {
                    MainActivity.this.strRecTime = String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i3));
                } else {
                    MainActivity.this.strRecTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
                }
                MainActivity.this.binding.RecTimeView.setText(MainActivity.this.strRecTime);
                if ((this.nStep & 1) == 0) {
                    if (this.bhalf) {
                        MainActivity.this.binding.butVideo.setBackgroundResource(R.mipmap.video_icon_active);
                    } else {
                        MainActivity.this.binding.butVideo.setBackgroundResource(R.mipmap.video_icon);
                    }
                    this.bhalf = !this.bhalf;
                }
                this.nStep++;
            }
            MainActivity.RecHandler.postDelayed(this, 250L);
        }
    };
    int[] nRotaArray = {0, 90, 180, 270};
    boolean b3D = false;
    int nSetPara = -1;
    int[] daA = {R.id.btn_Resolution1, R.id.btn_Resolution2, R.id.btn_Resolution3, R.id.btn_Resolution4, R.id.btn_Resolution5, R.id.btn_Resolution6, R.id.btn_Resolution7};
    boolean bIsChanged = false;
    int nRetry = 0;
    private final Handler checkWifiCameraHander = new Handler();
    private final Runnable checkWifiCameraRunnable = new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity.4
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.nRev > 40) {
                MainActivity.this.nRetry++;
                if (MainActivity.this.nRetry > 4) {
                    MainActivity.this.nRetry = 4;
                    EventBus.getDefault().post("", "DispInvalid");
                    return;
                }
                if (MainActivity.this.nRetry != 1) {
                    MainActivity.this.F_Open_();
                    Log.e(MainActivity.TAG, "retry2");
                    int unused = MainActivity.nRev = 15;
                } else {
                    MyApp.F_StopStream();
                    MyApp.forceSendRequestByWifiData(true);
                    int unused2 = MainActivity.nRev = 20;
                }
                Log.d("BmpTest", "retry: " + MainActivity.this.nRetry);
            }
            MainActivity.nRev();
            if (MainActivity.this.bCheckWifi) {
                MainActivity.this.checkWifiCameraHander.postDelayed(this, 50L);
            }
        }
    };
    private boolean bCheckWifi = false;
    private final Handler dispScanHandler = new Handler();
    boolean bKeyProcessed_1 = false;
    boolean bKeyProcessed_2 = false;
    boolean bKeyProcessed_4 = false;
    boolean bKeyProcessed_5 = false;
    boolean bKeyProcessed_28 = false;
    boolean bKeyProcessed_29 = false;
    boolean bKeyProcessed_30 = false;
    long preTime = System.currentTimeMillis();
    long preTime2 = System.currentTimeMillis();
    long preTime3 = System.currentTimeMillis();
    byte TogglePre = -1;
    int nStyle = 0;
    int[] da = {R.mipmap.battery_0, R.mipmap.battery_1, R.mipmap.battery_2, R.mipmap.battery_3};

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApp.bNeedAsk = true;
        ActivityMainBinding activityMainBindingInflate = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding = activityMainBindingInflate;
        setContentView(activityMainBindingInflate.getRoot());
        this.mCheckScreenHandler = new Handler(getMainLooper());
        MyApp.F_ReadParaSet();
        this.mAsker = new PermissionAsker(10, new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m165lambda$onCreate$0$comjoyhonestwifi_check_adjMainActivity();
            }
        }, new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m166lambda$onCreate$1$comjoyhonestwifi_check_adjMainActivity();
            }
        });
        this.binding.ViewSetting.setVisibility(8);
        String string = getString(R.string.warning);
        String string2 = getString(R.string.permission_msg);
        this.alertDialog = new AlertDialog.Builder(this).setTitle(string).setMessage(string2).setNegativeButton(getString(R.string.OK), new DialogInterface.OnClickListener() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws Throwable {
                this.f$0.m167lambda$onCreate$2$comjoyhonestwifi_check_adjMainActivity(dialogInterface, i);
            }
        }).create();
        MyApp.nStatus = 0;
        MyApp.F_GetSettings();
        this.openCameraHandler = new Handler(getMainLooper());
        this.nResolutionSel = MyApp.nResolutionSel;
        MyApp.checkDeviceHasNavigationBar(this);
        TAG = "Camscope";
        wifiCamera.naCreateLocalDir("Camscope");
        wifiCamera.naSetMirror(this.bMirro);
        this.binding.tvScale.setVisibility(8);
        this.binding.batteryView.setVisibility(4);
        this.binding.SensorWarnView.setVisibility(4);
        this.binding.ConnectedView.setVisibility(0);
        this.binding.mainSettings.setVisibility(8);
        this.binding.ResolutionSelectView.setVisibility(8);
        this.binding.paraSet.setOnClickListener(this);
        this.binding.ResolutionSet.setOnClickListener(this);
        this.binding.toolbar.post(new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity.1
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.F_GetLiuhaiScreenGraplandscape();
            }
        });
        if (MyApp.CheckResolutionSupport(640, 480)) {
            this.binding.btnResolution1.setVisibility(0);
            this.binding.line1.setVisibility(0);
        } else {
            this.binding.btnResolution1.setVisibility(8);
            this.binding.line1.setVisibility(8);
        }
        if (!MyApp.CheckResolutionSupport(1280, 720)) {
            this.binding.btnResolution2.setVisibility(8);
            this.binding.line2.setVisibility(8);
        } else {
            this.binding.btnResolution2.setVisibility(0);
            this.binding.line2.setVisibility(0);
        }
        if (!MyApp.CheckResolutionSupport(1600, 1200)) {
            this.binding.btnResolution3.setVisibility(8);
            this.binding.line3.setVisibility(8);
        } else {
            this.binding.btnResolution3.setVisibility(0);
            this.binding.line3.setVisibility(0);
        }
        if (!MyApp.CheckResolutionSupport(1920, 1080)) {
            this.binding.btnResolution4.setVisibility(8);
            this.binding.line4.setVisibility(8);
        } else {
            this.binding.btnResolution4.setVisibility(0);
            this.binding.line4.setVisibility(0);
        }
        if (!MyApp.CheckResolutionSupport(2560, 1440)) {
            this.binding.btnResolution5.setVisibility(8);
            this.binding.line5.setVisibility(8);
        } else {
            this.binding.btnResolution5.setVisibility(0);
            this.binding.line5.setVisibility(0);
        }
        if (!MyApp.CheckResolutionSupport(2592, 1944)) {
            this.binding.btnResolution6.setVisibility(8);
            this.binding.line6.setVisibility(8);
        } else {
            this.binding.btnResolution6.setVisibility(0);
            this.binding.line6.setVisibility(0);
        }
        if (!MyApp.CheckResolutionSupport(3840, 2160)) {
            this.binding.btnResolution7.setVisibility(8);
            this.binding.line7.setVisibility(8);
        } else {
            this.binding.btnResolution7.setVisibility(0);
            this.binding.line7.setVisibility(0);
        }
        this.binding.RecTimeView.setVisibility(4);
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.thread1 = handlerThread;
        handlerThread.start();
        openHandler = new Handler(this.thread1.getLooper());
        RecHandler = new Handler(getMainLooper());
        F_SetClickListener();
        this.binding.DispplayImage.setOnClickListener(this);
        this.binding.ResolutionSelectView.setVisibility(8);
        F_SetResolution();
        this.binding.butEar.setVisibility(4);
        F_DispEar();
        StartcheckScreen();
        F_DispSetParaView();
    }

    /* JADX INFO: renamed from: lambda$onCreate$1$com-joyhonest-wifi_check_adj-MainActivity, reason: not valid java name */
    /* synthetic */ void m166lambda$onCreate$1$comjoyhonestwifi_check_adjMainActivity() {
        this.alertDialog.show();
    }

    /* JADX INFO: renamed from: lambda$onCreate$2$com-joyhonest-wifi_check_adj-MainActivity, reason: not valid java name */
    /* synthetic */ void m167lambda$onCreate$2$comjoyhonestwifi_check_adjMainActivity(DialogInterface dialogInterface, int i) throws Throwable {
        PermissionPageUtils permissionPageUtils = new PermissionPageUtils(this);
        MyApp.bGotsystemActivity = true;
        permissionPageUtils.jumpPermissionPage();
    }

    private void StopCheckScreen() {
        this.bChecScreen = false;
        this.mCheckScreenHandler.removeCallbacksAndMessages(null);
    }

    private void StartcheckScreen() {
        this.mCheckScreenHandler.removeCallbacksAndMessages(null);
        this.bChecScreen = true;
        this.mCheckScreenHandler.post(new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity.2
            @Override // java.lang.Runnable
            public void run() {
                MainActivity.this.F_GetLiuhaiScreenGraplandscape();
                if (MainActivity.this.bChecScreen) {
                    MainActivity.this.mCheckScreenHandler.postDelayed(this, 250L);
                }
            }
        });
    }

    public void F_GetLiuhaiScreenGraplandscape() {
        DisplayCutout displayCutout;
        WindowInsets rootWindowInsets = getWindow().getDecorView().getRootWindowInsets();
        if (rootWindowInsets == null || Build.VERSION.SDK_INT < 28 || (displayCutout = rootWindowInsets.getDisplayCutout()) == null) {
            return;
        }
        if (getResources().getConfiguration().orientation == 2) {
            WindowManager windowManager = (WindowManager) getSystemService("window");
            if (windowManager != null) {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                if (defaultDisplay.getRotation() == 1 || defaultDisplay.getRotation() == 3) {
                    int safeInsetRight = displayCutout.getSafeInsetRight();
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.binding.toolbar.getLayoutParams();
                    layoutParams.rightMargin = safeInsetRight;
                    this.binding.toolbar.setLayoutParams(layoutParams);
                    int safeInsetLeft = displayCutout.getSafeInsetLeft();
                    ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.binding.ViewSetting.getLayoutParams();
                    layoutParams2.leftMargin = safeInsetLeft;
                    this.binding.ViewSetting.setLayoutParams(layoutParams2);
                    return;
                }
                return;
            }
            return;
        }
        int i = getResources().getConfiguration().orientation;
    }

    private void F_StartDispRecTime(boolean z) {
        if (z) {
            this.binding.RecTimeView.setText("00:00");
            this.binding.RecTimeView.setVisibility(0);
            RecHandler.removeCallbacksAndMessages(null);
            RecHandler.post(this.RecRunnable);
            return;
        }
        this.binding.RecTimeView.setVisibility(4);
        RecHandler.removeCallbacksAndMessages(null);
        this.binding.butVideo.setBackgroundResource(R.mipmap.video_icon);
    }

    private void F_SetClickListener() {
        this.binding.butPhoto.setOnClickListener(this);
        this.binding.butVideo.setOnClickListener(this);
        this.binding.butBrow.setOnClickListener(this);
        this.binding.butRota.setOnClickListener(this);
        this.binding.butMirr.setOnClickListener(this);
        this.binding.butSetup.setOnClickListener(this);
        this.binding.butEar.setOnClickListener(this);
        this.binding.BrightnessBtn.setOnClickListener(this);
        this.binding.ContrastBtn.setOnClickListener(this);
        this.binding.SaturationBtn.setOnClickListener(this);
        this.binding.RColorBtn.setOnClickListener(this);
        this.binding.GColorBtn.setOnClickListener(this);
        this.binding.BColorBtn.setOnClickListener(this);
        this.binding.btnReset.setOnClickListener(this);
        this.binding.btnResolution1.setTextColor(nSelectColor);
        this.binding.btnResolution1.setOnClickListener(this);
        this.binding.btnResolution2.setOnClickListener(this);
        this.binding.btnResolution3.setOnClickListener(this);
        this.binding.btnResolution4.setOnClickListener(this);
        this.binding.btnResolution5.setOnClickListener(this);
        this.binding.btnResolution6.setOnClickListener(this);
        this.binding.btnResolution7.setOnClickListener(this);
    }

    public void F_CheckPermissions(int i) {
        this.nAsk = i;
        if (Build.VERSION.SDK_INT >= 34) {
            this.mAsker.askPermission(this, "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        } else if (Build.VERSION.SDK_INT == 33) {
            this.mAsker.askPermission(this, "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_IMAGES");
        } else {
            this.mAsker.askPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE");
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.binding.butPhoto) {
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                return;
            }
            F_CheckPermissions(0);
        }
        if (view == this.binding.butVideo) {
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                return;
            }
            F_CheckPermissions(1);
        }
        if (view == this.binding.butBrow) {
            MyApp.F_PlayBtnVoice();
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                return;
            }
            F_CheckPermissions(2);
        }
        if (view == this.binding.butRota) {
            MyApp.F_PlayBtnVoice();
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                return;
            }
            F_SetRota();
        }
        if (view == this.binding.butMirr || view == this.binding.butEar) {
            MyApp.F_PlayBtnVoice();
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                return;
            } else {
                this.bMirro = !this.bMirro;
                if (view == this.binding.butEar) {
                    F_DispEar();
                }
                wifiCamera.naSetMirror(this.bMirro);
            }
        }
        if (view == this.binding.butSetup) {
            MyApp.F_PlayBtnVoice();
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                return;
            } else {
                if (this.binding.ViewSetting.getVisibility() == 0) {
                    this.binding.ViewSetting.setVisibility(8);
                    return;
                }
                this.binding.mainSettings.setVisibility(0);
            }
        }
        if (view == this.binding.DispplayImage) {
            MyApp.F_PlayBtnVoice();
            if (this.binding.ResolutionSelectView.getVisibility() == 0 || this.binding.mainSettings.getVisibility() == 0 || this.binding.ViewSetting.getVisibility() == 0) {
                this.binding.ResolutionSelectView.setVisibility(8);
                this.binding.mainSettings.setVisibility(8);
                this.binding.ViewSetting.setVisibility(8);
                return;
            }
            Show_hid_toolbar(isConnected());
        }
        if (view == this.binding.btnResolution1) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 0;
            F_SetResolution();
        }
        if (view == this.binding.btnResolution2) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 1;
            F_SetResolution();
        }
        if (view == this.binding.btnResolution3) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 2;
            F_SetResolution();
        }
        if (view == this.binding.btnResolution4) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 3;
            F_SetResolution();
        }
        if (view == this.binding.btnResolution5) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 4;
            F_SetResolution();
        }
        if (view == this.binding.btnResolution6) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 5;
            F_SetResolution();
        }
        if (view == this.binding.btnResolution7) {
            MyApp.F_PlayBtnVoice();
            this.nResolutionSel = 6;
            F_SetResolution();
        }
        if (this.binding.paraSet == view) {
            MyApp.F_PlayBtnVoice();
            this.binding.mainSettings.setVisibility(8);
            this.binding.ViewSetting.setVisibility(0);
            this.nSetPara = -1;
            F_DispSetParaView();
        }
        if (this.binding.ResolutionSet == view) {
            MyApp.F_PlayBtnVoice();
            this.binding.mainSettings.setVisibility(8);
            this.binding.ResolutionSelectView.setVisibility(0);
        }
        if (this.binding.BrightnessBtn == view) {
            MyApp.F_PlayBtnVoice();
            if (this.nSetPara == 0) {
                this.nSetPara = -1;
            } else {
                this.nSetPara = 0;
            }
            F_DispSetParaView();
        }
        if (this.binding.ContrastBtn == view) {
            MyApp.F_PlayBtnVoice();
            if (this.nSetPara == 1) {
                this.nSetPara = -1;
            } else {
                this.nSetPara = 1;
            }
            F_DispSetParaView();
        }
        if (this.binding.SaturationBtn == view) {
            MyApp.F_PlayBtnVoice();
            if (this.nSetPara == 2) {
                this.nSetPara = -1;
            } else {
                this.nSetPara = 2;
            }
            F_DispSetParaView();
        }
        if (this.binding.RColorBtn == view) {
            MyApp.F_PlayBtnVoice();
            if (this.nSetPara == 3) {
                this.nSetPara = -1;
            } else {
                this.nSetPara = 3;
            }
            F_DispSetParaView();
        }
        if (this.binding.GColorBtn == view) {
            MyApp.F_PlayBtnVoice();
            if (this.nSetPara == 4) {
                this.nSetPara = -1;
            } else {
                this.nSetPara = 4;
            }
            F_DispSetParaView();
        }
        if (this.binding.BColorBtn == view) {
            MyApp.F_PlayBtnVoice();
            if (this.nSetPara == 5) {
                this.nSetPara = -1;
            } else {
                this.nSetPara = 5;
            }
            F_DispSetParaView();
        }
        if (view == this.binding.btnReset) {
            MyApp.Para_Saturation = 50;
            MyApp.Para_Contrast = 50;
            MyApp.Para_Brightness = 50;
            MyApp.Para_R = 50;
            MyApp.Para_G = 50;
            MyApp.Para_B = 50;
            MyApp.F_SaveParaSet();
            F_SetColor(3);
            F_SetColor(4);
            F_SetColor(5);
            F_SetBrightness();
            F_SetContrast();
            F_Setsaturation();
            this.nSetPara = -1;
            F_DispSetParaView();
        }
    }

    private void F_DispSetParaView() {
        this.binding.BrightnessBtn.setbSelected(false);
        this.binding.ContrastBtn.setbSelected(false);
        this.binding.SaturationBtn.setbSelected(false);
        this.binding.RColorBtn.setbSelected(false);
        this.binding.GColorBtn.setbSelected(false);
        this.binding.BColorBtn.setbSelected(false);
        if (this.nSetPara == -1) {
            this.binding.mySlider.setVisibility(8);
        } else {
            this.binding.mySlider.setVisibility(0);
        }
        int i = this.nSetPara;
        if (i == 0) {
            this.binding.BrightnessBtn.sValue = "" + MyApp.Para_Brightness;
            this.binding.BrightnessBtn.setbSelected(true);
            this.binding.mySlider.setnValue(MyApp.Para_Brightness);
            this.binding.mySlider.setTrackimg(R.mipmap.brightness_sel);
            return;
        }
        if (i == 1) {
            this.binding.ContrastBtn.sValue = "" + MyApp.Para_Contrast;
            this.binding.ContrastBtn.setbSelected(true);
            this.binding.mySlider.setnValue(MyApp.Para_Contrast);
            this.binding.mySlider.setTrackimg(R.mipmap.contrast_sel);
            return;
        }
        if (i == 2) {
            this.binding.SaturationBtn.sValue = "" + MyApp.Para_Saturation;
            this.binding.SaturationBtn.setbSelected(true);
            this.binding.mySlider.setnValue(MyApp.Para_Saturation);
            this.binding.mySlider.setTrackimg(R.mipmap.saturation_sel);
            return;
        }
        if (i == 3) {
            this.binding.RColorBtn.sValue = "" + MyApp.Para_R;
            this.binding.RColorBtn.setbSelected(true);
            this.binding.mySlider.setnValue(MyApp.Para_R);
            this.binding.mySlider.setTrackimg(R.mipmap.r_sel);
            return;
        }
        if (i == 4) {
            this.binding.GColorBtn.sValue = "" + MyApp.Para_G;
            this.binding.GColorBtn.setbSelected(true);
            this.binding.mySlider.setnValue(MyApp.Para_G);
            this.binding.mySlider.setTrackimg(R.mipmap.g_sel);
            return;
        }
        if (i == 5) {
            this.binding.BColorBtn.sValue = "" + MyApp.Para_B;
            this.binding.BColorBtn.setbSelected(true);
            this.binding.mySlider.setnValue(MyApp.Para_B);
            this.binding.mySlider.setTrackimg(R.mipmap.b_sel);
        }
    }

    private void F_DispEar() {
        if (this.bMirro) {
            if (GP4225_Device.DeviceType == 5) {
                this.binding.butEar.setBackgroundResource(R.mipmap.left_ear);
            }
            if (GP4225_Device.DeviceType == 6) {
                this.binding.butEar.setBackgroundResource(R.mipmap.mirr_icon);
                return;
            }
            return;
        }
        if (GP4225_Device.DeviceType == 5) {
            this.binding.butEar.setBackgroundResource(R.mipmap.right_ear);
        }
        if (GP4225_Device.DeviceType == 6) {
            this.binding.butEar.setBackgroundResource(R.mipmap.mirr_icon);
        }
    }

    private void DispResolution() {
        for (int i = 0; i < 7; i++) {
            if (i == this.nResolutionSel) {
                ((Button) findViewById(this.daA[i])).setTextColor(nSelectColor);
            } else {
                ((Button) findViewById(this.daA[i])).setTextColor(-16777216);
            }
        }
    }

    private void F_SetResolution() {
        if ((MyApp.nStatus & 2) != 0) {
            return;
        }
        int i = this.nResolutionSel;
        if (i == 6) {
            wifiCamera.naSetRecordWH(3840, 2160);
        } else if (i == 5) {
            wifiCamera.naSetRecordWH(2592, 1944);
        } else if (i == 4) {
            wifiCamera.naSetRecordWH(2560, 1440);
        } else if (i == 3) {
            wifiCamera.naSetRecordWH(1920, 1080);
        } else if (i == 2) {
            wifiCamera.naSetRecordWH(1600, 1200);
        } else if (i == 1) {
            wifiCamera.naSetRecordWH(1280, 720);
        } else {
            this.nResolutionSel = 0;
            wifiCamera.naSetRecordWH(640, 480);
        }
        DispResolution();
        MyApp.nResolutionSel = this.nResolutionSel;
        MyApp.F_SavwSettings();
    }

    private void Show_hid_toolbar(boolean z) {
        View viewFindViewById = findViewById(R.id.toolbar);
        View viewFindViewById2 = findViewById(R.id.toolbar1);
        if (viewFindViewById != null) {
            int width = viewFindViewById.getWidth();
            int translationX = (int) viewFindViewById.getTranslationX();
            if (z) {
                if (translationX == 0) {
                    ObjectAnimator.ofFloat(viewFindViewById, "TranslationX", width).setDuration(200L).start();
                } else {
                    ObjectAnimator.ofFloat(viewFindViewById, "TranslationX", 0.0f).setDuration(200L).start();
                }
            } else if (translationX != 0) {
                ObjectAnimator.ofFloat(viewFindViewById, "TranslationX", 0.0f).setDuration(2L).start();
            }
        }
        if (viewFindViewById2 != null) {
            int width2 = viewFindViewById2.getWidth();
            int translationX2 = (int) viewFindViewById2.getTranslationX();
            if (!z) {
                if (translationX2 != 0) {
                    ObjectAnimator.ofFloat(viewFindViewById2, "TranslationX", 0.0f).setDuration(2L).start();
                }
            } else if (translationX2 == 0) {
                ObjectAnimator.ofFloat(viewFindViewById2, "TranslationX", -width2).setDuration(200L).start();
            } else {
                ObjectAnimator.ofFloat(viewFindViewById2, "TranslationX", 0.0f).setDuration(200L).start();
            }
        }
    }

    private boolean isConnected() {
        return (MyApp.nStatus & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: F_GetPermissions, reason: merged with bridge method [inline-methods] */
    public void m165lambda$onCreate$0$comjoyhonestwifi_check_adjMainActivity() {
        wifiCamera.naCreateLocalDir(TAG);
        if (MyApp.F_CheckFirstRun()) {
            SystemClock.sleep(500L);
            MyApp.F_SaveCheckFirstRun();
        }
        int i = this.nAsk;
        if (i == 0) {
            F_TakePhoto();
        } else if (i == 1) {
            StartOrStopRecord();
        } else if (i == 2) {
            MyApp.F_PlayBtnVoice();
            startActivity(new Intent(this, (Class<?>) BrowSelectActivity.class));
            overridePendingTransition(0, 0);
        }
        this.nAsk = -1;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Destroy ----");
        MyApp.bNeedAsk = true;
        this.openCameraHandler.removeCallbacksAndMessages(null);
        this.bCheckWifi = false;
        this.checkWifiCameraHander.removeCallbacksAndMessages(null);
        StopCheckScreen();
        MyApp.F_StopStream();
        if (this.binding.SensorWarnView.getVisibility() == 0) {
            this.binding.SensorWarnView.setVisibility(8);
        }
        MyApp.StopAlert();
        this.dispScanHandler.removeCallbacksAndMessages(null);
        MyApp.nStatus = 0;
        MyDialog myDialog = this.myDialog;
        if (myDialog != null && myDialog.isShowing()) {
            this.myDialog.dismiss();
        }
        openHandler.removeCallbacksAndMessages(null);
        RecHandler.removeCallbacksAndMessages(null);
        this.thread1.quit();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v6 */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Locale locale = getResources().getConfiguration().locale;
        String upperCase = locale.getLanguage().toUpperCase();
        locale.getScript().toUpperCase();
        ?? Contains = upperCase.contains("ZH");
        if (upperCase.contains("JP")) {
            Contains = 2;
        }
        if (Contains == 1) {
            this.binding.txtReset.setTextSize(1, 18.0f);
        } else if (Contains == 1) {
            this.binding.txtReset.setTextSize(1, 12.0f);
        } else {
            this.binding.txtReset.setTextSize(1, 16.0f);
        }
        GP4225_Device.DeviceType = (byte) 0;
        MyApp.checkDeviceHasNavigationBar(this);
        this.bIsChanged = false;
        MyApp.forceSendRequestByWifiData(true);
        this.openCameraHandler.removeCallbacksAndMessages(null);
        this.openCameraHandler.postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m168lambda$onResume$3$comjoyhonestwifi_check_adjMainActivity();
            }
        }, 250L);
    }

    /* JADX INFO: renamed from: lambda$onResume$3$com-joyhonest-wifi_check_adj-MainActivity, reason: not valid java name */
    /* synthetic */ void m168lambda$onResume$3$comjoyhonestwifi_check_adjMainActivity() {
        if (this.bIsChanged) {
            return;
        }
        if ((MyApp.nStatus & 1) == 0) {
            this.binding.ConnectedView.setVisibility(0);
        }
        F_Open_();
        F_CehckWifiCameraStatus(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void F_Open_() {
        this.bIsChanged = true;
        wifiCamera.naIsJoyCamera();
        if ((1 & MyApp.nStatus) == 0) {
            MyApp.F_StopStream();
            this.binding.butEar.setVisibility(4);
            this.binding.butMirr.setVisibility(0);
            Log.e(TAG, "device type = " + ((int) GP4225_Device.DeviceType));
            openHandler.removeCallbacksAndMessages(null);
            openHandler.postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m164lambda$F_Open_$4$comjoyhonestwifi_check_adjMainActivity();
                }
            }, 100L);
        }
    }

    /* JADX INFO: renamed from: lambda$F_Open_$4$com-joyhonest-wifi_check_adj-MainActivity, reason: not valid java name */
    /* synthetic */ void m164lambda$F_Open_$4$comjoyhonestwifi_check_adjMainActivity() {
        MyApp.F_OpenStream();
        F_SetColor(3);
        F_SetColor(4);
        F_SetColor(5);
        F_SetBrightness();
        F_SetContrast();
        F_Setsaturation();
        F_SetResolution();
        wifiCamera.naSetMirror(false);
        this.nRota--;
        F_SetRota();
    }

    @Subscriber(tag = "onSensorWarn_res")
    private void onSensorWarn_res(byte[] bArr) {
        if (bArr != null) {
            if (((byte) (bArr[0] & UByte.MAX_VALUE)) == 1) {
                if (this.binding.SensorWarnView.getVisibility() != 0) {
                    this.binding.SensorWarnView.setVisibility(0);
                    MyApp.PlayAlertMusic();
                    return;
                }
                return;
            }
            if (this.binding.SensorWarnView.getVisibility() == 0) {
                this.binding.SensorWarnView.setVisibility(8);
            }
            MyApp.StopAlert();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void nRev() {
        nRev++;
    }

    private void F_CehckWifiCameraStatus(boolean z) {
        nRev = 0;
        if (z) {
            this.nRetry = 0;
        }
        this.bCheckWifi = false;
        this.checkWifiCameraHander.removeCallbacksAndMessages(null);
        this.bCheckWifi = true;
        this.checkWifiCameraHander.post(this.checkWifiCameraRunnable);
    }

    @Subscriber(tag = "onGetFrame")
    private void ReceiveBMP(Bitmap bitmap) {
        nRev = 0;
        this.nRetry = 0;
        this.binding.DispplayImage.setImageBitmap(bitmap);
        this.binding.ConnectedView.setVisibility(8);
        MyDialog myDialog = this.myDialog;
        if (myDialog == null || !myDialog.isShowing()) {
            return;
        }
        this.myDialog.dismiss();
    }

    @Subscriber(tag = "DispInvalid")
    private void DispInvalid(String str) {
        this.binding.ConnectedView.setVisibility(8);
        this.binding.DispplayImage.setImageBitmap(null);
        if (this.myDialog == null) {
            this.myDialog = new MyDialog(this, R.style.MyDialog);
        }
        this.myDialog.setTitle(R.string.Tips);
        this.myDialog.setMessage(R.string.Please_connected_device);
        String string = getString(R.string.OK);
        getString(R.string.Cancel);
        this.myDialog.setYesOnclickListener(string, new MyDialog.onClickListener() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda0
            @Override // com.joyhonest.wifi_check_adj.MyDialog.onClickListener
            public final void onClick() {
                this.f$0.m163lambda$DispInvalid$5$comjoyhonestwifi_check_adjMainActivity();
            }
        });
        this.myDialog.show();
    }

    /* JADX INFO: renamed from: lambda$DispInvalid$5$com-joyhonest-wifi_check_adj-MainActivity, reason: not valid java name */
    /* synthetic */ void m163lambda$DispInvalid$5$comjoyhonestwifi_check_adjMainActivity() {
        MyApp.bGotsystemActivity = true;
        this.nRetry = 0;
        MyApp.bGotsystemActivity = true;
        Intent intent = new Intent();
        try {
            intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
            startActivity(intent);
        } catch (Exception unused) {
            intent.setAction("android.settings.WIFI_SETTINGS");
            startActivity(intent);
        }
        overridePendingTransition(0, 0);
        this.myDialog.dismiss();
    }

    @Subscriber(tag = "onCameraStatusChange")
    private void _OnStatusChanged(int i) {
        Log.e("   Status", "" + i);
        Log.d("BmpTest", "status: " + i);
        if ((i & 1) != 0) {
            if ((MyApp.nStatus & 1) == 0) {
                F_SetResolution();
            }
            MyApp.nStatus |= 1;
        }
        if ((i & 2) != 0 && (MyApp.nStatus & 2) == 0) {
            MyApp.nStatus |= 2;
            F_StartDispRecTime(true);
        } else if ((MyApp.nStatus & 2) != 0) {
            MyApp.nStatus &= -3;
            F_StartDispRecTime(false);
        }
    }

    void showScale(String str) {
        this.binding.tvScale.setText(str);
        this.binding.tvScale.setVisibility(0);
        this.dispScanHandler.removeCallbacksAndMessages(null);
        this.dispScanHandler.postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.MainActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m169lambda$showScale$6$comjoyhonestwifi_check_adjMainActivity();
            }
        }, 500L);
    }

    /* JADX INFO: renamed from: lambda$showScale$6$com-joyhonest-wifi_check_adj-MainActivity, reason: not valid java name */
    /* synthetic */ void m169lambda$showScale$6$comjoyhonestwifi_check_adjMainActivity() {
        this.binding.tvScale.setVisibility(8);
    }

    private void F_SetRota() {
        int i = this.nRota + 1;
        this.nRota = i;
        if (i > 3) {
            this.nRota = 0;
        }
        wifiCamera.naSetCameraDataRota(this.nRotaArray[this.nRota]);
    }

    private void F_AddScal() {
        GP4225_GetKey(new byte[]{(byte) (this.TogglePre ^ UByte.MAX_VALUE), 5, 1, 0});
    }

    private void F_SubScal() {
        GP4225_GetKey(new byte[]{(byte) (this.TogglePre ^ UByte.MAX_VALUE), 5, 2, 0});
    }

    @Subscriber(tag = "GP4225_GetKey")
    private void GP4225_GetKey(byte[] bArr) {
        Log.e(TAG, bArr.length + "");
        if (bArr.length < 4) {
        }
        byte b = bArr[0];
        byte b2 = bArr[1];
        byte b3 = bArr[2];
        byte b4 = bArr[3];
        if (this.TogglePre == b) {
            this.TogglePre = b;
            return;
        }
        this.TogglePre = b;
        switch (b2) {
            case 1:
                F_CheckPermissions(0);
                break;
            case 2:
                F_CheckPermissions(1);
                break;
            case 3:
                StatRecord();
                break;
            case 4:
                StopRecord();
                break;
            case 5:
                if (b3 == 1) {
                    float f = this.scal + 0.2f;
                    this.scal = f;
                    if (f < 1.0f) {
                        this.scal = 1.0f;
                    }
                    if (this.scal > 2.0f) {
                        this.scal = 2.0f;
                    }
                    float f2 = this.scal;
                    float f3 = (f2 - 1.0f) * 2.0f;
                    int i = (int) (f2 * 100.0f);
                    if (i > 100) {
                        wifiCamera.naSetScal(f3 + 1.0f);
                    } else {
                        wifiCamera.naSetScal(1.0f);
                    }
                    showScale("X" + (Math.round(this.scal * 100.0f) / 100.0f));
                    Log.d("BmpTest", "scale: " + i);
                } else if (b3 == 2) {
                    float f4 = this.scal - 0.2f;
                    this.scal = f4;
                    if (f4 < 1.0f) {
                        this.scal = 1.0f;
                    }
                    if (this.scal > 2.0f) {
                        this.scal = 2.0f;
                    }
                    float f5 = this.scal;
                    float f6 = (f5 - 1.0f) * 2.0f;
                    int i2 = (int) (f5 * 100.0f);
                    if (i2 > 100) {
                        wifiCamera.naSetScal(f6 + 1.0f);
                    } else {
                        wifiCamera.naSetScal(1.0f);
                    }
                    showScale("X" + (Math.round(this.scal * 100.0f) / 100.0f));
                    Log.d("BmpTest", "scale: " + i2);
                } else if (b3 == 3) {
                    int i3 = (int) ((((b4 >= 0 ? b4 : (byte) 0) * 0.2f) + 1.0f) * 100.0f);
                    if (i3 > 100) {
                        wifiCamera.naSetScal(this.scal);
                    } else {
                        wifiCamera.naSetScal(1.0f);
                    }
                    Log.d("BmpTest", "scale: " + i3);
                }
                break;
        }
    }

    @Subscriber(tag = "onGetKey")
    private void Key_Pressed(Integer num) {
        int iIntValue = num.intValue();
        if (iIntValue == 1) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.preTime > 100) {
                this.preTime = jCurrentTimeMillis;
                if (!this.bKeyProcessed_1) {
                    this.bKeyProcessed_1 = true;
                    F_CheckPermissions(0);
                    Log.d("BmpTest", "takePhoto: ");
                }
            }
        } else {
            this.bKeyProcessed_1 = false;
        }
        if (iIntValue == 2) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            if (jCurrentTimeMillis2 - this.preTime2 > 100) {
                this.preTime2 = jCurrentTimeMillis2;
                if (!this.bKeyProcessed_2) {
                    this.bKeyProcessed_2 = true;
                    F_CheckPermissions(1);
                    Log.d("BmpTest", "startOrStopRecord: ");
                }
            }
        } else {
            this.bKeyProcessed_2 = false;
        }
        if (iIntValue == 4) {
            if (!this.bKeyProcessed_4) {
                this.bKeyProcessed_4 = true;
                float f = this.scal + 0.2f;
                this.scal = f;
                if (f < 1.0f) {
                    this.scal = 1.0f;
                }
                if (this.scal > 2.0f) {
                    this.scal = 2.0f;
                }
                float f2 = this.scal;
                float f3 = (f2 - 1.0f) * 2.0f;
                int i = (int) (f2 * 100.0f);
                if (i > 100) {
                    wifiCamera.naSetScal(f3 + 1.0f);
                } else {
                    wifiCamera.naSetScal(1.0f);
                }
                showScale("X" + (Math.round(this.scal * 100.0f) / 100.0f));
                Log.d("BmpTest", "scal: " + i);
            }
        } else {
            this.bKeyProcessed_4 = false;
        }
        if (iIntValue == 5) {
            if (!this.bKeyProcessed_5) {
                this.bKeyProcessed_5 = false;
                float f4 = this.scal - 0.2f;
                this.scal = f4;
                if (f4 < 1.0f) {
                    this.scal = 1.0f;
                }
                if (this.scal > 2.0f) {
                    this.scal = 2.0f;
                }
                float f5 = this.scal;
                float f6 = (f5 - 1.0f) * 2.0f;
                int i2 = (int) (f5 * 100.0f);
                if (i2 > 100) {
                    wifiCamera.naSetScal(f6 + 1.0f);
                } else {
                    wifiCamera.naSetScal(1.0f);
                }
                showScale("X" + (Math.round(this.scal * 100.0f) / 100.0f));
                Log.d("BmpTest", "sacl: " + i2);
            }
        } else {
            this.bKeyProcessed_5 = false;
        }
        if (iIntValue == 40) {
            long jCurrentTimeMillis3 = System.currentTimeMillis();
            if (jCurrentTimeMillis3 - this.preTime3 > 100) {
                this.preTime3 = jCurrentTimeMillis3;
                if (!this.bKeyProcessed_28) {
                    this.bKeyProcessed_28 = true;
                    F_SetRota();
                }
            }
        } else {
            this.bKeyProcessed_28 = false;
        }
        if (iIntValue == 41) {
            long jCurrentTimeMillis4 = System.currentTimeMillis();
            if (jCurrentTimeMillis4 - this.preTime > 100) {
                this.preTime = jCurrentTimeMillis4;
                if (!this.bKeyProcessed_29) {
                    this.bKeyProcessed_29 = true;
                    int i3 = this.nStyle + 1;
                    this.nStyle = i3;
                    if (i3 > 12) {
                        this.nStyle = 0;
                    }
                    wifiCamera.naSetStyle(this.nStyle);
                }
            }
        } else {
            this.bKeyProcessed_29 = false;
        }
        if (iIntValue == 48) {
            long jCurrentTimeMillis5 = System.currentTimeMillis();
            if (jCurrentTimeMillis5 - this.preTime > 100) {
                this.preTime = jCurrentTimeMillis5;
                if (this.bKeyProcessed_30) {
                    return;
                }
                this.bKeyProcessed_30 = true;
                this.nStyle = 0;
                wifiCamera.naSetStyle(0);
                return;
            }
            return;
        }
        this.bKeyProcessed_30 = false;
    }

    private void StopRecord() {
        wifiCamera.naStopRecordAll();
    }

    private void StatRecord() {
        if (isConnected() && (MyApp.nStatus & 2) == 0) {
            F_SetResolution();
            F_CheckPermissions(1);
        }
    }

    private void StartOrStopRecord() {
        if (isConnected()) {
            if ((MyApp.nStatus & 2) == 0) {
                F_SetResolution();
                wifiCamera.naStartRecord(MyApp.getFileNameFromDate(true, true), 0, 1, false);
            } else {
                wifiCamera.naStopRecordAll();
            }
        }
    }

    private void F_TakePhoto() {
        if (isConnected()) {
            wifiCamera.naSnapPhoto(null, 0, 1);
            MyApp.F_PhotoMusic();
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.binding.DispplayImage, "scaleX", 1.0f, 0.01f);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.binding.DispplayImage, "scaleY", 1.0f, 0.01f);
            animatorSet.setDuration(150L);
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.play(objectAnimatorOfFloat).with(objectAnimatorOfFloat2);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.joyhonest.wifi_check_adj.MainActivity.5
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(MainActivity.this.binding.DispplayImage, "scaleX", 0.01f, 1.0f);
                    ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(MainActivity.this.binding.DispplayImage, "scaleY", 0.01f, 1.0f);
                    animatorSet2.setDuration(80L);
                    animatorSet2.setInterpolator(new DecelerateInterpolator());
                    animatorSet2.play(objectAnimatorOfFloat3).with(objectAnimatorOfFloat4);
                    animatorSet2.start();
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    Log.d("动画取消", "");
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                    Log.d("动画重复", "");
                }
            });
        }
    }

    @Subscriber(tag = "OnGetBatteryLevel")
    private void OnGetBatteryLevel(Integer num) {
        int iIntValue = num.intValue();
        if (iIntValue > 3) {
            iIntValue = 3;
        }
        this.binding.batteryView.setVisibility(0);
        this.binding.batteryView.setBackgroundResource(this.da[iIntValue]);
    }

    @Subscriber(tag = "Go2Background")
    private void Go2Background(String str) {
        this.openCameraHandler.removeCallbacksAndMessages(null);
        this.bCheckWifi = false;
        this.checkWifiCameraHander.removeCallbacksAndMessages(null);
        MyApp.F_StopStream();
        finish();
        overridePendingTransition(0, 0);
    }

    private void F_SetBrightness() {
        wifiCamera.naSetBrightness((MyApp.Para_Brightness / 50.0f) - 1.0f);
    }

    private void F_SetContrast() {
        float f = MyApp.Para_Contrast / 50.0f;
        wifiCamera.naSetContrast(f);
        Log.e(TAG, "F_SetContrast: " + f);
    }

    private void F_Setsaturation() {
        float f = MyApp.Para_Saturation / 50.0f;
        wifiCamera.naSetSaturation(f);
        Log.e(TAG, "F_Setsaturation: " + f);
    }

    private void F_SetColor(int i) {
        if (i == 3) {
            wifiCamera.naSetRedChannel((int) (((MyApp.Para_R / 50.0f) - 1.0f) * 100.0f));
        }
        if (i == 4) {
            wifiCamera.naSetGreenChannel((int) (((MyApp.Para_G / 50.0f) - 1.0f) * 100.0f));
        }
        if (i == 5) {
            wifiCamera.naSetBlueChannel((int) (((MyApp.Para_B / 50.0f) - 1.0f) * 100.0f));
        }
    }

    @Subscriber(tag = "Slider_OnChanged")
    private void Slider_OnChanged(MySilder mySilder) {
        if (mySilder == this.binding.mySlider) {
            int i = mySilder.nData & SupportMenu.USER_MASK;
            if (this.nSetPara == 0) {
                MyApp.Para_Brightness = i;
                this.binding.BrightnessBtn.setsValue("" + i);
                F_SetBrightness();
            }
            if (this.nSetPara == 1) {
                MyApp.Para_Contrast = i;
                this.binding.ContrastBtn.setsValue("" + i);
                F_SetContrast();
            }
            if (this.nSetPara == 2) {
                MyApp.Para_Saturation = i;
                this.binding.SaturationBtn.setsValue("" + i);
                F_Setsaturation();
            }
            if (this.nSetPara == 3) {
                MyApp.Para_R = i;
                this.binding.RColorBtn.setsValue("" + i);
                F_SetColor(this.nSetPara);
            }
            if (this.nSetPara == 4) {
                MyApp.Para_G = i;
                this.binding.GColorBtn.setsValue("" + i);
                F_SetColor(this.nSetPara);
            }
            if (this.nSetPara == 5) {
                MyApp.Para_B = i;
                this.binding.BColorBtn.setsValue("" + i);
                F_SetColor(this.nSetPara);
            }
            MyApp.F_SaveParaSet();
        }
    }
}
