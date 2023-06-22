package net.zenoc.gallium.plugin;

public interface PluginLoader {
    /**
     * Load a plugin
     * @param plugin The plugin
     * @param meta The metadata of the plugin
     */
    void loadPlugin(Plugin plugin, PluginMeta meta);
}
