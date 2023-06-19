package net.zenoc.gallium.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.RegisterPlugin;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.internal.plugin.GalliumPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginManager {
    ArrayList<Plugin> plugins = new ArrayList<>();
    private static final Logger log = LogManager.getLogger("Gallium/PluginManager");
    public PluginManager() {

    }

    public void loadPlugin(Plugin plugin) throws PluginLoadFailException {
        plugins.add(plugin);
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

    public void loadPlugins() throws IOException {
        // Load internal plugin
        GalliumPlugin internalPlugin = new GalliumPlugin();
        loadPlugin(internalPlugin.plugin);

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
                log.warn("CanaryMod plugins are not natively supported. Please remove {}", file.getName());
            } else if (zip.getEntry("plugin.yml") != null) {
                log.warn("Bukkit plugins are not natively supported. Please remove {}", file.getName());
            } else if (zip.getEntry("bungee.yml") != null) {
                log.warn("BungeeCord plugins are not natively supported. Please remove {}", file.getName());
            } else if (zip.getEntry("mcmod.info") != null) {
                log.warn("Sponge plugins and Forge mods aren ot natively supported. Please remove {}", file.getName());
            } else {
                // Actually load the plugin
                ZipEntry manifest =  zip.getEntry("META-INF/MANIFEST.MF");
                if (manifest == null) {
                    log.warn("I can not find a manifest file in {}", file.getName());
                } else {
                    InputStream manifestInput = zip.getInputStream(manifest);
                    InputStreamReader manifestReader = new InputStreamReader(manifestInput);
                    BufferedReader br = new BufferedReader(manifestReader);

                    Properties prop = new Properties();
                    prop.load(br);

                    String mainClass = prop.getProperty("Main-Class");
                    if (mainClass == null) {
                        log.warn("I can not find a Main-Class attribute in the manifest file for {}", file.getName());
                    } else {
                        try {
                            URLClassLoader child = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
                            Class<?> clazz = Class.forName(mainClass, true, child);
                            Arrays.stream(clazz.getFields())
                                    .filter(field -> field.isAnnotationPresent(RegisterPlugin.class))
                                    .filter(field -> field.getType() == Plugin.class)
                                    .forEach(field -> {
                                        try {
                                            // I have no idea why this works
                                            Plugin plugin = (Plugin) field.get(new PluginBuilder().build());
                                            loadPlugin(plugin);
                                        } catch (IllegalAccessException e) {
                                            throw new PluginLoadFailException(e);
                                        }
                                    });
                        } catch (Exception e) {
                            throw new PluginLoadFailException(e);
                        }
                    }
                }
            }

        }
    }
}
