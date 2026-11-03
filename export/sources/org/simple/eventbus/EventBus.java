package org.simple.eventbus;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import org.simple.eventbus.handler.AsyncEventHandler;
import org.simple.eventbus.handler.DefaultEventHandler;
import org.simple.eventbus.handler.EventHandler;
import org.simple.eventbus.handler.UIThreadEventHandler;
import org.simple.eventbus.matchpolicy.DefaultMatchPolicy;
import org.simple.eventbus.matchpolicy.MatchPolicy;

/* JADX INFO: loaded from: classes.dex */
public final class EventBus {
    private static final String DESCRIPTOR = "EventBus";
    private static EventBus sDefaultBus;
    private String mDesc;
    EventDispatcher mDispatcher;
    ThreadLocal<Queue<EventType>> mLocalEvents;
    SubsciberMethodHunter mMethodHunter;
    private List<EventType> mStickyEvents;
    private final Map<EventType, CopyOnWriteArrayList<Subscription>> mSubcriberMap;

    private EventBus() {
        this(DESCRIPTOR);
    }

    public EventBus(String str) {
        this.mDesc = DESCRIPTOR;
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        this.mSubcriberMap = concurrentHashMap;
        this.mStickyEvents = Collections.synchronizedList(new LinkedList());
        this.mLocalEvents = new ThreadLocal<Queue<EventType>>() { // from class: org.simple.eventbus.EventBus.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public Queue<EventType> initialValue() {
                return new ConcurrentLinkedQueue();
            }
        };
        this.mDispatcher = new EventDispatcher();
        this.mMethodHunter = new SubsciberMethodHunter(concurrentHashMap);
        this.mDesc = str;
    }

    public static EventBus getDefault() {
        if (sDefaultBus == null) {
            synchronized (EventBus.class) {
                if (sDefaultBus == null) {
                    sDefaultBus = new EventBus();
                }
            }
        }
        return sDefaultBus;
    }

    public void register(Object obj) {
        if (obj == null) {
            return;
        }
        synchronized (this) {
            this.mMethodHunter.findSubcribeMethods(obj);
        }
    }

    public void registerSticky(Object obj) {
        register(obj);
        this.mDispatcher.dispatchStickyEvents(obj);
    }

    public void unregister(Object obj) {
        if (obj == null) {
            return;
        }
        synchronized (this) {
            this.mMethodHunter.removeMethodsFromMap(obj);
        }
    }

    public void post(Object obj) {
        post(obj, EventType.DEFAULT_TAG);
    }

    public void post(Object obj, String str) {
        if (obj == null) {
            Log.e(getClass().getSimpleName(), "The event object is null");
        } else {
            this.mLocalEvents.get().offer(new EventType(obj.getClass(), str));
            this.mDispatcher.dispatchEvents(obj);
        }
    }

    public void postSticky(Object obj) {
        postSticky(obj, EventType.DEFAULT_TAG);
    }

    public void postSticky(Object obj, String str) {
        if (obj == null) {
            Log.e(getClass().getSimpleName(), "The event object is null");
            return;
        }
        EventType eventType = new EventType(obj.getClass(), str);
        eventType.event = obj;
        this.mStickyEvents.add(eventType);
    }

    public void removeStickyEvent(Class<?> cls) {
        removeStickyEvent(cls, EventType.DEFAULT_TAG);
    }

    public void removeStickyEvent(Class<?> cls, String str) {
        Iterator<EventType> it = this.mStickyEvents.iterator();
        while (it.hasNext()) {
            EventType next = it.next();
            if (next.paramClass.equals(cls) && next.tag.equals(str)) {
                it.remove();
            }
        }
    }

    public List<EventType> getStickyEvents() {
        return this.mStickyEvents;
    }

    public void setMatchPolicy(MatchPolicy matchPolicy) {
        this.mDispatcher.mMatchPolicy = matchPolicy;
    }

    public void setUIThreadEventHandler(EventHandler eventHandler) {
        this.mDispatcher.mUIThreadEventHandler = eventHandler;
    }

    public void setPostThreadHandler(EventHandler eventHandler) {
        this.mDispatcher.mPostThreadHandler = eventHandler;
    }

    public void setAsyncEventHandler(EventHandler eventHandler) {
        this.mDispatcher.mAsyncEventHandler = eventHandler;
    }

    public Map<EventType, CopyOnWriteArrayList<Subscription>> getSubscriberMap() {
        return this.mSubcriberMap;
    }

    public Queue<EventType> getEventQueue() {
        return this.mLocalEvents.get();
    }

    public synchronized void clear() {
        this.mLocalEvents.get().clear();
        this.mSubcriberMap.clear();
    }

    public String getDescriptor() {
        return this.mDesc;
    }

    public EventDispatcher getDispatcher() {
        return this.mDispatcher;
    }

    private class EventDispatcher {
        EventHandler mAsyncEventHandler;
        private Map<EventType, List<EventType>> mCacheEventTypes;
        MatchPolicy mMatchPolicy;
        EventHandler mPostThreadHandler;
        EventHandler mUIThreadEventHandler;

        private EventDispatcher() {
            this.mUIThreadEventHandler = new UIThreadEventHandler();
            this.mPostThreadHandler = new DefaultEventHandler();
            this.mAsyncEventHandler = new AsyncEventHandler();
            this.mCacheEventTypes = new ConcurrentHashMap();
            this.mMatchPolicy = new DefaultMatchPolicy();
        }

        void dispatchEvents(Object obj) {
            Queue<EventType> queue = EventBus.this.mLocalEvents.get();
            while (queue.size() > 0) {
                deliveryEvent(queue.poll(), obj);
            }
        }

        private void deliveryEvent(EventType eventType, Object obj) {
            Iterator<EventType> it = getMatchedEventTypes(eventType, obj).iterator();
            while (it.hasNext()) {
                handleEvent(it.next(), obj);
            }
        }

        private void handleEvent(EventType eventType, Object obj) {
            List<Subscription> list = (List) EventBus.this.mSubcriberMap.get(eventType);
            if (list == null) {
                return;
            }
            for (Subscription subscription : list) {
                getEventHandler(subscription.threadMode).handleEvent(subscription, obj);
            }
        }

        private List<EventType> getMatchedEventTypes(EventType eventType, Object obj) {
            List<EventType> list;
            if (this.mCacheEventTypes.containsKey(eventType)) {
                list = this.mCacheEventTypes.get(eventType);
            } else {
                List<EventType> listFindMatchEventTypes = this.mMatchPolicy.findMatchEventTypes(eventType, obj);
                this.mCacheEventTypes.put(eventType, listFindMatchEventTypes);
                list = listFindMatchEventTypes;
            }
            return list != null ? list : new ArrayList();
        }

        void dispatchStickyEvents(Object obj) {
            Iterator it = EventBus.this.mStickyEvents.iterator();
            while (it.hasNext()) {
                handleStickyEvent((EventType) it.next(), obj);
            }
        }

        private void handleStickyEvent(EventType eventType, Object obj) {
            List<EventType> matchedEventTypes = getMatchedEventTypes(eventType, eventType.event);
            Object obj2 = eventType.event;
            for (EventType eventType2 : matchedEventTypes) {
                Log.e("", "### 找到的类型 : " + eventType2.paramClass.getSimpleName() + ", event class : " + obj2.getClass().getSimpleName());
                List<Subscription> list = (List) EventBus.this.mSubcriberMap.get(eventType2);
                if (list != null) {
                    for (Subscription subscription : list) {
                        EventHandler eventHandler = getEventHandler(subscription.threadMode);
                        if (isTarget(subscription, obj) && (subscription.eventType.equals(eventType2) || subscription.eventType.paramClass.isAssignableFrom(eventType2.paramClass))) {
                            eventHandler.handleEvent(subscription, obj2);
                        }
                    }
                }
            }
        }

        private boolean isTarget(Subscription subscription, Object obj) {
            Object obj2 = subscription.subscriber != null ? subscription.subscriber.get() : null;
            return obj == null || !(obj == null || obj2 == null || !obj2.equals(obj));
        }

        private EventHandler getEventHandler(ThreadMode threadMode) {
            if (threadMode == ThreadMode.ASYNC) {
                return this.mAsyncEventHandler;
            }
            if (threadMode == ThreadMode.POST) {
                return this.mPostThreadHandler;
            }
            return this.mUIThreadEventHandler;
        }
    }
}
