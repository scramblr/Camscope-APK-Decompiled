package com.joyhonest.wifi_check_adj;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;
import androidx.appcompat.widget.AppCompatImageView;

/* JADX INFO: loaded from: classes.dex */
public class MyImageView extends AppCompatImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.0f;
    private static final float SUPER_MIN_MULTIPLIER = 1.0f;
    public boolean bCanScal;
    private Context context;
    private ZoomVariables delayedZoomVariables;
    private GestureDetector.OnDoubleTapListener doubleTapListener;
    private Fling fling;
    private boolean imageRenderedAtLeastOnce;
    private float[] m;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleDetector;
    private ImageView.ScaleType mScaleType;
    private float matchViewHeight;
    private float matchViewWidth;
    private Matrix matrix;
    private float maxScale;
    private float minScale;
    private float normalizedScale;
    private boolean onDrawReady;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;
    private State state;
    private float superMaxScale;
    private float superMinScale;
    private OnTouchImageViewListener touchImageViewListener;
    private View.OnTouchListener userTouchListener;
    private int viewHeight;
    private int viewWidth;

    public interface OnTouchImageViewListener {
        void onMove();
    }

    private enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            return 0.0f;
        }
        return f;
    }

    private float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f5 = f2 - f3;
            f4 = 0.0f;
        } else {
            f4 = f2 - f3;
            f5 = 0.0f;
        }
        if (f < f4) {
            return (-f) + f4;
        }
        if (f > f5) {
            return (-f) + f5;
        }
        return 0.0f;
    }

    public MyImageView(Context context) {
        super(context);
        this.bCanScal = false;
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        sharedConstructing(context);
    }

    public MyImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bCanScal = false;
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        sharedConstructing(context);
    }

    public MyImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.bCanScal = false;
        this.doubleTapListener = null;
        this.userTouchListener = null;
        this.touchImageViewListener = null;
        sharedConstructing(context);
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        AnonymousClass1 anonymousClass1 = null;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener(this, anonymousClass1));
        this.mGestureDetector = new GestureDetector(context, new GestureListener(this, anonymousClass1));
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 4.0f;
        this.superMinScale = 1.0f * 1.0f;
        this.superMaxScale = 4.0f * 1.0f;
        setImageMatrix(this.matrix);
        setScaleType(ImageView.ScaleType.MATRIX);
        setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener(this, anonymousClass1));
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.userTouchListener = onTouchListener;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener onTouchImageViewListener) {
        this.touchImageViewListener = onTouchImageViewListener;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.doubleTapListener = onDoubleTapListener;
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i) {
        super.setImageResource(i);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        savePreviousImageValues();
        fitImageToView();
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.FIT_START || scaleType == ImageView.ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        }
        if (scaleType == ImageView.ScaleType.MATRIX) {
            super.setScaleType(ImageView.ScaleType.MATRIX);
            return;
        }
        this.mScaleType = scaleType;
        if (this.onDrawReady) {
            setZoom(this);
        }
    }

    @Override // android.widget.ImageView
    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public boolean isZoomed() {
        return this.normalizedScale != 1.0f;
    }

    public RectF getZoomedRect() {
        if (this.mScaleType == ImageView.ScaleType.FIT_XY) {
            throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        }
        PointF pointFTransformCoordTouchToBitmap = transformCoordTouchToBitmap(0.0f, 0.0f, true);
        PointF pointFTransformCoordTouchToBitmap2 = transformCoordTouchToBitmap(this.viewWidth, this.viewHeight, true);
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        return new RectF(pointFTransformCoordTouchToBitmap.x / intrinsicWidth, pointFTransformCoordTouchToBitmap.y / intrinsicHeight, pointFTransformCoordTouchToBitmap2.x / intrinsicWidth, pointFTransformCoordTouchToBitmap2.y / intrinsicHeight);
    }

    private void savePreviousImageValues() {
        Matrix matrix = this.matrix;
        if (matrix == null || this.viewHeight == 0 || this.viewWidth == 0) {
            return;
        }
        matrix.getValues(this.m);
        this.prevMatrix.setValues(this.m);
        this.prevMatchViewHeight = this.matchViewHeight;
        this.prevMatchViewWidth = this.matchViewWidth;
        this.prevViewHeight = this.viewHeight;
        this.prevViewWidth = this.viewWidth;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.m);
        bundle.putFloatArray("matrix", this.m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        return bundle;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.normalizedScale = bundle.getFloat("saveScale");
            float[] floatArray = bundle.getFloatArray("matrix");
            this.m = floatArray;
            this.prevMatrix.setValues(floatArray);
            this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            this.prevViewHeight = bundle.getInt("viewHeight");
            this.prevViewWidth = bundle.getInt("viewWidth");
            this.imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        ZoomVariables zoomVariables = this.delayedZoomVariables;
        if (zoomVariables != null) {
            setZoom(zoomVariables.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        savePreviousImageValues();
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
        this.superMaxScale = f * 1.0f;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public void setMinZoom(float f) {
        this.minScale = f;
        this.superMinScale = f * 1.0f;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        fitImageToView();
    }

    public void setZoom(float f) {
        setZoom(f, 0.5f, 0.5f);
    }

    public void setZoom(float f, float f2, float f3) {
        setZoom(f, f2, f3, this.mScaleType);
    }

    public void setZoom(float f, float f2, float f3, ImageView.ScaleType scaleType) {
        if (!this.onDrawReady) {
            this.delayedZoomVariables = new ZoomVariables(f, f2, f3, scaleType);
            return;
        }
        if (scaleType != this.mScaleType) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage(f, this.viewWidth / 2, this.viewHeight / 2, true);
        this.matrix.getValues(this.m);
        this.m[2] = -((f2 * getImageWidth()) - (this.viewWidth * 0.5f));
        this.m[5] = -((f3 * getImageHeight()) - (this.viewHeight * 0.5f));
        this.matrix.setValues(this.m);
        fixTrans();
        setImageMatrix(this.matrix);
    }

    public void setZoom(MyImageView myImageView) {
        PointF scrollPosition = myImageView.getScrollPosition();
        setZoom(myImageView.getCurrentZoom(), scrollPosition.x, scrollPosition.y, myImageView.getScaleType());
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        PointF pointFTransformCoordTouchToBitmap = transformCoordTouchToBitmap(this.viewWidth / 2, this.viewHeight / 2, true);
        pointFTransformCoordTouchToBitmap.x /= intrinsicWidth;
        pointFTransformCoordTouchToBitmap.y /= intrinsicHeight;
        return pointFTransformCoordTouchToBitmap;
    }

    public void setScrollPosition(float f, float f2) {
        setZoom(this.normalizedScale, f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fixTrans() {
        this.matrix.getValues(this.m);
        float[] fArr = this.m;
        float f = fArr[2];
        float f2 = fArr[5];
        float fixTrans = getFixTrans(f, this.viewWidth, getImageWidth());
        float fixTrans2 = getFixTrans(f2, this.viewHeight, getImageHeight());
        if (fixTrans == 0.0f && fixTrans2 == 0.0f) {
            return;
        }
        this.matrix.postTranslate(fixTrans, fixTrans2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fixScaleTrans() {
        fixTrans();
        this.matrix.getValues(this.m);
        float imageWidth = getImageWidth();
        int i = this.viewWidth;
        if (imageWidth < i) {
            this.m[2] = (i - getImageWidth()) / 2.0f;
        }
        float imageHeight = getImageHeight();
        int i2 = this.viewHeight;
        if (imageHeight < i2) {
            this.m[5] = (i2 - getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        this.viewWidth = setViewSize(mode, size, intrinsicWidth);
        int viewSize = setViewSize(mode2, size2, intrinsicHeight);
        this.viewHeight = viewSize;
        setMeasuredDimension(this.viewWidth, viewSize);
        fitImageToView();
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0099  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a6  */
    private void fitImageToView() {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0 || this.matrix == null || this.prevMatrix == null) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        float f = intrinsicWidth;
        float fMax = this.viewWidth / f;
        float f2 = intrinsicHeight;
        float f3 = this.viewHeight / f2;
        int i = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
        if (i == 1) {
            fMax = 1.0f;
        } else if (i == 2) {
            fMax = Math.max(fMax, f3);
        } else {
            if (i == 3) {
                fMax = Math.min(1.0f, Math.min(fMax, f3));
                f3 = fMax;
            } else {
                if (i != 4) {
                    if (i != 5) {
                        throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
                    }
                }
                int i2 = this.viewWidth;
                float f4 = i2 - (fMax * f);
                int i3 = this.viewHeight;
                float f5 = i3 - (f3 * f2);
                this.matchViewWidth = i2 - f4;
                this.matchViewHeight = i3 - f5;
                if (isZoomed() && !this.imageRenderedAtLeastOnce) {
                    this.matrix.setScale(fMax, f3);
                    this.matrix.postTranslate(f4 / 2.0f, f5 / 2.0f);
                    this.normalizedScale = 1.0f;
                } else {
                    if (this.prevMatchViewWidth != 0.0f || this.prevMatchViewHeight == 0.0f) {
                        savePreviousImageValues();
                    }
                    this.prevMatrix.getValues(this.m);
                    float[] fArr = this.m;
                    float f6 = this.matchViewWidth / f;
                    float f7 = this.normalizedScale;
                    fArr[0] = f6 * f7;
                    fArr[4] = (this.matchViewHeight / f2) * f7;
                    float f8 = fArr[2];
                    float f9 = fArr[5];
                    translateMatrixAfterRotate(2, f8, this.prevMatchViewWidth * f7, getImageWidth(), this.prevViewWidth, this.viewWidth, intrinsicWidth);
                    translateMatrixAfterRotate(5, f9, this.prevMatchViewHeight * this.normalizedScale, getImageHeight(), this.prevViewHeight, this.viewHeight, intrinsicHeight);
                    this.matrix.setValues(this.m);
                }
                fixTrans();
                setImageMatrix(this.matrix);
            }
            fMax = Math.min(fMax, f3);
        }
        f3 = fMax;
        int i4 = this.viewWidth;
        float f10 = i4 - (fMax * f);
        int i5 = this.viewHeight;
        float f11 = i5 - (f3 * f2);
        this.matchViewWidth = i4 - f10;
        this.matchViewHeight = i5 - f11;
        if (isZoomed()) {
            if (this.prevMatchViewWidth != 0.0f) {
                savePreviousImageValues();
            } else {
                savePreviousImageValues();
            }
            this.prevMatrix.getValues(this.m);
            float[] fArr2 = this.m;
            float f12 = this.matchViewWidth / f;
            float f13 = this.normalizedScale;
            fArr2[0] = f12 * f13;
            fArr2[4] = (this.matchViewHeight / f2) * f13;
            float f14 = fArr2[2];
            float f15 = fArr2[5];
            translateMatrixAfterRotate(2, f14, this.prevMatchViewWidth * f13, getImageWidth(), this.prevViewWidth, this.viewWidth, intrinsicWidth);
            translateMatrixAfterRotate(5, f15, this.prevMatchViewHeight * this.normalizedScale, getImageHeight(), this.prevViewHeight, this.viewHeight, intrinsicHeight);
            this.matrix.setValues(this.m);
        } else {
            if (this.prevMatchViewWidth != 0.0f) {
                savePreviousImageValues();
            } else {
                savePreviousImageValues();
            }
            this.prevMatrix.getValues(this.m);
            float[] fArr3 = this.m;
            float f16 = this.matchViewWidth / f;
            float f17 = this.normalizedScale;
            fArr3[0] = f16 * f17;
            fArr3[4] = (this.matchViewHeight / f2) * f17;
            float f18 = fArr3[2];
            float f19 = fArr3[5];
            translateMatrixAfterRotate(2, f18, this.prevMatchViewWidth * f17, getImageWidth(), this.prevViewWidth, this.viewWidth, intrinsicWidth);
            translateMatrixAfterRotate(5, f19, this.prevMatchViewHeight * this.normalizedScale, getImageHeight(), this.prevViewHeight, this.viewHeight, intrinsicHeight);
            this.matrix.setValues(this.m);
        }
        fixTrans();
        setImageMatrix(this.matrix);
    }

    /* JADX INFO: renamed from: com.joyhonest.wifi_check_adj.MyImageView$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private int setViewSize(int i, int i2, int i3) {
        if (i != Integer.MIN_VALUE) {
            return i != 0 ? i2 : i3;
        }
        return Math.min(i3, i2);
    }

    private void translateMatrixAfterRotate(int i, float f, float f2, float f3, int i2, int i3, int i4) {
        float f4 = i3;
        if (f3 < f4) {
            float[] fArr = this.m;
            fArr[i] = (f4 - (i4 * fArr[0])) * 0.5f;
        } else if (f > 0.0f) {
            this.m[i] = -((f3 - f4) * 0.5f);
        } else {
            this.m[i] = -((((Math.abs(f) + (i2 * 0.5f)) / f2) * f3) - (f4 * 0.5f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setState(State state) {
        this.state = state;
    }

    public boolean canScrollHorizontallyFroyo(int i) {
        return canScrollHorizontally(i);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        this.matrix.getValues(this.m);
        float f = this.m[2];
        if (getImageWidth() < this.viewWidth) {
            return false;
        }
        if (f < -1.0f || i >= 0) {
            return (Math.abs(f) + ((float) this.viewWidth)) + 1.0f < getImageWidth() || i <= 0;
        }
        return false;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        /* synthetic */ GestureListener(MyImageView myImageView, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (MyImageView.this.doubleTapListener != null) {
                return MyImageView.this.doubleTapListener.onSingleTapConfirmed(motionEvent);
            }
            return MyImageView.this.performClick();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            MyImageView.this.performLongClick();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (MyImageView.this.fling != null) {
                MyImageView.this.fling.cancelFling();
            }
            MyImageView.this.fling = MyImageView.this.new Fling((int) f, (int) f2);
            MyImageView myImageView = MyImageView.this;
            myImageView.compatPostOnAnimation(myImageView.fling);
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            boolean zOnDoubleTap = MyImageView.this.doubleTapListener != null ? MyImageView.this.doubleTapListener.onDoubleTap(motionEvent) : false;
            if (MyImageView.this.state != State.NONE) {
                return zOnDoubleTap;
            }
            MyImageView.this.compatPostOnAnimation(MyImageView.this.new DoubleTapZoom(MyImageView.this.normalizedScale == MyImageView.this.minScale ? MyImageView.this.maxScale : MyImageView.this.minScale, motionEvent.getX(), motionEvent.getY(), false));
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (MyImageView.this.doubleTapListener != null) {
                return MyImageView.this.doubleTapListener.onDoubleTapEvent(motionEvent);
            }
            return false;
        }
    }

    private class PrivateOnTouchListener implements View.OnTouchListener {
        private PointF last;

        private PrivateOnTouchListener() {
            this.last = new PointF();
        }

        /* synthetic */ PrivateOnTouchListener(MyImageView myImageView, AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX WARN: Code duplicated, block: B:23:0x00a8  */
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!MyImageView.this.bCanScal) {
                return false;
            }
            MyImageView.this.mScaleDetector.onTouchEvent(motionEvent);
            MyImageView.this.mGestureDetector.onTouchEvent(motionEvent);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            if (MyImageView.this.state == State.NONE || MyImageView.this.state == State.DRAG || MyImageView.this.state == State.FLING) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.last.set(pointF);
                    if (MyImageView.this.fling != null) {
                        MyImageView.this.fling.cancelFling();
                    }
                    MyImageView.this.setState(State.DRAG);
                } else if (action == 1) {
                    MyImageView.this.setState(State.NONE);
                } else if (action != 2) {
                    if (action == 6) {
                        MyImageView.this.setState(State.NONE);
                    }
                } else if (MyImageView.this.state == State.DRAG) {
                    float f = pointF.x - this.last.x;
                    float f2 = pointF.y - this.last.y;
                    MyImageView myImageView = MyImageView.this;
                    float fixDragTrans = myImageView.getFixDragTrans(f, myImageView.viewWidth, MyImageView.this.getImageWidth());
                    MyImageView myImageView2 = MyImageView.this;
                    MyImageView.this.matrix.postTranslate(fixDragTrans, myImageView2.getFixDragTrans(f2, myImageView2.viewHeight, MyImageView.this.getImageHeight()));
                    MyImageView.this.fixTrans();
                    this.last.set(pointF.x, pointF.y);
                }
            }
            MyImageView myImageView3 = MyImageView.this;
            myImageView3.setImageMatrix(myImageView3.matrix);
            if (MyImageView.this.userTouchListener != null) {
                MyImageView.this.userTouchListener.onTouch(view, motionEvent);
            }
            if (MyImageView.this.touchImageViewListener != null) {
                MyImageView.this.touchImageViewListener.onMove();
            }
            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        /* synthetic */ ScaleListener(MyImageView myImageView, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            MyImageView.this.setState(State.ZOOM);
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            MyImageView.this.scaleImage(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (MyImageView.this.touchImageViewListener == null) {
                return true;
            }
            MyImageView.this.touchImageViewListener.onMove();
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(scaleGestureDetector);
            MyImageView.this.setState(State.NONE);
            float f = MyImageView.this.normalizedScale;
            boolean z = true;
            if (MyImageView.this.normalizedScale > MyImageView.this.maxScale) {
                f = MyImageView.this.maxScale;
            } else if (MyImageView.this.normalizedScale < MyImageView.this.minScale) {
                f = MyImageView.this.minScale;
            } else {
                z = false;
            }
            float f2 = f;
            if (z) {
                MyImageView myImageView = MyImageView.this;
                MyImageView.this.compatPostOnAnimation(myImageView.new DoubleTapZoom(f2, myImageView.viewWidth / 2, MyImageView.this.viewHeight / 2, true));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scaleImage(double d, float f, float f2, boolean z) {
        float f3;
        float f4;
        if (z) {
            f3 = this.superMinScale;
            f4 = this.superMaxScale;
        } else {
            f3 = this.minScale;
            f4 = this.maxScale;
        }
        float f5 = this.normalizedScale;
        float f6 = (float) (((double) f5) * d);
        this.normalizedScale = f6;
        if (f6 > f4) {
            this.normalizedScale = f4;
            d = f4 / f5;
        } else if (f6 < f3) {
            this.normalizedScale = f3;
            d = f3 / f5;
        }
        float f7 = (float) d;
        this.matrix.postScale(f7, f7, f, f2);
        fixScaleTrans();
    }

    private class DoubleTapZoom implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        DoubleTapZoom(float f, float f2, float f3, boolean z) {
            MyImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = MyImageView.this.normalizedScale;
            this.targetZoom = f;
            this.stretchImageToSuper = z;
            PointF pointFTransformCoordTouchToBitmap = MyImageView.this.transformCoordTouchToBitmap(f2, f3, false);
            this.bitmapX = pointFTransformCoordTouchToBitmap.x;
            float f4 = pointFTransformCoordTouchToBitmap.y;
            this.bitmapY = f4;
            this.startTouch = MyImageView.this.transformCoordBitmapToTouch(this.bitmapX, f4);
            this.endTouch = new PointF(MyImageView.this.viewWidth / 2, MyImageView.this.viewHeight / 2);
        }

        @Override // java.lang.Runnable
        public void run() {
            float fInterpolate = interpolate();
            MyImageView.this.scaleImage(calculateDeltaScale(fInterpolate), this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            translateImageToCenterTouchPosition(fInterpolate);
            MyImageView.this.fixScaleTrans();
            MyImageView myImageView = MyImageView.this;
            myImageView.setImageMatrix(myImageView.matrix);
            if (MyImageView.this.touchImageViewListener != null) {
                MyImageView.this.touchImageViewListener.onMove();
            }
            if (fInterpolate < 1.0f) {
                MyImageView.this.compatPostOnAnimation(this);
            } else {
                MyImageView.this.setState(State.NONE);
            }
        }

        private void translateImageToCenterTouchPosition(float f) {
            float f2 = this.startTouch.x + ((this.endTouch.x - this.startTouch.x) * f);
            float f3 = this.startTouch.y + (f * (this.endTouch.y - this.startTouch.y));
            PointF pointFTransformCoordBitmapToTouch = MyImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            MyImageView.this.matrix.postTranslate(f2 - pointFTransformCoordBitmapToTouch.x, f3 - pointFTransformCoordBitmapToTouch.y);
        }

        private float interpolate() {
            return this.interpolator.getInterpolation(Math.min(1.0f, (System.currentTimeMillis() - this.startTime) / ZOOM_TIME));
        }

        private double calculateDeltaScale(float f) {
            float f2 = this.startZoom;
            return ((double) (f2 + (f * (this.targetZoom - f2)))) / ((double) MyImageView.this.normalizedScale);
        }
    }

    public PointF transformCoordTouchToBitmap(float f, float f2, boolean z) {
        this.matrix.getValues(this.m);
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        float[] fArr = this.m;
        float f3 = fArr[2];
        float f4 = fArr[5];
        float imageWidth = ((f - f3) * intrinsicWidth) / getImageWidth();
        float imageHeight = ((f2 - f4) * intrinsicHeight) / getImageHeight();
        if (z) {
            imageWidth = Math.min(Math.max(imageWidth, 0.0f), intrinsicWidth);
            imageHeight = Math.min(Math.max(imageHeight, 0.0f), intrinsicHeight);
        }
        return new PointF(imageWidth, imageHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PointF transformCoordBitmapToTouch(float f, float f2) {
        this.matrix.getValues(this.m);
        return new PointF(this.m[2] + (getImageWidth() * (f / getDrawable().getIntrinsicWidth())), this.m[5] + (getImageHeight() * (f2 / getDrawable().getIntrinsicHeight())));
    }

    private class Fling implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;

        Fling(int i, int i2) {
            int imageWidth;
            int i3;
            int imageHeight;
            int i4;
            MyImageView.this.setState(State.FLING);
            this.scroller = MyImageView.this.new CompatScroller(MyImageView.this.context);
            MyImageView.this.matrix.getValues(MyImageView.this.m);
            int i5 = (int) MyImageView.this.m[2];
            int i6 = (int) MyImageView.this.m[5];
            if (MyImageView.this.getImageWidth() > MyImageView.this.viewWidth) {
                imageWidth = MyImageView.this.viewWidth - ((int) MyImageView.this.getImageWidth());
                i3 = 0;
            } else {
                imageWidth = i5;
                i3 = imageWidth;
            }
            if (MyImageView.this.getImageHeight() > MyImageView.this.viewHeight) {
                imageHeight = MyImageView.this.viewHeight - ((int) MyImageView.this.getImageHeight());
                i4 = 0;
            } else {
                imageHeight = i6;
                i4 = imageHeight;
            }
            this.scroller.fling(i5, i6, i, i2, imageWidth, i3, imageHeight, i4);
            this.currX = i5;
            this.currY = i6;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                MyImageView.this.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MyImageView.this.touchImageViewListener != null) {
                MyImageView.this.touchImageViewListener.onMove();
            }
            if (this.scroller.isFinished()) {
                this.scroller = null;
                return;
            }
            if (this.scroller.computeScrollOffset()) {
                int currX = this.scroller.getCurrX();
                int currY = this.scroller.getCurrY();
                int i = currX - this.currX;
                int i2 = currY - this.currY;
                this.currX = currX;
                this.currY = currY;
                MyImageView.this.matrix.postTranslate(i, i2);
                MyImageView.this.fixTrans();
                MyImageView myImageView = MyImageView.this;
                myImageView.setImageMatrix(myImageView.matrix);
                MyImageView.this.compatPostOnAnimation(this);
            }
        }
    }

    private class CompatScroller {
        boolean isPreGingerbread = false;
        OverScroller overScroller;
        Scroller scroller;

        public CompatScroller(Context context) {
            this.overScroller = new OverScroller(context);
        }

        public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (this.isPreGingerbread) {
                this.scroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
            } else {
                this.overScroller.fling(i, i2, i3, i4, i5, i6, i7, i8);
            }
        }

        public void forceFinished(boolean z) {
            if (this.isPreGingerbread) {
                this.scroller.forceFinished(z);
            } else {
                this.overScroller.forceFinished(z);
            }
        }

        public boolean isFinished() {
            if (this.isPreGingerbread) {
                return this.scroller.isFinished();
            }
            return this.overScroller.isFinished();
        }

        public boolean computeScrollOffset() {
            if (this.isPreGingerbread) {
                return this.scroller.computeScrollOffset();
            }
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
        }

        public int getCurrX() {
            if (this.isPreGingerbread) {
                return this.scroller.getCurrX();
            }
            return this.overScroller.getCurrX();
        }

        public int getCurrY() {
            if (this.isPreGingerbread) {
                return this.scroller.getCurrY();
            }
            return this.overScroller.getCurrY();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compatPostOnAnimation(Runnable runnable) {
        postOnAnimation(runnable);
    }

    private class ZoomVariables {
        public float focusX;
        public float focusY;
        public float scale;
        public ImageView.ScaleType scaleType;

        public ZoomVariables(float f, float f2, float f3, ImageView.ScaleType scaleType) {
            this.scale = f;
            this.focusX = f2;
            this.focusY = f3;
            this.scaleType = scaleType;
        }
    }

    private void printMatrixInfo() {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        Log.d(DEBUG, "Scale: " + fArr[0] + " TransX: " + fArr[2] + " TransY: " + fArr[5]);
    }
}
