package com.joyhonest.wifi_check_adj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class WaitView extends LinearLayout {
    private TextView TitleViewA;
    Animation hyperspaceJumpAnimation;
    private ImageView spaceshipImage;

    public WaitView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.loading_dialog, this);
        this.TitleViewA = (TextView) findViewById(R.id.tipTextView);
        this.spaceshipImage = (ImageView) findViewById(R.id.img);
        this.hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            this.spaceshipImage.startAnimation(this.hyperspaceJumpAnimation);
        } else {
            this.spaceshipImage.clearAnimation();
        }
    }

    private void setTitleView(String str) {
        this.TitleViewA.setText(str);
    }

    private void setTitleView(int i) {
        this.TitleViewA.setText(i);
    }
}
