package net.zenoc.gallium.plugin;

public interface PluginLoader {
    /**
     * Load a plugin
     * @param plugin The plugin
     */
    void loadPlugin(Plugin plugin);

    /**
     * Unload a plugin
     * @param plugin The plugin
     */
    void unloadPlugin(Plugin plugin);
}
