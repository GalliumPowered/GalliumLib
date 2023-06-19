package net.zenoc.gallium.galliumlib;

import net.minecraft.server.MinecraftServer;
import net.zenoc.gallium.galliumlib.api.entity.Player;
import net.zenoc.gallium.galliumlib.commands.CommandHandler;
import net.zenoc.gallium.galliumlib.permissions.PermissionManager;
import net.zenoc.gallium.galliumlib.plugin.PluginManager;

import java.util.ArrayList;

public abstract class Gallium {
    protected static Gallium instance;
    protected MinecraftServer nms;
    protected CommandHandler commandHandler;
    protected PermissionManager permissionManager;
    protected PluginManager pluginManager;

    /**
     * Get the NMS server
     * @return Minecraft Server (net.minecraft.server.MinecraftServer)
     */
     public MinecraftServer getNMS() {
         return instance.nms;
     }

    /**
     * Get command handler
     * @return Command handler
     */
    public CommandHandler getCommandHandler() {
        return instance.commandHandler;
    }
    /**
     * Get permission manager
     * @return Permission manager
     */
    public PermissionManager getPermissionManager() {
        return instance.permissionManager;
    }

    /**
     * Get plugin manager
     */
    public PluginManager getPluginManager() {
        return instance.pluginManager;
    }

    /**
     * FOR INTERNAL USE ONLY
     * Set instance
     * @param gallium instance
     */
    public static void setGallium(Gallium gallium) {
        if (instance == null) {
            instance = gallium;
        }
    }

    public ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        getNMS().getPlayerList().getPlayers().forEach(serverPlayer -> players.add(new Player(this, serverPlayer)));
        return players;
    }

    /**
     * Send a message to everyone onlline
     * @param message message to send
     */
    public void sendMsgToAll(ChatMessage message) {
        getOnlinePlayers().forEach(player -> player.sendMessage(message));
    }
}
