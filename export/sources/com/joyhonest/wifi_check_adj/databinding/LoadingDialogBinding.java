package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;

/* JADX INFO: loaded from: classes.dex */
public final class LoadingDialogBinding implements ViewBinding {
    public final LinearLayout dialogView;
    public final ImageView img;
    private final LinearLayout rootView;
    public final TextView tipTextView;

    private LoadingDialogBinding(LinearLayout linearLayout, LinearLayout linearLayout2, ImageView imageView, TextView textView) {
        this.rootView = linearLayout;
        this.dialogView = linearLayout2;
        this.img = imageView;
        this.tipTextView = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static LoadingDialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static LoadingDialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.loading_dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static LoadingDialogBinding bind(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        int i = R.id.img;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
        if (imageView != null) {
            i = R.id.tipTextView;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
            if (textView != null) {
                return new LoadingDialogBinding(linearLayout, linearLayout, imageView, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
