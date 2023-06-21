package net.zenoc.gallium.plugin.java;

import net.zenoc.gallium.api.annotations.PluginLifecycleListener;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.plugin.Plugin;
import net.zenoc.gallium.plugin.PluginLifecycleState;
import net.zenoc.gallium.plugin.PluginLoader;

import java.util.Arrays;

/**
 * Loads Java plugins
 */
public class JavaPluginLoader implements PluginLoader {

    /**
     * Loads a {@link JavaPlugin}
     * @param plugin The plugin
     */
    @Override
    public void loadPlugin(Plugin plugin) {
//        Gallium.getPluginManager().addPlugin(plugin, meta);
        try {
            _load((JavaPlugin) plugin);
        } catch (ClassCastException e) {
            throw new PluginLoadFailException(e);
        }
    }

    /**
     * Unloads a {@link JavaPlugin}
     * @param plugin The plugin
     */
    @Override
    public void unloadPlugin(Plugin plugin) {
        try {
            _unload((JavaPlugin) plugin);
        } catch (ClassCastException e) {
            throw new PluginLoadFailException(e);
        }
    }

    /**
     * Loads a plugin
     * @param plugin The plugin
     */
    @SuppressWarnings("deprecation")
    private void _load(JavaPlugin plugin) {
        Class<? extends JavaPlugin> clazz = plugin.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == PluginLifecycleState.ENABLED)
                .forEach(method -> {
                    try {
                        method.invoke(plugin);
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });
    }

    /**
     * Unload a plugin
     * @param plugin The plugin
     */
    @SuppressWarnings("deprecation")
    private void _unload(JavaPlugin plugin) {
        Class<? extends JavaPlugin> clazz = plugin.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == PluginLifecycleState.DISABLED)
                .forEach(method -> {
                    try {
                        method.invoke(plugin);
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });
    }
}
