package net.zenoc.gallium.plugin;

public class Plugin {
    private PluginMeta meta;

    /**
     * Sets the plugin's {@link PluginMeta}
     * @param meta The {@link PluginMeta}
     */
    public void setMeta(PluginMeta meta) {
        if (this.meta == null) {
            this.meta = meta;
        }
    }

    /**
     * Get the plugin's {@link PluginMeta}
     * @return The plugin's {@link PluginMeta}
     */
    public PluginMeta getMeta() {
        return this.meta;
    }
}
