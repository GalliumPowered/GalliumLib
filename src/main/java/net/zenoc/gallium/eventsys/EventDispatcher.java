package net.zenoc.gallium.eventsys;

import net.zenoc.gallium.api.event.Event;

public interface EventDispatcher {
    /**
     * Call an event
     * @param event
     */
    void callEvent(Event event);
}
