package net.zenoc.gallium.plugin.java;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.PluginLifecycleListener;
import net.zenoc.gallium.exceptions.BadPluginException;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.plugin.Plugin;
import net.zenoc.gallium.plugin.PluginLifecycleState;
import net.zenoc.gallium.plugin.PluginLoader;
import net.zenoc.gallium.plugin.PluginMeta;

import java.util.Arrays;

/**
 * Loads plugins in the Java programming lanuage
 */
public class JavaPluginLoader implements PluginLoader {

    /**
     * Loads a {@link JavaPlugin}
     * @param plugin The plugin
     * @param meta The metadata of the plugin
     */
    @Override
    public void loadPlugin(Plugin plugin, PluginMeta meta) {
        try {
            _load((JavaPlugin) plugin, meta);
        } catch (ClassCastException e) {
            throw new PluginLoadFailException(e);
        }
    }

    /**
     * Unloads a {@link JavaPlugin}
     * @param plugin The plugin
     * @param meta The metadata of the plugin
     */
    @Override
    public void unloadPlugin(Plugin plugin, PluginMeta meta) {
        try {
            _unload((JavaPlugin) plugin, meta);
        } catch (ClassCastException e) {
            throw new PluginLoadFailException(e);
        }
    }

    /**
     * Loads a plugin
     * @param plugin The plugin
     * @param meta A {@link Plugin} annotation
     */
    @SuppressWarnings("deprecation")
    private void _load(JavaPlugin plugin, PluginMeta meta) {
        Class<? extends JavaPlugin> clazz = plugin.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == PluginLifecycleState.ENABLED)
                .forEach(method -> {
                    try {
                        method.invoke(clazz.newInstance());
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });
        Gallium.getPluginManager().addPlugin(plugin, meta);
    }

    /**
     * Unload a plugin
     * @param plugin The plugin
     * @param meta The plugin's {@link PluginMeta}
     */
    @SuppressWarnings("deprecation")
    private void _unload(JavaPlugin plugin, PluginMeta meta) {
        Class<? extends JavaPlugin> clazz = plugin.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == PluginLifecycleState.DISABLED)
                .forEach(method -> {
                    try {
                        method.invoke(clazz.newInstance());
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });
    }
}
