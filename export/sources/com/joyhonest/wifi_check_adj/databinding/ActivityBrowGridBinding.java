package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.R;
import com.joyhonest.wifi_check_adj.WaitView;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityBrowGridBinding implements ViewBinding {
    public final RelativeLayout DeleteAert;
    public final WaitView WaitView;
    public final Button alartCancel;
    public final Button alartOk;
    public final Button butCancel;
    public final Button butDelete;
    public final Button butEdit;
    public final Button butExit;
    public final CheckBox checkBox;
    public final TextView countText;
    public final GridView gridView;
    private final ConstraintLayout rootView;
    public final TextView textView2;
    public final ConstraintLayout topBar;

    private ActivityBrowGridBinding(ConstraintLayout constraintLayout, RelativeLayout relativeLayout, WaitView waitView, Button button, Button button2, Button button3, Button button4, Button button5, Button button6, CheckBox checkBox, TextView textView, GridView gridView, TextView textView2, ConstraintLayout constraintLayout2) {
        this.rootView = constraintLayout;
        this.DeleteAert = relativeLayout;
        this.WaitView = waitView;
        this.alartCancel = button;
        this.alartOk = button2;
        this.butCancel = button3;
        this.butDelete = button4;
        this.butEdit = button5;
        this.butExit = button6;
        this.checkBox = checkBox;
        this.countText = textView;
        this.gridView = gridView;
        this.textView2 = textView2;
        this.topBar = constraintLayout2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityBrowGridBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityBrowGridBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_brow_grid, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityBrowGridBinding bind(View view) {
        int i = R.id.DeleteAert;
        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i);
        if (relativeLayout != null) {
            i = R.id.WaitView;
            WaitView waitView = (WaitView) ViewBindings.findChildViewById(view, i);
            if (waitView != null) {
                i = R.id.alart_cancel;
                Button button = (Button) ViewBindings.findChildViewById(view, i);
                if (button != null) {
                    i = R.id.alart_ok;
                    Button button2 = (Button) ViewBindings.findChildViewById(view, i);
                    if (button2 != null) {
                        i = R.id.but_Cancel;
                        Button button3 = (Button) ViewBindings.findChildViewById(view, i);
                        if (button3 != null) {
                            i = R.id.but_delete;
                            Button button4 = (Button) ViewBindings.findChildViewById(view, i);
                            if (button4 != null) {
                                i = R.id.but_edit;
                                Button button5 = (Button) ViewBindings.findChildViewById(view, i);
                                if (button5 != null) {
                                    i = R.id.but_exit;
                                    Button button6 = (Button) ViewBindings.findChildViewById(view, i);
                                    if (button6 != null) {
                                        i = R.id.checkBox;
                                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(view, i);
                                        if (checkBox != null) {
                                            i = R.id.count_text;
                                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                                            if (textView != null) {
                                                i = R.id.gridView;
                                                GridView gridView = (GridView) ViewBindings.findChildViewById(view, i);
                                                if (gridView != null) {
                                                    i = R.id.textView2;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                                                    if (textView2 != null) {
                                                        i = R.id.top_bar;
                                                        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
                                                        if (constraintLayout != null) {
                                                            return new ActivityBrowGridBinding((ConstraintLayout) view, relativeLayout, waitView, button, button2, button3, button4, button5, button6, checkBox, textView, gridView, textView2, constraintLayout);
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
