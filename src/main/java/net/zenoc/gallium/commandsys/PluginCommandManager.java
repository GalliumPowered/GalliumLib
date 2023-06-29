package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.plugin.PluginContainer;
import net.zenoc.gallium.plugin.metadata.PluginMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class PluginCommandManager {
    private PluginMeta meta;
    public PluginCommandManager(PluginMeta meta) {
        this.meta = meta;
    }

    public void registerCommand(Object command) {
        Gallium.getCommandManager().registerCommand(command, meta);
    }

}
