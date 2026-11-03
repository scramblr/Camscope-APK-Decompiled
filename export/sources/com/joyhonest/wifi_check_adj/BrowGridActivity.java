package com.joyhonest.wifi_check_adj;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.joyhonest.joycamera.sdk.Utility;
import com.joyhonest.wifi_check_adj.databinding.ActivityBrowGridBinding;
import com.joyhonest.wifi_check_adj.databinding.MyGridNodeBinding;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class BrowGridActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityBrowGridBinding binding;
    private List<Uri> list;
    private MyAdapter myAdapter;
    private List<MyNode> nodes;
    private Handler threadHanlder;
    private HandlerThread thread_read;
    private boolean bExit = false;
    private int nSelectedCount = 0;
    private boolean bEdit = false;
    private final ReentrantLock lock = new ReentrantLock();
    private int index = -1;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityBrowGridBinding activityBrowGridBindingInflate = ActivityBrowGridBinding.inflate(getLayoutInflater());
        this.binding = activityBrowGridBindingInflate;
        setContentView(activityBrowGridBindingInflate.getRoot());
        MyApp.checkDeviceHasNavigationBar(this);
        this.nodes = new ArrayList();
        this.binding.butExit.setOnClickListener(this);
        this.binding.butCancel.setOnClickListener(this);
        this.binding.butDelete.setOnClickListener(this);
        this.binding.butEdit.setOnClickListener(this);
        this.binding.butEdit.setVisibility(4);
        this.binding.butCancel.setVisibility(4);
        this.binding.butDelete.setVisibility(4);
        this.binding.WaitView.setVisibility(0);
        this.binding.alartCancel.setOnClickListener(this);
        this.binding.alartOk.setOnClickListener(this);
        this.binding.checkBox.setOnClickListener(this);
        this.binding.checkBox.setVisibility(4);
        HandlerThread handlerThread = new HandlerThread("OiSee-01-ReadFiles");
        this.thread_read = handlerThread;
        handlerThread.start();
        this.threadHanlder = new Handler(this.thread_read.getLooper());
        this.myAdapter = new MyAdapter(this, R.layout.my_grid_node, this.nodes);
        this.binding.gridView.setAdapter((ListAdapter) this.myAdapter);
        this.binding.gridView.post(new Runnable() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m161lambda$onCreate$0$comjoyhonestwifi_check_adjBrowGridActivity();
            }
        });
        this.binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BrowGridActivity.this.DoItemClick(view, i);
            }
        });
        this.binding.gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                BrowGridActivity.this.DoItemLongClick(i);
                return true;
            }
        });
        this.threadHanlder.postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m162lambda$onCreate$1$comjoyhonestwifi_check_adjBrowGridActivity();
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$com-joyhonest-wifi_check_adj-BrowGridActivity, reason: not valid java name */
    /* synthetic */ void m161lambda$onCreate$0$comjoyhonestwifi_check_adjBrowGridActivity() {
        int width = this.binding.gridView.getWidth();
        if (isScreenLANDSCAPE()) {
            this.binding.gridView.setNumColumns((width / dip2px(this, 100.0f)) - 1);
        } else {
            this.binding.gridView.setNumColumns(width / dip2px(this, 100.0f));
        }
    }

    /* JADX INFO: renamed from: lambda$onCreate$1$com-joyhonest-wifi_check_adj-BrowGridActivity, reason: not valid java name */
    /* synthetic */ void m162lambda$onCreate$1$comjoyhonestwifi_check_adjBrowGridActivity() {
        this.bExit = false;
        this.list = Utility.F_GetAllLocalFiles(MyApp.bBrowPhoto);
        EventBus.getDefault().post("", "GetFilesOK");
    }

    public boolean isScreenLANDSCAPE() {
        return getResources().getConfiguration().orientation == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void DoItemLongClick(int i) {
        if (i >= 0) {
            this.index = i;
        }
        boolean zIsinSelectMode = isinSelectMode();
        this.bEdit = zIsinSelectMode;
        if (zIsinSelectMode) {
            return;
        }
        Iterator<MyNode> it = this.nodes.iterator();
        while (it.hasNext()) {
            it.next().nOp = 1;
        }
        if (i >= 0) {
            this.nodes.get(this.index).nOp = 2;
        }
        this.bEdit = true;
        this.myAdapter.notifyDataSetChanged();
        this.binding.butDelete.setVisibility(0);
        this.binding.butCancel.setVisibility(0);
        this.binding.checkBox.setVisibility(0);
        this.binding.butEdit.setVisibility(4);
        this.nSelectedCount = F_GetSelectFiles();
        F_SetAllorNO();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void DoItemClick(View view, int i) {
        MyNode myNode = this.nodes.get(i);
        if (!this.bEdit) {
            MyApp.dispList.clear();
            if (MyApp.bBrowPhoto) {
                Iterator<MyNode> it = this.nodes.iterator();
                while (it.hasNext()) {
                    MyApp.dispList.add(Uri.parse(it.next().sPath));
                }
                MyApp.F_PlayBtnVoice();
                MyApp.dispListInx = i;
                startActivity(new Intent(this, (Class<?>) DispPhotoActivity.class));
                overridePendingTransition(0, 0);
                return;
            }
            MyApp.F_PlayBtnVoice();
            MyApp.dispList.add(Uri.parse(myNode.sPath));
            startActivity(new Intent(this, (Class<?>) DispVideoActivity.class));
            overridePendingTransition(0, 0);
            return;
        }
        MyApp.F_PlayBtnVoice();
        if (myNode.nOp == 1) {
            myNode.nOp = 2;
            this.nSelectedCount++;
        } else {
            myNode.nOp = 1;
            this.nSelectedCount--;
        }
        F_SetAllorNO();
        final MyAdapter.MyViewHolder myViewHolder = (MyAdapter.MyViewHolder) view.getTag();
        if (myNode.nOp == 2) {
            myViewHolder.setIv_delete(R.mipmap.selected_icon);
            myViewHolder.tv_item.setText(this.nSelectedCount + "");
            myViewHolder.tv_item.setVisibility(0);
            new Handler(getMainLooper()).postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    myViewHolder.tv_item.setVisibility(8);
                }
            }, 500L);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 1, 0.5f, 1, 0.5f);
            scaleAnimation.setDuration(250L);
            scaleAnimation.setFillAfter(true);
            myViewHolder.icon.startAnimation(scaleAnimation);
        }
        if (myNode.nOp == 1) {
            myViewHolder.setIv_delete(R.mipmap.noselected_icon);
            ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, 1, 0.5f, 1, 0.5f);
            scaleAnimation2.setDuration(250L);
            scaleAnimation2.setFillAfter(true);
            myViewHolder.icon.startAnimation(scaleAnimation2);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MyApp.checkDeviceHasNavigationBar(this);
    }

    private boolean isinSelectMode() {
        Iterator<MyNode> it = this.nodes.iterator();
        while (it.hasNext()) {
            if (it.next().nOp != 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        new Handler().postDelayed(new Runnable() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity.3
            @Override // java.lang.Runnable
            public void run() {
                int iDip2px;
                int width = BrowGridActivity.this.binding.gridView.getWidth();
                if (BrowGridActivity.this.isScreenLANDSCAPE()) {
                    BrowGridActivity browGridActivity = BrowGridActivity.this;
                    iDip2px = (width / browGridActivity.dip2px(browGridActivity, 100.0f)) - 1;
                } else {
                    BrowGridActivity browGridActivity2 = BrowGridActivity.this;
                    iDip2px = width / browGridActivity2.dip2px(browGridActivity2, 100.0f);
                }
                BrowGridActivity.this.binding.gridView.setNumColumns(iDip2px);
                EventBus.getDefault().post("", "GetFilesOK");
            }
        }, 100L);
    }

    @Subscriber(mode = ThreadMode.POST, tag = "GetFilesOK")
    private void GetFilesOK(String str) {
        Bitmap videoThumbnail;
        if (this.list != null) {
            List<MyNode> list = this.nodes;
            if (list != null) {
                list.clear();
            } else {
                this.nodes = new ArrayList();
            }
            Collections.sort(this.list, new MapComparator());
            for (Uri uri : this.list) {
                if (this.bExit) {
                    break;
                }
                if (MyApp.bBrowPhoto) {
                    videoThumbnail = GetSuonuitu(uri);
                } else {
                    videoThumbnail = getVideoThumbnail(uri);
                }
                if (videoThumbnail != null) {
                    MyNode myNode = new MyNode(0);
                    myNode.bitmap = videoThumbnail;
                    myNode.sPath = uri.toString();
                    this.nodes.add(myNode);
                    runOnUiThread(new Runnable() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m159x23aa0b53();
                        }
                    });
                }
            }
            runOnUiThread(new Runnable() { // from class: com.joyhonest.wifi_check_adj.BrowGridActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m160x99243194();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$GetFilesOK$3$com-joyhonest-wifi_check_adj-BrowGridActivity, reason: not valid java name */
    /* synthetic */ void m159x23aa0b53() {
        this.myAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: renamed from: lambda$GetFilesOK$4$com-joyhonest-wifi_check_adj-BrowGridActivity, reason: not valid java name */
    /* synthetic */ void m160x99243194() {
        this.myAdapter.notifyDataSetChanged();
        if (this.nodes.size() > 0) {
            this.binding.butEdit.setVisibility(0);
        } else {
            this.binding.butEdit.setVisibility(4);
        }
        this.binding.countText.setText(String.format(Locale.ENGLISH, getString(R.string.count_file), Integer.valueOf(this.nodes.size())));
        if (this.binding.WaitView.getVisibility() == 0) {
            this.binding.WaitView.setVisibility(4);
        }
    }

    private Bitmap GetSuonuitu(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptorOpenFileDescriptor.getFileDescriptor();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = (options.outHeight + 99) / 100;
            Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            parcelFileDescriptorOpenFileDescriptor.close();
            return bitmapDecodeFileDescriptor;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getVideoThumbnail(Uri uri) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        Bitmap frameAtTime = null;
        try {
            mediaMetadataRetriever.setDataSource(this, uri);
            frameAtTime = mediaMetadataRetriever.getFrameAtTime(1L);
            Bitmap bitmapExtractThumbnail = ThumbnailUtils.extractThumbnail(frameAtTime, 100, 100);
            try {
                return bitmapExtractThumbnail;
            } catch (Exception e) {
                return bitmapExtractThumbnail;
            }
        } catch (RuntimeException e2) {
            e2.printStackTrace();
            return frameAtTime;
        } finally {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    static class MapComparator implements Comparator<Uri> {
        MapComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Uri uri, Uri uri2) {
            return uri2.compareTo(uri);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.binding.WaitView.setVisibility(4);
        this.thread_read.quit();
        this.threadHanlder.removeCallbacksAndMessages(null);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.binding.butEdit == view) {
            DoItemLongClick(-1);
        }
        if (this.binding.butExit == view) {
            onBackPressed();
        }
        if (this.binding.butCancel == view) {
            MyApp.F_PlayBtnVoice();
            this.bEdit = false;
            ResetAllItem();
        }
        if (this.binding.butDelete == view) {
            Iterator<MyNode> it = this.nodes.iterator();
            while (it.hasNext()) {
                if (it.next().nOp == 2) {
                    MyApp.F_PlayBtnVoice();
                    this.binding.DeleteAert.setVisibility(0);
                    break;
                }
            }
        }
        if (this.binding.alartCancel == view) {
            MyApp.F_PlayBtnVoice();
            this.bEdit = false;
            ResetAllItem();
            this.binding.DeleteAert.setVisibility(4);
        }
        if (this.binding.alartOk == view) {
            MyApp.F_PlayBtnVoice();
            F_DeleteSelectedFiles();
            ResetAllItem();
            this.binding.countText.setText(String.format(Locale.ENGLISH, getString(R.string.count_file), Integer.valueOf(this.nodes.size())));
            this.binding.DeleteAert.setVisibility(4);
        }
        if (this.binding.checkBox == view) {
            MyApp.F_PlayBtnVoice();
            if (this.binding.checkBox.isChecked()) {
                Iterator<MyNode> it2 = this.nodes.iterator();
                while (it2.hasNext()) {
                    it2.next().nOp = 2;
                }
                this.myAdapter.notifyDataSetChanged();
            } else {
                Iterator<MyNode> it3 = this.nodes.iterator();
                while (it3.hasNext()) {
                    it3.next().nOp = 1;
                }
                this.myAdapter.notifyDataSetChanged();
            }
            this.nSelectedCount = F_GetSelectFiles();
        }
    }

    private void F_DeleteSelectedFiles() {
        for (MyNode myNode : this.nodes) {
            if (this.bExit) {
                this.bExit = false;
                break;
            } else {
                if (myNode.nOp == 2) {
                    Utility.DeleteImage(myNode.sPath);
                }
            }
        }
        this.lock.lock();
        try {
            Iterator<MyNode> it = this.nodes.iterator();
            while (it.hasNext()) {
                if (it.next().nOp == 2) {
                    it.remove();
                    this.myAdapter.notifyDataSetChanged();
                }
            }
            this.lock.unlock();
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    private void ResetAllItem() {
        this.bEdit = false;
        Iterator<MyNode> it = this.nodes.iterator();
        while (it.hasNext()) {
            it.next().nOp = 0;
        }
        this.myAdapter.notifyDataSetChanged();
        this.binding.butCancel.setVisibility(4);
        this.binding.butDelete.setVisibility(4);
        this.binding.checkBox.setVisibility(4);
        if (this.nodes.size() > 0) {
            this.binding.butEdit.setVisibility(0);
        }
    }

    class MyAdapter extends BaseAdapter {
        private final Context context;
        private final LayoutInflater mInflater;
        private final List<MyNode> mfilelist;
        private int resourceid;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        MyAdapter(Context context, int i, List<MyNode> list) {
            this.context = context;
            this.resourceid = i;
            this.mfilelist = list;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            BrowGridActivity.this.lock.lock();
            int size = this.mfilelist.size();
            BrowGridActivity.this.lock.unlock();
            return size;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyViewHolder myViewHolder;
            View root;
            BrowGridActivity.this.lock.lock();
            try {
                MyNode myNode = i < this.mfilelist.size() ? this.mfilelist.get(i) : null;
                if (view == null) {
                    myViewHolder = new MyViewHolder();
                    MyGridNodeBinding myGridNodeBindingInflate = MyGridNodeBinding.inflate(this.mInflater);
                    root = myGridNodeBindingInflate.getRoot();
                    myViewHolder.progressBar = myGridNodeBindingInflate.GridProgressBar1;
                    myViewHolder.item_view = myGridNodeBindingInflate.itemView;
                    myViewHolder.progressBar.setProgress(0);
                    myViewHolder.progressBar.setMax(1000);
                    myViewHolder.progressBar.setVisibility(4);
                    myViewHolder.icon = myGridNodeBindingInflate.GridImageView1;
                    myViewHolder.video_bg = myGridNodeBindingInflate.videoBg;
                    myViewHolder.iv_delete = myGridNodeBindingInflate.ivDelete;
                    myViewHolder.iv_delete.setBackgroundResource(R.mipmap.noselected_icon);
                    myViewHolder.tv_item = myGridNodeBindingInflate.tvItem;
                    myViewHolder.caption = myGridNodeBindingInflate.GridTextView1;
                    myViewHolder.filename_TextView = myGridNodeBindingInflate.filenameTextView;
                    myViewHolder.btn_down = myGridNodeBindingInflate.btnDown;
                    myViewHolder.sUrl = "";
                    root.setTag(myViewHolder);
                } else {
                    myViewHolder = (MyViewHolder) view.getTag();
                    root = view;
                }
                if (myNode != null) {
                    myViewHolder.filename_TextView.setText(myNode.sUrl);
                    if (myNode.bitmap != null) {
                        myViewHolder.icon.setImageBitmap(myNode.bitmap);
                    } else if (MyApp.bBrowPhoto) {
                        myViewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.image_default));
                    } else {
                        myViewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.video_default));
                    }
                    myViewHolder.progressBar.setVisibility(8);
                    myViewHolder.caption.setVisibility(4);
                    if (myNode.nOp == 1) {
                        myViewHolder.iv_delete.setVisibility(0);
                        myViewHolder.iv_delete.setBackgroundResource(R.mipmap.noselected_icon);
                        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, 1, 0.5f, 1, 0.5f);
                        scaleAnimation.setDuration(1L);
                        scaleAnimation.setFillAfter(true);
                        myViewHolder.icon.startAnimation(scaleAnimation);
                    } else if (myNode.nOp == 2) {
                        myViewHolder.iv_delete.setVisibility(0);
                        myViewHolder.iv_delete.setBackgroundResource(R.mipmap.selected_icon);
                        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.2f, 1.2f, 1.2f, 1.2f, 1, 0.5f, 1, 0.5f);
                        scaleAnimation2.setDuration(1L);
                        scaleAnimation2.setFillAfter(true);
                        myViewHolder.icon.startAnimation(scaleAnimation2);
                    } else {
                        myViewHolder.iv_delete.setVisibility(4);
                    }
                    if (MyApp.bBrowPhoto || myNode.bitmap == null) {
                        myViewHolder.video_bg.setVisibility(4);
                    } else {
                        myViewHolder.video_bg.setVisibility(0);
                    }
                }
                return root;
            } finally {
                BrowGridActivity.this.lock.unlock();
            }
        }

        public class MyViewHolder {
            Button btn_down;
            TextView caption;
            TextView filename_TextView;
            ImageView icon;
            RelativeLayout item_view;
            ImageView iv_delete;
            ProgressBar progressBar;
            String sUrl;
            TextView tv_item;
            ImageView video_bg;

            public MyViewHolder() {
            }

            public Drawable getIv_delete() {
                return this.iv_delete.getBackground();
            }

            void setIv_delete(int i) {
                this.iv_delete.setBackgroundResource(i);
            }
        }
    }

    private int F_GetSelectFiles() {
        Iterator<MyNode> it = this.nodes.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().nOp == 2) {
                i++;
            }
        }
        return i;
    }

    private void F_SetAllorNO() {
        Iterator<MyNode> it = this.nodes.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().nOp == 2) {
                i++;
            }
        }
        this.binding.checkBox.setChecked(i == this.nodes.size());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        this.bEdit = true;
        MyApp.F_PlayBtnVoice();
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Subscriber(tag = "Go2Background")
    private void Go2Background(String str) {
        this.bExit = true;
        finish();
    }

    @Subscriber(tag = "DeviceDisConnected")
    private void DeviceDisConnected(String str) {
        this.bExit = true;
        finish();
    }
}
