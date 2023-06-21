package net.zenoc.gallium.api.event.player;

import net.zenoc.gallium.api.world.entity.Player;

public class PlayerChatEvent extends PlayerEvent {
    String content;
    /**
     * {@inheritDoc}
     */
    public PlayerChatEvent(Player player, String content) {
        super(player);
        this.content = content;
    }

    /**
     * Get the content of the message
     * @return Message content
     */
    public String getMessage() {
        return this.content;
    }
}
