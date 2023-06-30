package net.zenoc.gallium.plugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.exceptions.BadPluginException;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.internal.plugin.GalliumPlugin;
import net.zenoc.gallium.plugin.inject.modules.InjectPluginModule;
import net.zenoc.gallium.plugin.metadata.PluginMeta;
import net.zenoc.gallium.plugin.metadata.PluginMetaLoader;
import net.zenoc.gallium.plugin.loader.PluginLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

public class PluginManager {
    private ArrayList<PluginContainer> plugins = new ArrayList<>();
    public PluginLoader javaPluginLoader = new PluginLoader();
    private static final Logger log = LogManager.getLogger("Gallium/PluginManager");
    public PluginManager() {

    }

    public Optional<PluginContainer> getPluginById(String id) {
        return plugins.stream()
                .filter(container -> container.getMeta().getId().equalsIgnoreCase(id))
                .findFirst();
    }

    /**
     * Get the plugins on the server
     * @return ArrayList of plugins
     */
    public ArrayList<PluginContainer> getLoadedPlugins() {
        return plugins;
    }

    public void loadPlugins() throws IOException {
        // Load internal plugin
        log.info("Loading plugin Gallium");
        GalliumPlugin internalPlugin = new GalliumPlugin();
        PluginMeta internalPluginMeta = PluginMetaLoader.getPluginMetaFromAnnotation(internalPlugin.getClass());
        PluginContainer internalPluginContainer = new PluginContainer();

        internalPluginContainer.setMeta(internalPluginMeta);

        Injector internalPluginInjector = Guice.createInjector(new InjectPluginModule(internalPluginContainer));
        internalPluginContainer.setInjector(internalPluginInjector);
        internalPluginContainer.setInstance(internalPluginInjector.getInstance(internalPlugin.getClass()));

        internalPluginContainer.setLifecycleState(PluginLifecycleState.ENABLED);

        addPlugin(internalPluginContainer);

        // Load plugins in the plugins directory
        File pluginsDir = Gallium.getPluginsDirectory();
        if (!pluginsDir.exists()) {
            pluginsDir.mkdirs();
        }
        if (pluginsDir.listFiles() == null) {
            log.info("No plugins are installed!");
            return;
        }
        for (File file : pluginsDir.listFiles()) {
            log.info("Loading plugin {}", file.getName());
            try {
                javaPluginLoader.loadPlugin(file).ifPresent(container -> {
                    container.setLifecycleState(PluginLifecycleState.ENABLED);
                    addPlugin(container);
                });

            } catch (Exception e) {
                throw new PluginLoadFailException(e);
            }
        }
    }

    /**
     * Unload all plugins on the server
     */
    public void unloadPlugins() {
        Iterator it = plugins.iterator();
        while (it.hasNext()) {
            PluginContainer plugin = (PluginContainer) it.next();
            log.info("Unloading plugin {}", plugin.getMeta().getId());
            plugin.setLifecycleState(PluginLifecycleState.DISABLED);
            Gallium.getCommandManager().unregisterAllPluginCommands(plugin.getMeta());
            removePlugin(plugin);
        }
    }

    /**
     * FOR INTERNAL USE ONLY. DO NOT CALL THIS METHOD.
     * Adds a plugin to the ArrayList
     * @param plugin The {@link PluginContainer} instance
     */
    public void addPlugin(PluginContainer plugin) {
        plugins.add(plugin);
    }

    /**
     * FOR INTERNAL USE ONLY. DO NOT CALL THIS METHOD.
     * Removes a plugin from the ArrayList
     * @param plugin The {@link PluginContainer} instance
     */
    public void removePlugin(PluginContainer plugin) {
        plugins.remove(plugin);
    }
}
