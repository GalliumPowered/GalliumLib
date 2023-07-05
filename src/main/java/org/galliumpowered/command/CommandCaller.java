package org.galliumpowered.command;

import net.kyori.adventure.text.Component;
import org.galliumpowered.world.entity.Player;

import java.util.Optional;

public interface CommandCaller {

    Optional<Player> getPlayer();

    default void sendMessage(Component component) {
        this.getPlayer().ifPresentOrElse(player -> player.sendMessage(component), () -> System.out.println(component));
    }
}
