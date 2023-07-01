package net.zenoc.gallium.internal.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.commandsys.CommandContext;

public class PingCommand {
    @Command(aliases = {"ping"}, description = "Pong")
    public void pingCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(Component.text("Pong"));
    }
}
