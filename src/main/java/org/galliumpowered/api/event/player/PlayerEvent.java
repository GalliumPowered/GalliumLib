package org.galliumpowered.api.event.player;

import org.galliumpowered.api.event.CancelableEvent;
import org.galliumpowered.world.entity.Player;

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
