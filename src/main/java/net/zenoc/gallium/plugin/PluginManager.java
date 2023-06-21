package net.zenoc.gallium.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.Plugin;
import net.zenoc.gallium.api.annotations.PluginLifecycleListener;
import net.zenoc.gallium.exceptions.BadPluginException;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.internal.plugin.GalliumPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginManager {
    ArrayList<JavaPlugin> plugins = new ArrayList<>();
    private static final Logger log = LogManager.getLogger("Gallium/PluginManager");
    public PluginManager() {

    }

    /**
     * Loads a plugin
     * @param plugin The plugin
     * @param meta A {@link Plugin} annotation
     */
    @SuppressWarnings("deprecation")
    public void loadPlugin(JavaPlugin plugin, Plugin meta) {
        Class<? extends JavaPlugin> clazz = plugin.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == PluginLifecycleState.ENABLED)
                .forEach(method -> {
                    try {
                        log.info("Invoking!");
                        method.invoke(clazz.newInstance());
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });
        plugins.add(plugin);
        log.info("Loaded plugin {}", meta.name());
    }

    /**
     * Unload a plugin
     * @param plugin The plugin
     * @param meta A {@link Plugin} annotation
     */
    @SuppressWarnings("deprecation")
    public void unloadPlugin(JavaPlugin plugin, Plugin meta) {
        Class<? extends JavaPlugin> clazz = plugin.getClass();
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == PluginLifecycleState.DISABLED)
                .forEach(method -> {
                    try {
                        log.info("Invoking!");
                        method.invoke(clazz.newInstance());
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });

        plugins.remove(plugin);
        log.info("Unloaded plugin {}", meta.name());
    }

    public Optional<JavaPlugin> getPluginByName(String name) {
        // TODO
        return Optional.empty();
    }

    /**
     * Get the plugins on the server
     * @return ArrayList of plugins
     */
    public ArrayList<JavaPlugin> getLoadedPlugins() {
        return plugins;
    }

    @SuppressWarnings("deprecation")
    public void loadPlugins() throws IOException {
        // Load internal plugin
        GalliumPlugin internalPlugin = new GalliumPlugin();
        loadPlugin(internalPlugin, internalPlugin.getClass().getAnnotation(Plugin.class));

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
            ZipFile zip = new ZipFile(file);
            if (zip.getEntry("Canary.inf") != null) {
                throw new BadPluginException("CanaryMod plugins are not natively supported. Please remove " + file.getName());
            } else if (zip.getEntry("plugin.yml") != null) {
                throw new BadPluginException("Bukkit plugins are not natively supported. Please remove " + file.getName());
            } else if (zip.getEntry("bungee.yml") != null) {
                throw new BadPluginException("BungeeCord plugins are not natively supported. Please remove " + file.getName());
            } else if (zip.getEntry("mcmod.info") != null) {
                throw new BadPluginException("Sponge plugins and Forge mods are not natively supported. Please remove " + file.getName());
            } else {
                // Actually load the plugin
                ZipEntry manifest =  zip.getEntry("META-INF/MANIFEST.MF");
                if (manifest == null) {
                    throw new BadPluginException("Could not find a manifest file in " + file.getName());
                } else {
                    InputStream manifestInput = zip.getInputStream(manifest);
                    InputStreamReader manifestReader = new InputStreamReader(manifestInput);
                    BufferedReader br = new BufferedReader(manifestReader);

                    Properties prop = new Properties();
                    prop.load(br);

                    String mainClass = prop.getProperty("Main-Class");
                    if (mainClass == null) {
                        throw new BadPluginException("Could not find a Main-Class attribute in the manifest file for " + file.getName());
                    } else {
                        try {
                            URLClassLoader child = new URLClassLoader(new URL[] { file.toURI().toURL() }, this.getClass().getClassLoader());
                            Class<?> clazz = Class.forName(mainClass, true, child);
                            Class<? extends JavaPlugin> javaPluginClass;
                            try {
                                javaPluginClass = clazz.asSubclass(JavaPlugin.class);
                            } catch (ClassCastException e) {
                                throw new BadPluginException(file.getName() + " main class does not inherit JavaPlugin! (Hint: extend JavaPlugin)");
                            }
                            Plugin meta = javaPluginClass.getAnnotation(Plugin.class);
                            loadPlugin(javaPluginClass.newInstance(), meta);
                        } catch (Exception e) {
                            throw new PluginLoadFailException(e);
                        }
                    }
                }
            }

        }
    }

    /**
     * Unload all plugins on the server
     */
    public void unloadPlugins() {
        for (JavaPlugin plugin : plugins) {
            Plugin meta = plugin.getClass().getAnnotation(Plugin.class);
            unloadPlugin(plugin, meta);
        }
    }
}
