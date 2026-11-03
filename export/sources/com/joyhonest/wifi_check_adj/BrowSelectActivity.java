package com.joyhonest.wifi_check_adj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.joyhonest.wifi_check_adj.databinding.ActivityBrowSelectBinding;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class BrowSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityBrowSelectBinding binding;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApp.checkDeviceHasNavigationBar(this);
        ActivityBrowSelectBinding activityBrowSelectBindingInflate = ActivityBrowSelectBinding.inflate(getLayoutInflater());
        this.binding = activityBrowSelectBindingInflate;
        setContentView(activityBrowSelectBindingInflate.getRoot());
        this.binding.butExit.setOnClickListener(this);
        this.binding.butPhoto.setOnClickListener(this);
        this.binding.butVideo.setOnClickListener(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MyApp.checkDeviceHasNavigationBar(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        MyApp.F_PlayBtnVoice();
        overridePendingTransition(0, 0);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.binding.butExit == view) {
            onBackPressed();
        }
        if (this.binding.butPhoto == view) {
            MyApp.F_PlayBtnVoice();
            MyApp.bBrowPhoto = true;
            startActivity(new Intent(this, (Class<?>) BrowGridActivity.class));
            overridePendingTransition(0, 0);
        }
        if (this.binding.butVideo == view) {
            MyApp.F_PlayBtnVoice();
            MyApp.bBrowPhoto = false;
            startActivity(new Intent(this, (Class<?>) BrowGridActivity.class));
            overridePendingTransition(0, 0);
        }
    }

    @Subscriber(tag = "Go2Background")
    private void Go2Background(String str) {
        finish();
    }

    @Subscriber(tag = "DeviceDisConnected")
    private void DeviceDisConnected(String str) {
        finish();
    }
}
