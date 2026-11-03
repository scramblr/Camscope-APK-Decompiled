package org.simple.eventbus;

import android.util.Log;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SubsciberMethodHunter {
    Map<EventType, CopyOnWriteArrayList<Subscription>> mSubcriberMap;

    public SubsciberMethodHunter(Map<EventType, CopyOnWriteArrayList<Subscription>> map) {
        this.mSubcriberMap = map;
    }

    public void findSubcribeMethods(Object obj) {
        Class<?>[] parameterTypes;
        if (this.mSubcriberMap == null) {
            throw new NullPointerException("the mSubcriberMap is null. ");
        }
        for (Class<?> superclass = obj.getClass(); superclass != null && !isSystemCalss(superclass.getName()); superclass = superclass.getSuperclass()) {
            for (Method method : superclass.getDeclaredMethods()) {
                Subscriber subscriber = (Subscriber) method.getAnnotation(Subscriber.class);
                if (subscriber != null && (parameterTypes = method.getParameterTypes()) != null && parameterTypes.length == 1) {
                    EventType eventType = new EventType(convertType(parameterTypes[0]), subscriber.tag());
                    subscibe(eventType, new TargetMethod(method, eventType, subscriber.mode()), obj);
                }
            }
        }
    }

    private void subscibe(EventType eventType, TargetMethod targetMethod, Object obj) {
        CopyOnWriteArrayList<Subscription> copyOnWriteArrayList = this.mSubcriberMap.get(eventType);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        }
        Subscription subscription = new Subscription(obj, targetMethod);
        if (copyOnWriteArrayList.contains(subscription)) {
            return;
        }
        copyOnWriteArrayList.add(subscription);
        this.mSubcriberMap.put(eventType, copyOnWriteArrayList);
    }

    public void removeMethodsFromMap(Object obj) {
        Iterator<CopyOnWriteArrayList<Subscription>> it = this.mSubcriberMap.values().iterator();
        while (it.hasNext()) {
            CopyOnWriteArrayList<Subscription> next = it.next();
            if (next != null) {
                LinkedList linkedList = new LinkedList();
                for (Subscription subscription : next) {
                    Object obj2 = subscription.subscriber.get();
                    if (isObjectsEqual(obj2, obj) || obj2 == null) {
                        Log.d("", "### 移除订阅 " + obj.getClass().getName());
                        linkedList.add(subscription);
                    }
                }
                next.removeAll(linkedList);
            }
            if (next == null || next.size() == 0) {
                it.remove();
            }
        }
    }

    private boolean isObjectsEqual(Object obj, Object obj2) {
        return obj != null && obj.equals(obj2);
    }

    private Class<?> convertType(Class<?> cls) {
        if (cls.equals(Boolean.TYPE)) {
            return Boolean.class;
        }
        if (cls.equals(Integer.TYPE)) {
            return Integer.class;
        }
        if (cls.equals(Float.TYPE)) {
            return Float.class;
        }
        return cls.equals(Double.TYPE) ? Double.class : cls;
    }

    private boolean isSystemCalss(String str) {
        return str.startsWith("java.") || str.startsWith("javax.") || str.startsWith("android.");
    }
}
