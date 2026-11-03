package org.simple.eventbus.matchpolicy;

import java.util.LinkedList;
import java.util.List;
import org.simple.eventbus.EventType;

/* JADX INFO: loaded from: classes.dex */
public class StrictMatchPolicy implements MatchPolicy {
    @Override // org.simple.eventbus.matchpolicy.MatchPolicy
    public List<EventType> findMatchEventTypes(EventType eventType, Object obj) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(eventType);
        return linkedList;
    }
}
