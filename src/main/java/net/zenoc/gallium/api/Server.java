package net.zenoc.gallium.api;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.api.world.entity.Player;

import java.util.ArrayList;

public interface Server {
    /**
     * Get the server hostname
     *
     * @return the hostname
     */
    String hostname();

    /**
     * Get online players as a number
     *
     * @return number of {@link Player}s online
     */
    static int currentPlayerCount() {
        return Gallium.getNMS().getPlayerCount();
    }

    /**
     * Get the maximum number of players that can be on the server
     *
     * @return number max {@link Player}s
     */
    static int maxPlayerCount() {
        return Gallium.getNMS().getMaxPlayers();
    }

    /**
     * Get all the online players
     * @return ArrayList of the players
     */
    static ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Gallium.getNMS().getPlayerList().getPlayers().forEach(serverPlayer -> players.add(new Player(serverPlayer)));
        return players;
    }

    /**
     * Send a message to everyone online
     * @param message message to send
     */
    static void sendMsgToAll(ChatMessage message) {
        getOnlinePlayers().forEach(player -> player.sendMessage(message));
    }
}
