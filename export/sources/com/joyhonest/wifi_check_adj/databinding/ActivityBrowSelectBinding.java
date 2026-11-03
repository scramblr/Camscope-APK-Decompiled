package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityBrowSelectBinding implements ViewBinding {
    public final Button butExit;
    public final Button butPhoto;
    public final Button butVideo;
    private final ConstraintLayout rootView;
    public final TextView textView8;

    private ActivityBrowSelectBinding(ConstraintLayout constraintLayout, Button button, Button button2, Button button3, TextView textView) {
        this.rootView = constraintLayout;
        this.butExit = button;
        this.butPhoto = button2;
        this.butVideo = button3;
        this.textView8 = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityBrowSelectBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityBrowSelectBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_brow_select, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityBrowSelectBinding bind(View view) {
        int i = R.id.but_exit;
        Button button = (Button) ViewBindings.findChildViewById(view, i);
        if (button != null) {
            i = R.id.but_photo;
            Button button2 = (Button) ViewBindings.findChildViewById(view, i);
            if (button2 != null) {
                i = R.id.but_video;
                Button button3 = (Button) ViewBindings.findChildViewById(view, i);
                if (button3 != null) {
                    i = R.id.textView8;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                    if (textView != null) {
                        return new ActivityBrowSelectBinding((ConstraintLayout) view, button, button2, button3, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
