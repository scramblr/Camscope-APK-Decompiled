package com.joyhonest.wifi_check_adj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.joyhonest.wifi_check_adj.databinding.ActivityDispPhotoBinding;
import java.io.FileDescriptor;
import java.util.List;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class DispPhotoActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private PagerAdapter adapter = new PagerAdapter() { // from class: com.joyhonest.wifi_check_adj.DispPhotoActivity.2
        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return DispPhotoActivity.this.nodes.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            ((MyImageView) obj).setImageBitmap(null);
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            MyImageView myImageView = new MyImageView(DispPhotoActivity.this);
            myImageView.bCanScal = false;
            myImageView.setMaxZoom(3.5f);
            DispPhotoActivity dispPhotoActivity = DispPhotoActivity.this;
            Bitmap bitmapLoadBitmap = dispPhotoActivity.LoadBitmap((Uri) dispPhotoActivity.nodes.get(i));
            bitmapLoadBitmap.getWidth();
            bitmapLoadBitmap.getHeight();
            myImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            myImageView.setImageBitmap(bitmapLoadBitmap);
            viewGroup.addView(myImageView);
            return myImageView;
        }
    };
    private ActivityDispPhotoBinding binding;
    private List<Uri> nodes;

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MyApp.checkDeviceHasNavigationBar(this);
        ActivityDispPhotoBinding activityDispPhotoBindingInflate = ActivityDispPhotoBinding.inflate(getLayoutInflater());
        this.binding = activityDispPhotoBindingInflate;
        setContentView(activityDispPhotoBindingInflate.getRoot());
        this.binding.indexView.setText((MyApp.dispListInx + 1) + "/" + MyApp.dispList.size());
        this.nodes = MyApp.dispList;
        this.binding.photoVp.setAdapter(this.adapter);
        this.binding.photoVp.addOnPageChangeListener(this);
        this.binding.photoVp.setCurrentItem(MyApp.dispListInx);
        this.binding.btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.joyhonest.wifi_check_adj.DispPhotoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyApp.F_PlayBtnVoice();
                DispPhotoActivity.this.onBackPressed();
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MyApp.checkDeviceHasNavigationBar(this);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        this.binding.indexView.setText((i + 1) + "/" + MyApp.dispList.size());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap LoadBitmap(Uri uri) {
        Log.e("TAGaaa", "get image");
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptorOpenFileDescriptor.getFileDescriptor();
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            options.inJustDecodeBounds = false;
            int i2 = (int) (options.outHeight / 1080.0f);
            if (i2 > 0) {
                i = i2;
            }
            options.inSampleSize = i;
            Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            parcelFileDescriptorOpenFileDescriptor.close();
            return bitmapDecodeFileDescriptor;
        } catch (Exception unused) {
            return null;
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
