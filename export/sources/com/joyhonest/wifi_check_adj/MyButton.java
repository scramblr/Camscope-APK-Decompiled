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
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class MyButton extends View {
    public boolean bSelected;
    private int color1;
    private Bitmap img;
    private Bitmap img_sel;
    private float inx;
    public Lock lock;
    public int nStyle;
    private Paint paint;
    private RectF rect1;
    private RectF rect2;
    private RectF rect3;
    public String sTitle;
    public String sValue;
    private Timer timer;
    private TimerTask timerTask;

    public MyButton(Context context) {
        super(context);
        this.nStyle = 0;
        Init(context, null);
    }

    public MyButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.nStyle = 0;
        Init(context, attributeSet);
    }

    public MyButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.nStyle = 0;
        Init(context, attributeSet);
    }

    public void setsValue(String str) {
        this.sValue = str;
        invalidate();
    }

    private void stopTimer() {
        this.lock.lock();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
            this.timerTask.cancel();
            this.timerTask = null;
        }
        this.lock.unlock();
    }

    private void startTimer() {
        this.lock.lock();
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
            this.timerTask.cancel();
            this.timerTask = null;
        }
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() { // from class: com.joyhonest.wifi_check_adj.MyButton.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (MyButton.this.bSelected) {
                    MyButton.this.invalidate();
                }
            }
        };
        this.timerTask = timerTask;
        this.timer.schedule(timerTask, 0L, 10L);
        this.lock.unlock();
    }

    public void setbSelected(boolean z) {
        if (!z) {
            this.bSelected = false;
            stopTimer();
            invalidate();
        } else {
            if (this.bSelected) {
                return;
            }
            this.inx = 0.0f;
            this.bSelected = true;
            startTimer();
        }
    }

    private void Init(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        this.nStyle = 0;
        this.lock = new ReentrantLock();
        this.paint = new Paint(1);
        this.rect1 = new RectF();
        this.rect2 = new RectF();
        this.rect3 = new RectF();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MyButton);
        this.color1 = typedArrayObtainStyledAttributes.getColor(R.styleable.MyButton_color_1, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MyButton_img, -1);
        if (resourceId != -1) {
            this.img = BitmapFactory.decodeResource(getContext().getResources(), resourceId);
        }
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MyButton_img_sel, -1);
        if (resourceId2 != -1) {
            this.img_sel = BitmapFactory.decodeResource(getContext().getResources(), resourceId2);
        }
        this.bSelected = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MyButton_selected, false);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.MyButton_sValue);
        this.sValue = string;
        if (string == null) {
            this.sValue = "";
        }
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.MyButton_title);
        this.sTitle = string2;
        if (string2 == null) {
            this.sTitle = "";
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setAntiAlias(true);
        canvas.drawColor(0);
        int width = getWidth();
        int height = getHeight();
        if (width < height || this.img == null || this.img_sel == null) {
            return;
        }
        float f = width - height;
        float f2 = f / 10.0f;
        this.rect1.left = 0.0f;
        this.rect1.top = 4.0f;
        float f3 = height;
        this.rect1.right = this.inx + f3;
        this.rect1.bottom = height - 4;
        RectF rectF = this.rect2;
        rectF.top = 0.0f;
        rectF.left = 0.0f;
        this.rect2.right = f3;
        this.rect2.bottom = f3;
        float f4 = 0.25f * f3;
        this.rect3.left = f;
        this.rect3.top = f4;
        RectF rectF2 = this.rect3;
        rectF2.right = rectF2.left + 8.0f;
        this.rect3.bottom = f3 - f4;
        if (this.bSelected) {
            if (this.nStyle == 0) {
                float f5 = width;
                if (this.rect1.right >= f5) {
                    this.rect1.right = f5;
                    stopTimer();
                }
                this.paint.setColor(this.color1);
                this.paint.setStyle(Paint.Style.FILL);
                float f6 = f3 / 2.0f;
                canvas.drawRoundRect(this.rect1, f6, f6, this.paint);
                this.paint.setStyle(Paint.Style.STROKE);
                canvas.drawBitmap(this.img_sel, (Rect) null, this.rect2, this.paint);
                this.inx += f2;
                this.paint.setColor(-16588804);
                this.paint.setStrokeWidth(0.0f);
                this.paint.setStyle(Paint.Style.FILL);
                this.paint.setTextSize(45.0f);
                this.paint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
                float f7 = f6 - ((fontMetrics.ascent + fontMetrics.descent) / 2.0f);
                canvas.drawText(this.sTitle, f3 + ((width - (height * 2)) / 2.0f), f7, this.paint);
                canvas.drawText(this.sValue, f + f6, f7, this.paint);
                this.paint.setColor(-7961199);
                this.paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(this.rect3, this.paint);
                return;
            }
            return;
        }
        canvas.drawBitmap(this.img, (Rect) null, this.rect2, this.paint);
    }
}
