package net.zenoc.gallium.api.config;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.plugin.PluginContainer;

import java.nio.file.Path;

public class PluginConfiguration extends DefaultConfiguration {
    public PluginConfiguration(PluginContainer container) {
        super(Path.of(Gallium.getPluginConfigDirectory().getAbsolutePath(), container.getMeta().getId()), "config.cfg");
    }
}
