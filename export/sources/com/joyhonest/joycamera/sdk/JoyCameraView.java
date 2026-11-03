package com.joyhonest.joycamera.sdk;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
public class JoyCameraView extends GLSurfaceView implements GLSurfaceView.Renderer {
    public JoyCameraView(Context context) {
        super(context);
        init();
    }

    public JoyCameraView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(this);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        wifiCamera.eglinit();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        wifiCamera.eglchangeLayout(i, i2);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        wifiCamera.egldrawFrame();
    }
}
