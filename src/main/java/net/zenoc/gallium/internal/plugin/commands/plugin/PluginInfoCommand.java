package net.zenoc.gallium.internal.plugin.commands.plugin;

import net.kyori.adventure.text.Component;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.commandsys.CommandContext;

public class PluginInfoCommand {
    @Command(aliases = {"plugininfo"}, description = "Show information about a plugin", neededPerms = "PLUGINS")
    public void pluginInfoCommand(CommandContext ctx) {
        if (ctx.getCommandArgs().length == 1) {
            ctx.getCaller().sendMessage(Component.text(Colors.LIGHT_RED + "/plugininfo <plugin id>"));
            return;
        }

        Gallium.getPluginManager().getPluginById(ctx.getCommandArgs()[1]).ifPresentOrElse(container -> {
            ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "----- Plugin info -----"));
            ctx.getCaller().sendMessage(Component.text("Name: " + container.getMeta().getName()));
            ctx.getCaller().sendMessage(Component.text("ID: " + container.getMeta().getId()));
            ctx.getCaller().sendMessage(Component.text("Description: " + container.getMeta().getDescription()));
            ctx.getCaller().sendMessage(Component.text("Version: " + container.getMeta().getVersion()));
            ctx.getCaller().sendMessage(Component.text("Authors: " + String.join(", ", container.getMeta().getAuthors())));
            ctx.getCaller().sendMessage(Component.text("Main Class: " + container.getMeta().getMainClass()));
            ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "-----------------------"));
        }, () -> {
            ctx.getCaller().sendMessage(Component.text(Colors.RED + "Could not find that plugin!"));
        });
    }
}
