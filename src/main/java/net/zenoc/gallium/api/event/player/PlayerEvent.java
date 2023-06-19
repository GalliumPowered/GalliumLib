package net.zenoc.gallium.event.player;

import net.zenoc.gallium.api.entity.Player;
import net.zenoc.gallium.event.Event;

public abstract class PlayerEvent extends Event {
    Player player;
    /**
     * The player
     */
    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
