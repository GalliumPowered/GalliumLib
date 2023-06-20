package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.world.entity.Player;

import java.io.Console;
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
}
