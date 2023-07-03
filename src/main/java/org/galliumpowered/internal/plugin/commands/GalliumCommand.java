package org.galliumpowered.internal.plugin.commands;

import net.kyori.adventure.text.Component;
import org.galliumpowered.Gallium;
import org.galliumpowered.api.annotations.Command;
import org.galliumpowered.api.chat.Colors;
import org.galliumpowered.commandsys.CommandContext;

public class GalliumCommand {
    @Command(aliases = {"gallium"}, description = "Information about Gallium")
    public void galliumCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "--- Gallium " + Colors.WHITE + "-" + Colors.GREEN + " Version 1.1.0-beta.3 ---"));
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "Developers: " + Colors.WHITE + "SlimeDiamond, TheKodeToad"));
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "API version: " + Colors.WHITE + Gallium.getVersion()));
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "Minecraft version: " + Colors.WHITE + Gallium.getNMSBridge().getServerVersion()));
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "--------------------"));
    }
}
