package org.simple.eventbus.handler;

import android.os.Handler;
import android.os.HandlerThread;
import org.simple.eventbus.Subscription;

/* JADX INFO: loaded from: classes.dex */
public class AsyncEventHandler implements EventHandler {
    DispatcherThread mDispatcherThread;
    EventHandler mEventHandler = new DefaultEventHandler();

    public AsyncEventHandler() {
        DispatcherThread dispatcherThread = new DispatcherThread("AsyncEventHandler");
        this.mDispatcherThread = dispatcherThread;
        dispatcherThread.start();
    }

    @Override // org.simple.eventbus.handler.EventHandler
    public void handleEvent(final Subscription subscription, final Object obj) {
        this.mDispatcherThread.post(new Runnable() { // from class: org.simple.eventbus.handler.AsyncEventHandler.1
            @Override // java.lang.Runnable
            public void run() {
                AsyncEventHandler.this.mEventHandler.handleEvent(subscription, obj);
            }
        });
    }

    class DispatcherThread extends HandlerThread {
        protected Handler mAsyncHandler;

        public DispatcherThread(String str) {
            super(str);
        }

        public void post(Runnable runnable) {
            Handler handler = this.mAsyncHandler;
            if (handler == null) {
                throw new NullPointerException("mAsyncHandler == null, please call start() first.");
            }
            handler.post(runnable);
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            super.start();
            this.mAsyncHandler = new Handler(getLooper());
        }
    }
}
