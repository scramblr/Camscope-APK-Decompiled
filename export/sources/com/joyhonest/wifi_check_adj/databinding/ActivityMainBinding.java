package com.joyhonest.wifi_check_adj.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.joyhonest.wifi_check_adj.MyButton;
import com.joyhonest.wifi_check_adj.MySilder;
import com.joyhonest.wifi_check_adj.R;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityMainBinding implements ViewBinding {
    public final MyButton BColorBtn;
    public final MyButton BrightnessBtn;
    public final ConstraintLayout ConnectedView;
    public final MyButton ContrastBtn;
    public final ImageView DispplayImage;
    public final MyButton GColorBtn;
    public final MyButton RColorBtn;
    public final TextView RecTimeView;
    public final LinearLayout ResolutionSelectView;
    public final TextView ResolutionSet;
    public final MyButton SaturationBtn;
    public final ConstraintLayout SensorWarnView;
    public final RelativeLayout ShutterView;
    public final ConstraintLayout ViewSetting;
    public final ImageView batteryView;
    public final Button btnReset;
    public final Button btnResolution1;
    public final Button btnResolution2;
    public final Button btnResolution3;
    public final Button btnResolution4;
    public final Button btnResolution5;
    public final Button btnResolution6;
    public final Button btnResolution7;
    public final Button butBrow;
    public final Button butEar;
    public final Button butMirr;
    public final Button butPhoto;
    public final Button butRota;
    public final Button butSetup;
    public final Button butVideo;
    public final View line1;
    public final View line1S;
    public final View line2;
    public final View line2S;
    public final View line3;
    public final View line4;
    public final View line5;
    public final View line6;
    public final View line7;
    public final LinearLayout mainSettings;
    public final MySilder mySlider;
    public final TextView paraSet;
    private final ConstraintLayout rootView;
    public final ConstraintLayout set6R;
    public final TextView textView;
    public final TextView textViewS;
    public final ConstraintLayout toolbar;
    public final LinearLayout toolbar1;
    public final TextView tvScale;
    public final TextView txtReset;

    private ActivityMainBinding(ConstraintLayout constraintLayout, MyButton myButton, MyButton myButton2, ConstraintLayout constraintLayout2, MyButton myButton3, ImageView imageView, MyButton myButton4, MyButton myButton5, TextView textView, LinearLayout linearLayout, TextView textView2, MyButton myButton6, ConstraintLayout constraintLayout3, RelativeLayout relativeLayout, ConstraintLayout constraintLayout4, ImageView imageView2, Button button, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Button button13, Button button14, Button button15, View view, View view2, View view3, View view4, View view5, View view6, View view7, View view8, View view9, LinearLayout linearLayout2, MySilder mySilder, TextView textView3, ConstraintLayout constraintLayout5, TextView textView4, TextView textView5, ConstraintLayout constraintLayout6, LinearLayout linearLayout3, TextView textView6, TextView textView7) {
        this.rootView = constraintLayout;
        this.BColorBtn = myButton;
        this.BrightnessBtn = myButton2;
        this.ConnectedView = constraintLayout2;
        this.ContrastBtn = myButton3;
        this.DispplayImage = imageView;
        this.GColorBtn = myButton4;
        this.RColorBtn = myButton5;
        this.RecTimeView = textView;
        this.ResolutionSelectView = linearLayout;
        this.ResolutionSet = textView2;
        this.SaturationBtn = myButton6;
        this.SensorWarnView = constraintLayout3;
        this.ShutterView = relativeLayout;
        this.ViewSetting = constraintLayout4;
        this.batteryView = imageView2;
        this.btnReset = button;
        this.btnResolution1 = button2;
        this.btnResolution2 = button3;
        this.btnResolution3 = button4;
        this.btnResolution4 = button5;
        this.btnResolution5 = button6;
        this.btnResolution6 = button7;
        this.btnResolution7 = button8;
        this.butBrow = button9;
        this.butEar = button10;
        this.butMirr = button11;
        this.butPhoto = button12;
        this.butRota = button13;
        this.butSetup = button14;
        this.butVideo = button15;
        this.line1 = view;
        this.line1S = view2;
        this.line2 = view3;
        this.line2S = view4;
        this.line3 = view5;
        this.line4 = view6;
        this.line5 = view7;
        this.line6 = view8;
        this.line7 = view9;
        this.mainSettings = linearLayout2;
        this.mySlider = mySilder;
        this.paraSet = textView3;
        this.set6R = constraintLayout5;
        this.textView = textView4;
        this.textViewS = textView5;
        this.toolbar = constraintLayout6;
        this.toolbar1 = linearLayout3;
        this.tvScale = textView6;
        this.txtReset = textView7;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_main, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityMainBinding bind(View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        View viewFindChildViewById4;
        View viewFindChildViewById5;
        View viewFindChildViewById6;
        View viewFindChildViewById7;
        View viewFindChildViewById8;
        View viewFindChildViewById9;
        int i = R.id.B_Color_btn;
        MyButton myButton = (MyButton) ViewBindings.findChildViewById(view, i);
        if (myButton != null) {
            i = R.id.Brightness_btn;
            MyButton myButton2 = (MyButton) ViewBindings.findChildViewById(view, i);
            if (myButton2 != null) {
                i = R.id.ConnectedView;
                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
                if (constraintLayout != null) {
                    i = R.id.Contrast_btn;
                    MyButton myButton3 = (MyButton) ViewBindings.findChildViewById(view, i);
                    if (myButton3 != null) {
                        i = R.id.DispplayImage;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
                        if (imageView != null) {
                            i = R.id.G_Color_btn;
                            MyButton myButton4 = (MyButton) ViewBindings.findChildViewById(view, i);
                            if (myButton4 != null) {
                                i = R.id.R_Color_btn;
                                MyButton myButton5 = (MyButton) ViewBindings.findChildViewById(view, i);
                                if (myButton5 != null) {
                                    i = R.id.RecTimeView;
                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                                    if (textView != null) {
                                        i = R.id.ResolutionSelectView;
                                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
                                        if (linearLayout != null) {
                                            i = R.id.Resolution_set;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                                            if (textView2 != null) {
                                                i = R.id.Saturation_btn;
                                                MyButton myButton6 = (MyButton) ViewBindings.findChildViewById(view, i);
                                                if (myButton6 != null) {
                                                    i = R.id.SensorWarnView;
                                                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
                                                    if (constraintLayout2 != null) {
                                                        i = R.id.ShutterView;
                                                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i);
                                                        if (relativeLayout != null) {
                                                            i = R.id.ViewSetting;
                                                            ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
                                                            if (constraintLayout3 != null) {
                                                                i = R.id.battery_view;
                                                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i);
                                                                if (imageView2 != null) {
                                                                    i = R.id.btn_Reset;
                                                                    Button button = (Button) ViewBindings.findChildViewById(view, i);
                                                                    if (button != null) {
                                                                        i = R.id.btn_Resolution1;
                                                                        Button button2 = (Button) ViewBindings.findChildViewById(view, i);
                                                                        if (button2 != null) {
                                                                            i = R.id.btn_Resolution2;
                                                                            Button button3 = (Button) ViewBindings.findChildViewById(view, i);
                                                                            if (button3 != null) {
                                                                                i = R.id.btn_Resolution3;
                                                                                Button button4 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                if (button4 != null) {
                                                                                    i = R.id.btn_Resolution4;
                                                                                    Button button5 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                    if (button5 != null) {
                                                                                        i = R.id.btn_Resolution5;
                                                                                        Button button6 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                        if (button6 != null) {
                                                                                            i = R.id.btn_Resolution6;
                                                                                            Button button7 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                            if (button7 != null) {
                                                                                                i = R.id.btn_Resolution7;
                                                                                                Button button8 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                if (button8 != null) {
                                                                                                    i = R.id.but_brow;
                                                                                                    Button button9 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                    if (button9 != null) {
                                                                                                        i = R.id.but_ear;
                                                                                                        Button button10 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                        if (button10 != null) {
                                                                                                            i = R.id.but_mirr;
                                                                                                            Button button11 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                            if (button11 != null) {
                                                                                                                i = R.id.but_photo;
                                                                                                                Button button12 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                                if (button12 != null) {
                                                                                                                    i = R.id.but_rota;
                                                                                                                    Button button13 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                                    if (button13 != null) {
                                                                                                                        i = R.id.but_setup;
                                                                                                                        Button button14 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                                        if (button14 != null) {
                                                                                                                            i = R.id.but_video;
                                                                                                                            Button button15 = (Button) ViewBindings.findChildViewById(view, i);
                                                                                                                            if (button15 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i = R.id.line1))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i = R.id.line1_s))) != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i = R.id.line2))) != null && (viewFindChildViewById4 = ViewBindings.findChildViewById(view, (i = R.id.line2_s))) != null && (viewFindChildViewById5 = ViewBindings.findChildViewById(view, (i = R.id.line3))) != null && (viewFindChildViewById6 = ViewBindings.findChildViewById(view, (i = R.id.line4))) != null && (viewFindChildViewById7 = ViewBindings.findChildViewById(view, (i = R.id.line5))) != null && (viewFindChildViewById8 = ViewBindings.findChildViewById(view, (i = R.id.line6))) != null && (viewFindChildViewById9 = ViewBindings.findChildViewById(view, (i = R.id.line7))) != null) {
                                                                                                                                i = R.id.main_Settings;
                                                                                                                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i);
                                                                                                                                if (linearLayout2 != null) {
                                                                                                                                    i = R.id.mySlider;
                                                                                                                                    MySilder mySilder = (MySilder) ViewBindings.findChildViewById(view, i);
                                                                                                                                    if (mySilder != null) {
                                                                                                                                        i = R.id.para_set;
                                                                                                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i);
                                                                                                                                        if (textView3 != null) {
                                                                                                                                            i = R.id.set6_R;
                                                                                                                                            ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
                                                                                                                                            if (constraintLayout4 != null) {
                                                                                                                                                i = R.id.textView;
                                                                                                                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i);
                                                                                                                                                if (textView4 != null) {
                                                                                                                                                    i = R.id.textView_s;
                                                                                                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i);
                                                                                                                                                    if (textView5 != null) {
                                                                                                                                                        i = R.id.toolbar;
                                                                                                                                                        ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i);
                                                                                                                                                        if (constraintLayout5 != null) {
                                                                                                                                                            i = R.id.toolbar1;
                                                                                                                                                            LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i);
                                                                                                                                                            if (linearLayout3 != null) {
                                                                                                                                                                i = R.id.tv_scale;
                                                                                                                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i);
                                                                                                                                                                if (textView6 != null) {
                                                                                                                                                                    i = R.id.txt_reset;
                                                                                                                                                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i);
                                                                                                                                                                    if (textView7 != null) {
                                                                                                                                                                        return new ActivityMainBinding((ConstraintLayout) view, myButton, myButton2, constraintLayout, myButton3, imageView, myButton4, myButton5, textView, linearLayout, textView2, myButton6, constraintLayout2, relativeLayout, constraintLayout3, imageView2, button, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, viewFindChildViewById, viewFindChildViewById2, viewFindChildViewById3, viewFindChildViewById4, viewFindChildViewById5, viewFindChildViewById6, viewFindChildViewById7, viewFindChildViewById8, viewFindChildViewById9, linearLayout2, mySilder, textView3, constraintLayout4, textView4, textView5, constraintLayout5, linearLayout3, textView6, textView7);
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
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
