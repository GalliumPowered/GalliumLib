package net.zenoc.gallium.api.world.entity;

import net.kyori.adventure.text.Component;
import net.zenoc.gallium.api.Gamemode;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.permissionsys.Group;
import net.zenoc.gallium.permissionsys.PermissionOwner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface Player extends Entity {
    /**
     * Get the player's UUID
     * @return the uuid
     */
    String getUUID();

    /**
     * Whether the player has a permission
     * @param permission the permission
     * @return whether the player has the permission
     */
    default boolean hasPermission(String permission) {
        return Gallium.getPermissionManager().playerHasPermission(this, permission);
    }

    /**
     * Get the players permissions
     * @return {@link ArrayList} of permissions
     */
    default ArrayList<String> getPermissions() {
        return Gallium.getPermissionManager().getPlayerPermissions(this);
    }

    /**
     * Send a message to the player
     * @param message a {@link ChatMessage}
     */
    void sendMessage(ChatMessage message);

    /**
     * Disconnect the player
     * @param reason the reason for disconnecting them
     */
    void disconnect(ChatMessage reason);

    /**
     * Disconnect the player
     */
    void disconnect();

    /**
     * Get the player's prefix
     * @return the prefix
     */
    default String getPrefix() {
        String prefix = Gallium.getDatabase().getPlayerPrefix(this);
        return Objects.requireNonNullElse(prefix, Colors.WHITE);
    }

    /**
     * Get the player's group
     * @return the {@link Group}
     */
    default Optional<Group> getGroup() {
        return Gallium.getDatabase().getPlayerGroup(this);
    }

    /**
     * Set a player's group
     * @param group the {@link Group}
     * @throws SQLException
     */
    default void setGroup(Group group) throws SQLException {
        Gallium.getDatabase().setPlayerGroup(this, group);
    }

    /**
     * Add a permission to the player
     * @param permission the permission node
     * @throws SQLException
     */
    default void addPermission(String permission) throws SQLException {
        Gallium.getDatabase().insertPermission(permission, new PermissionOwner(this));
    }

    /**
     * Remove a permission from the player
     * @param permission the permission node
     * @throws SQLException
     */
    default void removePermission(String permission) throws SQLException {
        Gallium.getDatabase().removePermission(permission, new PermissionOwner(this));
    }

    /**
     * Ungroup the player
     * @throws SQLException
     */
    default void ungroup() throws SQLException {
        Gallium.getDatabase().setPlayerGroup(this, null);
    }

    /**
     * Set the player's prefix
     * @param prefix the prefix
     * @throws SQLException
     */
    default void setPrefix(String prefix) throws SQLException {
        Gallium.getDatabase().setPlayerPrefix(this, prefix);
    }

    /**
     * Teleport the player
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     */
    void teleport(double x, double y, double z);

    /**
     * Set the player's {@link Gamemode}
     * @param gamemode The {@link Gamemode}
     */
    void setGamemode(Gamemode gamemode);
}
