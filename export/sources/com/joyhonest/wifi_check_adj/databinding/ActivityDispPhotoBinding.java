package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.joyhonest.wifi_check_adj.R;
import com.joyhonest.wifi_check_adj.StrokeTextView;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityDispPhotoBinding implements ViewBinding {
    public final Button btnBack;
    public final StrokeTextView indexView;
    public final ViewPager photoVp;
    private final ConstraintLayout rootView;

    private ActivityDispPhotoBinding(ConstraintLayout constraintLayout, Button button, StrokeTextView strokeTextView, ViewPager viewPager) {
        this.rootView = constraintLayout;
        this.btnBack = button;
        this.indexView = strokeTextView;
        this.photoVp = viewPager;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityDispPhotoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityDispPhotoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_disp_photo, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityDispPhotoBinding bind(View view) {
        int i = R.id.btnBack;
        Button button = (Button) ViewBindings.findChildViewById(view, i);
        if (button != null) {
            i = R.id.index_view;
            StrokeTextView strokeTextView = (StrokeTextView) ViewBindings.findChildViewById(view, i);
            if (strokeTextView != null) {
                i = R.id.photo_vp;
                ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(view, i);
                if (viewPager != null) {
                    return new ActivityDispPhotoBinding((ConstraintLayout) view, button, strokeTextView, viewPager);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
