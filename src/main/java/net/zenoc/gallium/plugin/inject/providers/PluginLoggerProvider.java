package net.zenoc.gallium.plugin.inject.providers;

import com.google.inject.Provider;
import net.zenoc.gallium.plugin.metadata.PluginMeta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PluginLoggerProvider implements Provider<Logger> {
    private PluginMeta meta;

    public PluginLoggerProvider(PluginMeta meta) {
        this.meta = meta;
    }

    @Override
    public Logger get() {
        return LogManager.getLogger("plugin/" + meta.getId());
    }
}
