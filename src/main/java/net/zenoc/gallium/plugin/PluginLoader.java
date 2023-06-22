package net.zenoc.gallium.plugin;

public interface PluginLoader {
    /**
     * Load a plugin
     * @param plugin The plugin
     * @param meta The plugin's {@link PluginMeta}
     */
    void loadPlugin(Plugin plugin, PluginMeta meta);

    /**
     * Unload a plugin
     * @param plugin The plugin
     * @param meta The plugin's {@link PluginMeta}
     */
    void unloadPlugin(Plugin plugin, PluginMeta meta);
}
