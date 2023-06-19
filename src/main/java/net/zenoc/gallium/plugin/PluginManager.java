package net.zenoc.gallium.galliumlib.plugin;

import net.zenoc.gallium.galliumlib.Gallium;
import net.zenoc.gallium.galliumlib.exceptions.PluginLoadFailException;

import java.util.ArrayList;
import java.util.Optional;

public class PluginManager {
    Gallium gallium;
    ArrayList<Plugin> plugins = new ArrayList<>();
    public PluginManager(Gallium gallium) {
        this.gallium = gallium;
    }

    public void loadPlugin(Plugin plugin) throws PluginLoadFailException {
        // TODO
    }

    public void unloadPlugin(Plugin plugin) {
        // TODO
    }

    public Optional<Plugin> getPluginByName(String name) {
        // TODO
        return Optional.empty();
    }

    public ArrayList<Plugin> getLoadedPlugins() {
        return plugins;
    }
}
