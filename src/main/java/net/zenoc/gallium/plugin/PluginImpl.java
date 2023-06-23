package net.zenoc.gallium.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.exceptions.PluginLoadFailException;

import javax.annotation.Nonnull;

public class PluginImpl extends Plugin {
    public PluginImpl(@Nonnull String name, @Nonnull String id, String description, String[] authors, String version) {
        super(name, id, description, authors, version);
    }

    @Override
    public void load() throws PluginLoadFailException {
        Gallium.getPluginManager().loadPlugin(this);
    }

    @Override
    public void unload() {
        Gallium.getPluginManager().unloadPlugin(this);
    }
}
