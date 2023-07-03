package org.galliumpowered.internal.plugin.commands;

import net.kyori.adventure.text.Component;
import org.galliumpowered.api.annotations.Command;
import org.galliumpowered.commandsys.CommandContext;

public class PingCommand {
    @Command(aliases = {"ping"}, description = "Pong")
    public void pingCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(Component.text("Pong"));
    }
}
