package net.zenoc.gallium.internal.plugin.commands.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.commandsys.CommandContext;

public class PluginInfoCommand {
    @Command(aliases = {"plugininfo"}, description = "Show information about a plugin", neededPerms = "PLUGINS")
    public void pluginInfoCommand(CommandContext ctx) {
        if (ctx.getCommandArgs().length == 1) {
            ctx.getCaller().sendMessage(ChatMessage.from(Colors.LIGHT_RED + "/plugininfo <plugin id>"));
            return;
        }

        Gallium.getPluginManager().getPluginById(ctx.getCommandArgs()[1]).ifPresentOrElse(container -> {
            ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "----- Plugin info -----"));
            ctx.getCaller().sendMessage(ChatMessage.from("Name: " + container.getMeta().getName()));
            ctx.getCaller().sendMessage(ChatMessage.from("ID: " + container.getMeta().getId()));
            ctx.getCaller().sendMessage(ChatMessage.from("Description: " + container.getMeta().getDescription()));
            ctx.getCaller().sendMessage(ChatMessage.from("Version: " + container.getMeta().getVersion()));
            ctx.getCaller().sendMessage(ChatMessage.from("Authors: " + String.join(", ", container.getMeta().getAuthors())));
            ctx.getCaller().sendMessage(ChatMessage.from("Main Class: " + container.getMeta().getMainClass()));
            ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "-----------------------"));
        }, () -> {
            ctx.getCaller().sendMessage(ChatMessage.from(Colors.RED + "Could not find that plugin!"));
        });
    }
}
