package net.zenoc.gallium.internal.plugin.listeners;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.EventListener;
import net.zenoc.gallium.world.entity.Player;
import net.zenoc.gallium.api.event.player.PlayerJoinEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class PlayerJoinListener {
    private static final Logger log = LogManager.getLogger();
    @EventListener
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        log.debug("Internal plugin player join event called");
        Player player = event.getPlayer();
        if (!Gallium.getDatabase().playerExists(player)) {
            log.info(player.getName() + " with UUID " + player.getUUID() + " is not in database. Is this the first time the player has joined? Adding to database");
            Gallium.getDatabase().insertPlayer(player);
        } else {
            log.debug("In DB");
        }
    }
}
