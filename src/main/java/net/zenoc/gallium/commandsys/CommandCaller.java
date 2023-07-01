package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.world.entity.Player;

import java.util.Optional;

public interface CommandCaller {

    public Optional<Player> getPlayer();

    default void sendMessage(ChatMessage message) {
        this.getPlayer().ifPresentOrElse(player -> player.sendMessage(message), () -> System.out.println(message.getContent()));
    }
}
