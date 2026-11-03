package com.joyhonest.wifi_check_adj;

import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import androidx.appcompat.app.AppCompatActivity;
import com.joyhonest.wifi_check_adj.databinding.ActivityDispVideoBinding;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class DispVideoActivity extends AppCompatActivity {
    private static final String TAG = "DispVideo";
    private boolean bTouchSeekBar = false;
    private ActivityDispVideoBinding binding;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityDispVideoBinding activityDispVideoBindingInflate = ActivityDispVideoBinding.inflate(getLayoutInflater());
        this.binding = activityDispVideoBindingInflate;
        setContentView(activityDispVideoBindingInflate.getRoot());
        MyApp.checkDeviceHasNavigationBar(this);
        this.binding.btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.joyhonest.wifi_check_adj.DispVideoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                DispVideoActivity.this.onBackPressed();
            }
        });
        MediaController mediaController = new MediaController(this);
        this.binding.videoPlay.setVisibility(0);
        this.binding.videoPlay.setMediaController(mediaController);
        mediaController.setMediaPlayer(this.binding.videoPlay);
        this.binding.videoPlay.setVideoURI(MyApp.dispList.get(0));
        this.binding.videoPlay.start();
        this.binding.videoPlay.requestFocus();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MyApp.checkDeviceHasNavigationBar(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        this.binding.videoPlay.stopPlayback();
        overridePendingTransition(0, 0);
    }

    @Subscriber(tag = "Go2Background")
    private void Go2Background(String str) {
        this.binding.videoPlay.stopPlayback();
        finish();
    }

    @Subscriber(tag = "DeviceDisConnected")
    private void DeviceDisConnected(String str) {
        finish();
    }
}
