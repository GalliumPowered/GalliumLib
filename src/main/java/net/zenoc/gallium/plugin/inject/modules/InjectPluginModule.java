package net.zenoc.gallium.plugin.inject.modules;

import com.google.inject.AbstractModule;
import net.zenoc.gallium.commandsys.PluginCommandManager;
import net.zenoc.gallium.plugin.PluginContainer;
import net.zenoc.gallium.plugin.inject.providers.PluginCommandManagerProvider;
import net.zenoc.gallium.plugin.inject.providers.PluginContainerProvider;
import net.zenoc.gallium.plugin.metadata.PluginMeta;
import net.zenoc.gallium.plugin.inject.providers.PluginLoggerProvider;
import org.apache.logging.log4j.Logger;

public class InjectPluginModule extends AbstractModule {
    private PluginContainer container;
    public InjectPluginModule(PluginContainer container) {
        this.container = container;
    }

    @Override
    protected void configure() {
        bind(Logger.class).toProvider(new PluginLoggerProvider(container.getMeta()));
        bind(PluginCommandManager.class).toProvider(new PluginCommandManagerProvider(container.getMeta()));
        bind(PluginContainer.class).toProvider(new PluginContainerProvider(container));
    }
}
