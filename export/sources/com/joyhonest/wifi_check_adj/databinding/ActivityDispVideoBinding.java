package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityDispVideoBinding implements ViewBinding {
    public final Button btnBack;
    private final ConstraintLayout rootView;
    public final ConstraintLayout topBar;
    public final VideoView videoPlay;

    private ActivityDispVideoBinding(ConstraintLayout constraintLayout, Button button, ConstraintLayout constraintLayout2, VideoView videoView) {
        this.rootView = constraintLayout;
        this.btnBack = button;
        this.topBar = constraintLayout2;
        this.videoPlay = videoView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityDispVideoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityDispVideoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_disp_video, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityDispVideoBinding bind(View view) {
        int i = R.id.btn_back;
        Button button = (Button) ViewBindings.findChildViewById(view, i);
        if (button != null) {
            i = R.id.top_bar;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
            if (constraintLayout != null) {
                i = R.id.video_play;
                VideoView videoView = (VideoView) ViewBindings.findChildViewById(view, i);
                if (videoView != null) {
                    return new ActivityDispVideoBinding((ConstraintLayout) view, button, constraintLayout, videoView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
