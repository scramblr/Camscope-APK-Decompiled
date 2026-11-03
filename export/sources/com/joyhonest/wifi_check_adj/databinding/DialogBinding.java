package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;

/* JADX INFO: loaded from: classes.dex */
public final class DialogBinding implements ViewBinding {
    public final TextView message;
    public final View midView;
    public final Button no;
    private final RelativeLayout rootView;
    public final TextView title;
    public final Button yes;

    private DialogBinding(RelativeLayout relativeLayout, TextView textView, View view, Button button, TextView textView2, Button button2) {
        this.rootView = relativeLayout;
        this.message = textView;
        this.midView = view;
        this.no = button;
        this.title = textView2;
        this.yes = button2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DialogBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static DialogBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogBinding bind(View view) {
        View viewFindChildViewById;
        int i = R.id.message;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
        if (textView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i = R.id.midView))) != null) {
            i = R.id.no;
            Button button = (Button) ViewBindings.findChildViewById(view, i);
            if (button != null) {
                i = R.id.title;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                if (textView2 != null) {
                    i = R.id.yes;
                    Button button2 = (Button) ViewBindings.findChildViewById(view, i);
                    if (button2 != null) {
                        return new DialogBinding((RelativeLayout) view, textView, viewFindChildViewById, button, textView2, button2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
