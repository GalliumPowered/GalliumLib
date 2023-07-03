package org.galliumpowered.commandsys;

import org.galliumpowered.Gallium;
import org.galliumpowered.api.annotations.Command;
import org.galliumpowered.plugin.metadata.PluginMeta;

public class PluginCommandManager {
    private PluginMeta meta;
    public PluginCommandManager(PluginMeta meta) {
        this.meta = meta;
    }

    /**
     * Register a command under the current container
     * @param command An instance of a class which has a {@link Command} annotation
     */
    public void registerCommand(Object command) {
        Gallium.getCommandManager().registerCommand(command, meta);
    }

}
