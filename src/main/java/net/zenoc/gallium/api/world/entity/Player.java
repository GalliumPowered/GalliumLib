package net.zenoc.gallium.api.entity;

import net.kyori.adventure.text.Component;
import net.minecraft.Util;
import net.minecraft.server.level.ServerPlayer;
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

public class Player {
    ServerPlayer nmsPlayer;
    public Player(ServerPlayer nmsPlayer) {
        this.nmsPlayer = nmsPlayer;
    }

    public String getName() {
        return nmsPlayer.getName().getContents().strip();
    }

    public String getUUID() {
        return nmsPlayer.getUUID().toString().strip();
    }

    public boolean hasPermission(String permission) {
        return Gallium.getPermissionManager().playerHasPermission(this, permission);
    }

    public ArrayList<String> getPermissions() {
        return Gallium.getPermissionManager().getPlayerPermissions(this);
    }

    public void sendMessage(ChatMessage message) {
        nmsPlayer.sendMessage(TextTransformer.toMinecraft(Component.text(message.getContent())), Util.NIL_UUID);
    }

    public void disconnect(ChatMessage reason) {
        nmsPlayer.sendMessage(TextTransformer.toMinecraft(Component.text("Disconnecting: " + reason.getContent())), Util.NIL_UUID);
        nmsPlayer.connection.disconnect(TextTransformer.toMinecraft(Component.text(reason.getContent())));
    }

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

    public String getPrefix() {
        String prefix = Gallium.getDatabase().getPlayerPrefix(this);
        return Objects.requireNonNullElse(prefix, Colors.WHITE);
    }

    public Optional<Group> getGroup() {
        return Gallium.getDatabase().getPlayerGroup(this);
    }

    public void setGroup(Group group) throws SQLException {
        Gallium.getDatabase().setPlayerGroup(this, group);
    }

    public void addPermission(String permission) throws SQLException {
        Gallium.getDatabase().insertPermission(permission, new PermissionOwner(this));
    }

    public void removePermission(String permission) throws SQLException {
        Gallium.getDatabase().removePermission(permission, new PermissionOwner(this));
    }

    public void ungroup() throws SQLException {
        Gallium.getDatabase().setPlayerGroup(this, null);
    }

    public void setPrefix(String prefix) throws SQLException {
        Gallium.getDatabase().setPlayerPrefix(this, prefix);
    }
}
