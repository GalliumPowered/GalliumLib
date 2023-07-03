package org.galliumpowered.plugin.inject.providers;

import com.google.inject.Provider;
import org.galliumpowered.commandsys.PluginCommandManager;
import org.galliumpowered.plugin.metadata.PluginMeta;

public class PluginCommandManagerProvider implements Provider<PluginCommandManager> {
    PluginMeta meta;
    public PluginCommandManagerProvider(PluginMeta meta) {
        this.meta = meta;
    }

    @Override
    public PluginCommandManager get() {
        return new PluginCommandManager(meta);
    }
}
