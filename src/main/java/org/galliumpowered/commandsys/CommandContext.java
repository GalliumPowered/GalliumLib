package org.galliumpowered.commandsys;

import org.galliumpowered.world.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public interface CommandContext {
    /**
     * Get the command caller
     */
    CommandCaller getCaller();

    /**
     * Command arguments
     *
     * @return Command arguments
     */
    String[] getCommandArgs();
    CommandContext ifPlayer(Consumer<Player> consumer);

    CommandContext ifConsole(Consumer<CommandCaller> consumer);

    Optional<String> getArgument(String name);
}
