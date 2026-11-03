package org.simple.eventbus;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
class TargetMethod {
    public EventType eventType;
    public Method method;
    public ThreadMode threadMode;

    public TargetMethod(Method method, EventType eventType, ThreadMode threadMode) {
        this.method = method;
        method.setAccessible(true);
        this.eventType = eventType;
        this.threadMode = threadMode;
    }

    public int hashCode() {
        EventType eventType = this.eventType;
        int iHashCode = ((eventType == null ? 0 : eventType.hashCode()) + 31) * 31;
        Method method = this.method;
        return iHashCode + (method != null ? method.getName().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TargetMethod targetMethod = (TargetMethod) obj;
        EventType eventType = this.eventType;
        if (eventType == null) {
            if (targetMethod.eventType != null) {
                return false;
            }
        } else if (!eventType.equals(targetMethod.eventType)) {
            return false;
        }
        Method method = this.method;
        if (method == null) {
            if (targetMethod.method != null) {
                return false;
            }
        } else if (!method.getName().equals(targetMethod.method.getName())) {
            return false;
        }
        return true;
    }
}
