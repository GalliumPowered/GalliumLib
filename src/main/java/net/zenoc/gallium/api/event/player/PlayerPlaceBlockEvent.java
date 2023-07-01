package net.zenoc.gallium.api.event.player;

import net.zenoc.gallium.world.block.Block;
import net.zenoc.gallium.world.entity.Player;

public class PlayerPlaceBlockEvent extends PlayerEvent {
    private Player player;
    private Block block;

    /**
     * A player break block event
     *
     * @param player The player
     * @param block The block
     */
    public PlayerPlaceBlockEvent(Player player, Block block) {
        super(player);
        this.player = player;
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
