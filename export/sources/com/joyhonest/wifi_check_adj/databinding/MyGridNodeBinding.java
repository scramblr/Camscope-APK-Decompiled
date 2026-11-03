package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;
import com.joyhonest.wifi_check_adj.RoundRectLayout;

/* JADX INFO: loaded from: classes.dex */
public final class MyGridNodeBinding implements ViewBinding {
    public final ImageView GridImageView1;
    public final ProgressBar GridProgressBar1;
    public final TextView GridTextView1;
    public final Button btnDown;
    public final TextView filenameTextView;
    public final RoundRectLayout itemView;
    public final ImageView ivDelete;
    private final LinearLayout rootView;
    public final TextView tvItem;
    public final ImageView videoBg;

    private MyGridNodeBinding(LinearLayout linearLayout, ImageView imageView, ProgressBar progressBar, TextView textView, Button button, TextView textView2, RoundRectLayout roundRectLayout, ImageView imageView2, TextView textView3, ImageView imageView3) {
        this.rootView = linearLayout;
        this.GridImageView1 = imageView;
        this.GridProgressBar1 = progressBar;
        this.GridTextView1 = textView;
        this.btnDown = button;
        this.filenameTextView = textView2;
        this.itemView = roundRectLayout;
        this.ivDelete = imageView2;
        this.tvItem = textView3;
        this.videoBg = imageView3;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static MyGridNodeBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static MyGridNodeBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.my_grid_node, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static MyGridNodeBinding bind(View view) {
        int i = R.id.Grid_imageView1;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
        if (imageView != null) {
            i = R.id.Grid_progressBar1;
            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i);
            if (progressBar != null) {
                i = R.id.Grid_textView1;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                if (textView != null) {
                    i = R.id.btn_down;
                    Button button = (Button) ViewBindings.findChildViewById(view, i);
                    if (button != null) {
                        i = R.id.filename_TextView;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                        if (textView2 != null) {
                            i = R.id.item_view;
                            RoundRectLayout roundRectLayout = (RoundRectLayout) ViewBindings.findChildViewById(view, i);
                            if (roundRectLayout != null) {
                                i = R.id.iv_delete;
                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i);
                                if (imageView2 != null) {
                                    i = R.id.tv_item;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i);
                                    if (textView3 != null) {
                                        i = R.id.video_bg;
                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i);
                                        if (imageView3 != null) {
                                            return new MyGridNodeBinding((LinearLayout) view, imageView, progressBar, textView, button, textView2, roundRectLayout, imageView2, textView3, imageView3);
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
