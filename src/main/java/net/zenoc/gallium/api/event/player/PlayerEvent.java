package net.zenoc.gallium.api.event.player;

import net.zenoc.gallium.api.event.CancelableEvent;
import net.zenoc.gallium.api.world.entity.Player;
import net.zenoc.gallium.api.event.Event;

public class PlayerEvent extends CancelableEvent {
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
