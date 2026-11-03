package com.joyhonest.wifi_check_adj;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/* JADX INFO: loaded from: classes.dex */
public class ConditionVideoView extends VideoView {
    public ConditionVideoView(Context context) {
        super(context);
    }

    public ConditionVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ConditionVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.VideoView, android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
    }
}
