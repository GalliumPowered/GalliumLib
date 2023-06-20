package net.zenoc.gallium.api.world.entity;

import net.kyori.adventure.text.Component;
import net.minecraft.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.zenoc.gallium.api.Gamemode;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.permissionsys.Group;
import net.zenoc.gallium.permissionsys.PermissionOwner;
import net.zenoc.gallium.util.TextTransformer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Player extends Entity {
    ServerPlayer nmsPlayer;
    public Player(ServerPlayer nmsPlayer) {
        super(nmsPlayer.getName().getContents().strip());
        this.nmsPlayer = nmsPlayer;
    }

    /**
     * Get the player's UUID
     * @return the uuid
     */
    public String getUUID() {
        return nmsPlayer.getUUID().toString().strip();
    }

    /**
     * Whether the player has a permission
     * @param permission the permission
     * @return whether the player has the permission
     */
    public boolean hasPermission(String permission) {
        return Gallium.getPermissionManager().playerHasPermission(this, permission);
    }

    /**
     * Get the players permissions
     * @return {@link ArrayList} of permissions
     */
    public ArrayList<String> getPermissions() {
        return Gallium.getPermissionManager().getPlayerPermissions(this);
    }

    /**
     * Send a message to the player
     * @param message a {@link ChatMessage}
     */
    public void sendMessage(ChatMessage message) {
        nmsPlayer.sendMessage(TextTransformer.toMinecraft(Component.text(message.getContent())), Util.NIL_UUID);
    }

    /**
     * Disconnect the player
     * @param reason the reason for disconnecting them
     */
    public void disconnect(ChatMessage reason) {
        nmsPlayer.sendMessage(TextTransformer.toMinecraft(Component.text("Disconnecting: " + reason.getContent())), Util.NIL_UUID);
        nmsPlayer.connection.disconnect(TextTransformer.toMinecraft(Component.text(reason.getContent())));
    }

    /**
     * Disconnect the player
     */
    public void disconnect() {
        this.sendMessage(ChatMessage.from("Disconnecting"));
        nmsPlayer.disconnect();
    }

    /**
     * Get a player by their username
     * @param username the username of the desired player
     * @return the {@link Player}
     */
    public static Optional<Player> fromName(String username) {
        AtomicReference<Player> player = new AtomicReference<>();
        Gallium.getNMS().getPlayerList().getPlayers().stream()
                .filter(serverPlayer -> serverPlayer.getName().getContents().strip().equalsIgnoreCase(username))
                .findFirst().ifPresent(serverPlayer -> player.set(new Player(serverPlayer)));

        if (player.get() == null) {
            return Optional.empty();
        } else {
            return Optional.of(player.get());
        }
    }

    /**
     * Get the player's prefix
     * @return the prefix
     */
    public String getPrefix() {
        String prefix = Gallium.getDatabase().getPlayerPrefix(this);
        return Objects.requireNonNullElse(prefix, Colors.WHITE);
    }

    /**
     * Get the player's group
     * @return the {@link Group}
     */
    public Optional<Group> getGroup() {
        return Gallium.getDatabase().getPlayerGroup(this);
    }

    /**
     * Set a player's group
     * @param group the {@link Group}
     * @throws SQLException
     */
    public void setGroup(Group group) throws SQLException {
        Gallium.getDatabase().setPlayerGroup(this, group);
    }

    /**
     * Add a permission to the player
     * @param permission the permission node
     * @throws SQLException
     */
    public void addPermission(String permission) throws SQLException {
        Gallium.getDatabase().insertPermission(permission, new PermissionOwner(this));
    }

    /**
     * Remove a permission from the player
     * @param permission the permission node
     * @throws SQLException
     */
    public void removePermission(String permission) throws SQLException {
        Gallium.getDatabase().removePermission(permission, new PermissionOwner(this));
    }

    /**
     * Ungroup the player
     * @throws SQLException
     */
    public void ungroup() throws SQLException {
        Gallium.getDatabase().setPlayerGroup(this, null);
    }

    /**
     * Set the player's prefix
     * @param prefix the prefix
     * @throws SQLException
     */
    public void setPrefix(String prefix) throws SQLException {
        Gallium.getDatabase().setPlayerPrefix(this, prefix);
    }

    /**
     * Teleport the player
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     */
    public void teleport(double x, double y, double z) {
        nmsPlayer.teleportTo(x, y, z);
    }

    /**
     * Set the player's {@link Gamemode}
     * @param gamemode The {@link Gamemode}
     */
    public void setGamemode(Gamemode gamemode) {
        nmsPlayer.setGameMode(GameType.byId(gamemode.getId()));
    }
}
