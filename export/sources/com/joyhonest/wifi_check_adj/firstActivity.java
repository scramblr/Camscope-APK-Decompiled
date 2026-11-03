package com.joyhonest.wifi_check_adj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.joyhonest.joycamera.sdk.wifiCamera;
import com.joyhonest.wifi_check_adj.databinding.ActivityFirstBinding;

/* JADX INFO: loaded from: classes.dex */
public class firstActivity extends AppCompatActivity {
    private static String TAG = "WIFI_Check-Adj";
    ActivityFirstBinding binding;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityFirstBinding activityFirstBindingInflate = ActivityFirstBinding.inflate(getLayoutInflater());
        this.binding = activityFirstBindingInflate;
        setContentView(activityFirstBindingInflate.getRoot());
        wifiCamera.naSetApplicationContext(getApplicationContext());
        MyApp.checkDeviceHasNavigationBar(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MyApp.checkDeviceHasNavigationBar(this);
        F_Init();
    }

    private void F_Init() {
        setRequestedOrientation(6);
        new Handler(getMainLooper()).postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.firstActivity.1
            @Override // java.lang.Runnable
            public void run() {
                firstActivity.this.startActivity(new Intent(firstActivity.this, (Class<?>) MainActivity.class));
                firstActivity.this.finish();
                firstActivity.this.overridePendingTransition(0, 0);
            }
        }, 1L);
    }
}
