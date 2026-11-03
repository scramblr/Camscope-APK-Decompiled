package org.simple.eventbus.matchpolicy;

import java.util.LinkedList;
import java.util.List;
import org.simple.eventbus.EventType;

/* JADX INFO: loaded from: classes.dex */
public class DefaultMatchPolicy implements MatchPolicy {
    @Override // org.simple.eventbus.matchpolicy.MatchPolicy
    public List<EventType> findMatchEventTypes(EventType eventType, Object obj) {
        LinkedList linkedList = new LinkedList();
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            linkedList.add(new EventType(superclass, eventType.tag));
            addInterfaces(linkedList, superclass, eventType.tag);
        }
        return linkedList;
    }

    private void addInterfaces(List<EventType> list, Class<?> cls, String str) {
        if (cls == null) {
            return;
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            if (!list.contains(cls2)) {
                list.add(new EventType(cls2, str));
                addInterfaces(list, cls2, str);
            }
        }
    }
}
