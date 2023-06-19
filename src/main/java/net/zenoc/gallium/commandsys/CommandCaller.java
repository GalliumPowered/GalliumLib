package net.zenoc.gallium.command;

import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.entity.Player;

import java.util.Optional;

public class CommandCaller {
    Gallium gallium;
    String name;
    CommandCallerType type;
    public CommandCaller(Gallium gallium, String name, CommandCallerType type) {
        this.gallium = gallium;
        this.name = name;
        this.type = type;
    }

    public Optional<Player> getPlayer() {
        if (type == CommandCallerType.CONSOLE) return Optional.empty();
        return Optional.of(new Player(gallium, gallium.getNMS().getPlayerList().getPlayerByName(name)));
    }

    public void sendMessage(ChatMessage message) {
        this.getPlayer().ifPresentOrElse(player -> player.sendMessage(message), () -> System.out.println(message.getContent()));
    }
}
