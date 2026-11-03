package com.joyhonest.wifi_check_adj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MySilder extends View {
    private int leftColor;
    public int nData;
    public int nValue;
    private Paint paint;
    private RectF rect;
    private RectF rectImg;
    private RectF rectR;
    private int rightColor;
    private Bitmap trackimg;

    private void Init(Context context, AttributeSet attributeSet) {
        this.paint = new Paint(1);
        this.rect = new RectF();
        this.rectR = new RectF();
        this.rectImg = new RectF();
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MySilder);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MySilder_trackimg, -1);
        if (resourceId != -1) {
            this.trackimg = BitmapFactory.decodeResource(getContext().getResources(), resourceId);
        }
        this.leftColor = typedArrayObtainStyledAttributes.getInt(R.styleable.MySilder_leftColor, -1);
        this.rightColor = typedArrayObtainStyledAttributes.getInt(R.styleable.MySilder_rightColor, -8355712);
        typedArrayObtainStyledAttributes.recycle();
    }

    public MySilder(Context context) {
        super(context);
        this.nValue = 50;
        this.nData = 0;
        Init(context, null);
    }

    public MySilder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.nValue = 50;
        this.nData = 0;
        Init(context, attributeSet);
    }

    public MySilder(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.nValue = 50;
        this.nData = 0;
        Init(context, attributeSet);
    }

    public void setTrackimg(int i) {
        this.trackimg = BitmapFactory.decodeResource(getContext().getResources(), i);
        invalidate();
    }

    public void setnValue(int i) {
        this.nValue = i;
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int height = getHeight();
        int i = (int) (height / 2.0f);
        int width = getWidth();
        int x = (int) motionEvent.getX();
        if (x < i) {
            x = 0;
        }
        int i2 = width - i;
        if (x > i2) {
            x = i2;
        }
        int i3 = (int) ((x / (width - height)) * 100.0f);
        int i4 = i3 >= 0 ? i3 : 0;
        if (i4 >= 100) {
            i4 = 100;
        }
        if (action == 0) {
            this.nData = i4;
            this.nValue = i4;
            invalidate();
            EventBus.getDefault().post(this, "Slider_OnChanged");
        }
        if (2 == action) {
            this.nData = i4;
            this.nValue = i4;
            invalidate();
            EventBus.getDefault().post(this, "Slider_OnChanged");
        }
        if (1 == action) {
            this.nData = 65536 + i4;
            this.nValue = i4;
            invalidate();
            EventBus.getDefault().post(this, "Slider_OnChanged");
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(0);
        if (width < height) {
            return;
        }
        float f = this.nValue / 100.0f;
        this.rect.left = 0.0f;
        this.rect.top = height / 4;
        float f2 = width;
        float f3 = f * f2;
        this.rect.right = f3;
        RectF rectF = this.rect;
        float f4 = height / 2;
        rectF.bottom = rectF.top + f4;
        this.paint.setColor(this.leftColor);
        canvas.drawRoundRect(this.rect, f4, f4, this.paint);
        this.rectR.left = f3;
        this.rectR.right = f2;
        this.rectR.top = this.rect.top;
        this.rectR.bottom = this.rect.bottom;
        this.paint.setColor(this.rightColor);
        canvas.drawRoundRect(this.rectR, f4, f4, this.paint);
        float f5 = height;
        float f6 = f5 / 2.0f;
        float f7 = f3 - f6;
        if (f7 < 0.0f) {
            f7 = 0.0f;
        }
        float f8 = f6 * 2.0f;
        if (f7 + f8 > f2) {
            f7 = f2 - f8;
        }
        this.rectImg.left = f7;
        RectF rectF2 = this.rectImg;
        rectF2.right = rectF2.left + f5;
        this.rectImg.top = 0.0f;
        this.rectImg.bottom = f5;
        canvas.drawBitmap(this.trackimg, (Rect) null, this.rectImg, this.paint);
    }
}
