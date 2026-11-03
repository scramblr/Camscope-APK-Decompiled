package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityFirstBinding implements ViewBinding {
    public final ImageView imageView;
    public final ConstraintLayout main;
    private final ConstraintLayout rootView;

    private ActivityFirstBinding(ConstraintLayout constraintLayout, ImageView imageView, ConstraintLayout constraintLayout2) {
        this.rootView = constraintLayout;
        this.imageView = imageView;
        this.main = constraintLayout2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityFirstBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityFirstBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_first, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityFirstBinding bind(View view) {
        int i = R.id.imageView;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
        if (imageView != null) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            return new ActivityFirstBinding(constraintLayout, imageView, constraintLayout);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
