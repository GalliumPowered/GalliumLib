package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.world.entity.Player;

import java.util.Optional;

public class CommandCaller {
    String name;
    CommandCallerType type;
    public CommandCaller(String name, CommandCallerType type) {
        this.name = name;
        this.type = type;
    }

    public Optional<Player> getPlayer() {
        if (type == CommandCallerType.CONSOLE) return Optional.empty();
        return Optional.of(new Player(Gallium.getNMS().getPlayerList().getPlayerByName(name)));
    }

    public void sendMessage(ChatMessage message) {
        this.getPlayer().ifPresentOrElse(player -> player.sendMessage(message), () -> System.out.println(message.getContent()));
    }
}
