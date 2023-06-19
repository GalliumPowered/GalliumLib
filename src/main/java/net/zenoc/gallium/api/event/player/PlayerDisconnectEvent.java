package net.zenoc.gallium.event.player;

import net.zenoc.gallium.api.entity.Player;

/**
 * A player disconnect event
 */
public abstract class PlayerDisconnectEvent extends PlayerEvent {
    /**
     * The player
     *
     * @param player The player
     */
    public PlayerDisconnectEvent(Player player) {
        super(player);
    }

    /**
     * Hide the message
     */
    abstract void suppressMessage();
}
