package net.zenoc.gallium.api.event;

import net.zenoc.gallium.Gallium;

public abstract class Event {
    /**
     * Call the event
     * @return this
     */
    public Event call() {
        Gallium.getEventDispatcher().callEvent(this);
        return this;
    }
}
