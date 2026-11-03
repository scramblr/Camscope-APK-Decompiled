package org.simple.eventbus.handler;

import android.os.Handler;
import android.os.Looper;
import org.simple.eventbus.Subscription;

/* JADX INFO: loaded from: classes.dex */
public class UIThreadEventHandler implements EventHandler {
    private Handler mUIHandler = new Handler(Looper.getMainLooper());
    DefaultEventHandler mEventHandler = new DefaultEventHandler();

    @Override // org.simple.eventbus.handler.EventHandler
    public void handleEvent(final Subscription subscription, final Object obj) {
        this.mUIHandler.post(new Runnable() { // from class: org.simple.eventbus.handler.UIThreadEventHandler.1
            @Override // java.lang.Runnable
            public void run() {
                UIThreadEventHandler.this.mEventHandler.handleEvent(subscription, obj);
            }
        });
    }
}
