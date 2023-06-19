package net.zenoc.gallium.internal.plugin;

import net.zenoc.gallium.api.annotations.MinecraftCommand;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.commandsys.CommandContext;

public class PingCommand {
    @MinecraftCommand(aliases = {"ping"}, description = "Pong")
    public void pingCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(ChatMessage.from("Pong!"));
    }
}
