package net.zenoc.gallium.internal.plugin.commands;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.commandsys.CommandContext;

public class GalliumCommand {
    @Command(aliases = {"gallium"}, description = "Information about Gallium")
    public void galliumCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--- Gallium " + Colors.WHITE + "-" + Colors.GREEN + " Version 1.1.0-beta.3 ---"));
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "Developers: " + Colors.WHITE + "SlimeDiamond, TheKodeToad"));
//        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "Contributors: " + Colors.WHITE + "Nobody! Maybe be the first?"));
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "API version: " + Colors.WHITE + Gallium.getVersion()));
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "Minecraft version: " + Colors.WHITE + Gallium.getNMSBridge().getServerVersion()));
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--------------------"));
    }
}
