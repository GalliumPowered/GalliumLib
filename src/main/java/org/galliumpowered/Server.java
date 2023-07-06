package org.galliumpowered;

import net.kyori.adventure.text.Component;
import org.galliumpowered.world.entity.Player;

import java.util.ArrayList;

public interface Server {

    /**
     * Get online players as a number
     *
     * @return number of {@link Player}s online
     */
    int currentPlayerCount();

    /**
     * Get the maximum number of players that can be on the server
     *
     * @return number max {@link Player}s
     */
    int maxPlayerCount();

    /**
     * Get all the online players
     * @return ArrayList of the players
     */
    ArrayList<Player> getOnlinePlayers();

    /**
     * Send a message to everyone online
     * @param message message to send
     */
    default void sendMsgToAll(Component component) {
        getOnlinePlayers().forEach(player -> player.sendMessage(component));
    }
}