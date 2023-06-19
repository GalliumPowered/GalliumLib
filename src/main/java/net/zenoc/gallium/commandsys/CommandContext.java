package net.zenoc.gallium.command;

import net.zenoc.gallium.Gallium;

public interface CommandContext {
    /**
     * Get the command caller
     */
    CommandCaller getCaller();

    /**
     * Get gallium
     */
    Gallium getGallium();
}
