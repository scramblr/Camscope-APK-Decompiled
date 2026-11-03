package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.joyhonest.wifi_check_adj.R;
import com.joyhonest.wifi_check_adj.WaitView;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityGridViewBinding implements ViewBinding {
    public final VideoView BrowVideoView2;
    public final RelativeLayout DeleteAert;
    public final LinearLayout Title;
    public final TextView TitleTextView;
    public final WaitView WaitView;
    public final RelativeLayout activityGridView;
    public final Button alartCancel;
    public final Button alartOk;
    public final Button butExit;
    public final Button buttonCancel;
    public final Button buttonDelete;
    public final GridView gridView1;
    public final ViewPager photoVp;
    private final RelativeLayout rootView;
    public final TextView textView2;

    private ActivityGridViewBinding(RelativeLayout relativeLayout, VideoView videoView, RelativeLayout relativeLayout2, LinearLayout linearLayout, TextView textView, WaitView waitView, RelativeLayout relativeLayout3, Button button, Button button2, Button button3, Button button4, Button button5, GridView gridView, ViewPager viewPager, TextView textView2) {
        this.rootView = relativeLayout;
        this.BrowVideoView2 = videoView;
        this.DeleteAert = relativeLayout2;
        this.Title = linearLayout;
        this.TitleTextView = textView;
        this.WaitView = waitView;
        this.activityGridView = relativeLayout3;
        this.alartCancel = button;
        this.alartOk = button2;
        this.butExit = button3;
        this.buttonCancel = button4;
        this.buttonDelete = button5;
        this.gridView1 = gridView;
        this.photoVp = viewPager;
        this.textView2 = textView2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityGridViewBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityGridViewBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_grid_view, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityGridViewBinding bind(View view) {
        int i = R.id.Brow_videoView2;
        VideoView videoView = (VideoView) ViewBindings.findChildViewById(view, i);
        if (videoView != null) {
            i = R.id.DeleteAert;
            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i);
            if (relativeLayout != null) {
                i = R.id.Title;
                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
                if (linearLayout != null) {
                    i = R.id.Title_textView;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                    if (textView != null) {
                        i = R.id.WaitView;
                        WaitView waitView = (WaitView) ViewBindings.findChildViewById(view, i);
                        if (waitView != null) {
                            RelativeLayout relativeLayout2 = (RelativeLayout) view;
                            i = R.id.alart_cancel;
                            Button button = (Button) ViewBindings.findChildViewById(view, i);
                            if (button != null) {
                                i = R.id.alart_ok;
                                Button button2 = (Button) ViewBindings.findChildViewById(view, i);
                                if (button2 != null) {
                                    i = R.id.but_exit;
                                    Button button3 = (Button) ViewBindings.findChildViewById(view, i);
                                    if (button3 != null) {
                                        i = R.id.button_cancel;
                                        Button button4 = (Button) ViewBindings.findChildViewById(view, i);
                                        if (button4 != null) {
                                            i = R.id.button_delete;
                                            Button button5 = (Button) ViewBindings.findChildViewById(view, i);
                                            if (button5 != null) {
                                                i = R.id.gridView1;
                                                GridView gridView = (GridView) ViewBindings.findChildViewById(view, i);
                                                if (gridView != null) {
                                                    i = R.id.photo_vp;
                                                    ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(view, i);
                                                    if (viewPager != null) {
                                                        i = R.id.textView2;
                                                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                                                        if (textView2 != null) {
                                                            return new ActivityGridViewBinding(relativeLayout2, videoView, relativeLayout, linearLayout, textView, waitView, relativeLayout2, button, button2, button3, button4, button5, gridView, viewPager, textView2);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
