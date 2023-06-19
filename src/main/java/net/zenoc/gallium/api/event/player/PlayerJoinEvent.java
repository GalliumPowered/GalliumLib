package net.zenoc.gallium.event.player;

import net.zenoc.gallium.api.entity.Player;

/**
 * A player join event
 */
public abstract class PlayerJoinEvent extends PlayerEvent {
    /**
     * The player
     *
     * @param player The player
     */
    public PlayerJoinEvent(Player player) {
        super(player);
    }

    /**
     * Hide the message
     */
    abstract void suppressMessage();
}
