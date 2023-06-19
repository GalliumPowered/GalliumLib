package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.Gallium;

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
}
