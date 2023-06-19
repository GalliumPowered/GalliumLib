package net.zenoc.gallium.permissions;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.entity.Player;

public class PermissionManager {
    public PermissionManager() {

    }

    public boolean playerHasPermission(Player player, Permission permission) {
        // TEMPORARY
        // TODO: Some sort of database stuff, I'm not entirely sure yet
        return true;
    }
}
