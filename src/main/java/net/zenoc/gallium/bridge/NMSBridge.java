package net.zenoc.gallium.bridge;

import net.zenoc.gallium.api.annotations.Args;
import net.zenoc.gallium.world.entity.Player;

import java.util.Optional;

/**
 * For bridging NMS to lib
 */
public interface NMSBridge {
    void registerCommand(String alias, String permission);

    void registerCommand(String alias, String permission, Args[] args);

    Optional<Player> getPlayerByName(String name);

    String getServerVersion();
}
