package net.zenoc.gallium.internal.plugin.commands;

import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.commandsys.CommandContext;

public class PingCommand {
    @Command(aliases = {"ping"}, description = "Pong")
    public void pingCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(ChatMessage.from("Pong!"));
    }
}
