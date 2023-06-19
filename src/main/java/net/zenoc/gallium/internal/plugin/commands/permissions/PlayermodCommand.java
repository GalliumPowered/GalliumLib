package net.zenoc.gallium.internal.plugin.commands.permissions;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.api.world.entity.Player;
import net.zenoc.gallium.commandsys.CommandCaller;
import net.zenoc.gallium.commandsys.CommandContext;
import net.zenoc.gallium.exceptions.GalliumDatabaseException;
import net.zenoc.gallium.permissionsys.Group;

import java.sql.SQLException;
import java.util.StringJoiner;

public class PlayermodCommand {
    @Command(aliases = {"playermod"}, description = "Modify a player's permissions and groups", neededPerms = "PERMSYS")
    public void playerModCommand(CommandContext ctx) {
        CommandCaller caller = ctx.getCaller();
        String[] args = ctx.getCommandArgs();
        // /playermod
        if (args.length == 1) {
            sendUsage(caller);
            return;
        }
        Player.fromName(args[1]).ifPresentOrElse(player -> {
            // /playermod <player>
            if (args.length == 2) {
                caller.sendMessage(ChatMessage.from(Colors.GREEN + "--- Player Info ---"));
                caller.sendMessage(ChatMessage.from(player.getPrefix() + player.getName()));
                player.getGroup().ifPresentOrElse(group -> {
                    caller.sendMessage(ChatMessage.from("Group: " + group.getName()));
                }, () -> {
                    caller.sendMessage(ChatMessage.from("Group: Not in a group"));
                });
                StringJoiner joiner = new StringJoiner(", ");
                for (String permission : player.getPermissions()) {
                    joiner.add(permission);
                }
                if (joiner.length() == 0) {
                    caller.sendMessage(ChatMessage.from("Permissions: No permissions"));
                } else {
                    caller.sendMessage(ChatMessage.from("Permissions: " + joiner));
                }

                ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--------------------"));
            } else if (args.length == 3) {
                if (args[2].equalsIgnoreCase("ungroup")) {
                    try {
                        player.ungroup();
                        caller.sendMessage(ChatMessage.from(Colors.GREEN + "Removed group from " + Colors.WHITE + player.getName()));
                    } catch (SQLException e) {
                        throw new GalliumDatabaseException(e);
                    }
                } else {
                    sendUsage(caller);
                }
            } else {
                if (args[2].equalsIgnoreCase("group")) {
                    String groupName = args[3];
                    try {
                        for (Group group : Gallium.getGroupManager().getGroups()) {
                            if (group.getName().equalsIgnoreCase(groupName)) {
                                player.setGroup(group);
                                caller.sendMessage(ChatMessage.from(Colors.GREEN + "Set " + Colors.WHITE + player.getName() + Colors.GREEN + " group to " + Colors.WHITE + group.getName()));
                                return;
                            }
                            caller.sendMessage(ChatMessage.from(Colors.LIGHT_RED + "Could not find that group!"));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else if (args[2].equalsIgnoreCase("permission")) {
                    String permission = args[3].toUpperCase();
                    if (player.hasPermission(permission)) {
                        try {
                            player.removePermission(permission);
                            caller.sendMessage(ChatMessage.from(Colors.GREEN + "Removed permission " + Colors.WHITE + permission + Colors.GREEN + " from " + Colors.WHITE + player.getName()));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            player.addPermission(permission);
                            caller.sendMessage(ChatMessage.from(Colors.GREEN + "Added permission " + Colors.WHITE + permission + Colors.GREEN + " to " + Colors.WHITE + player.getName()));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (args[2].equalsIgnoreCase("prefix")) {
                    String prefix = args[3].replace("&", "§");
                    try {
                        player.setPrefix(prefix);
                        caller.sendMessage(ChatMessage.from(Colors.GREEN + "Set " + Colors.WHITE + player.getName() + Colors.GREEN + "'s prefix to " + prefix));
                    } catch (SQLException e) {
                        throw new GalliumDatabaseException(e);
                    }
                } else {
                    sendUsage(caller);
                }
            }
        }, () -> caller.sendMessage(ChatMessage.from(Colors.LIGHT_RED + "Could not find that player")));
    }

    private void sendUsage(CommandCaller caller) {
        caller.sendMessage(ChatMessage.from(Colors.LIGHT_RED + "/playermod <player> [<group|ungroup|permission|prefix> <group|permission>]"));
    }
}
