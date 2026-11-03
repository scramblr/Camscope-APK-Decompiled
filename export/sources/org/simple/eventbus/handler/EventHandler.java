package org.simple.eventbus.handler;

import org.simple.eventbus.Subscription;

/* JADX INFO: loaded from: classes.dex */
public interface EventHandler {
    void handleEvent(Subscription subscription, Object obj);
}
