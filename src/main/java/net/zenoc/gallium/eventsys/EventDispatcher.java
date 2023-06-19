package net.zenoc.gallium.event;

public interface EventDispatcher {
    /**
     * Fire a ServerStartEvent
     */
    void fireServerStart();

    /**
     * Fire a player join event
     */
    void firePlayerJoin();

    /**
     * Fire a player disconnect event
     */
    void firePlayerDisconnect();
}
