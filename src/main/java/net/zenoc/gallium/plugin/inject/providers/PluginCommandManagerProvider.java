package net.zenoc.gallium.plugin.inject.providers;

import com.google.inject.Provider;
import net.zenoc.gallium.commandsys.PluginCommandManager;
import net.zenoc.gallium.plugin.metadata.PluginMeta;

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
