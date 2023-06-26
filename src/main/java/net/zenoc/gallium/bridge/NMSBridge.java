package net.zenoc.gallium.bridge;

import net.zenoc.gallium.api.world.entity.Player;

import java.util.Optional;

/**
 * For bridging NMS to lib
 */
public interface NMSBridge {
    void registerCommand(String alias, String permission);

    Optional<Player> getPlayerByName(String name);

    String getServerVersion();
}
