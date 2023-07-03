package org.galliumpowered.eventsys;

import org.galliumpowered.api.event.Event;

public interface EventDispatcher {
    /**
     * Call an event
     * @param event
     */
    void callEvent(Event event);
}
