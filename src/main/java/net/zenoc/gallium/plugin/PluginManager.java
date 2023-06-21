package net.zenoc.gallium.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.exceptions.BadPluginException;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.internal.plugin.GalliumPlugin;
import net.zenoc.gallium.plugin.java.JavaPlugin;
import net.zenoc.gallium.plugin.java.JavaPluginLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginManager {
    private ArrayList<Plugin> plugins = new ArrayList<>();
    public JavaPluginLoader javaPluginLoader = new JavaPluginLoader();
    private static final Logger log = LogManager.getLogger("Gallium/PluginManager");
    public PluginManager() {

    }

    public Optional<Plugin> getPluginByName(String name) {
        // TODO
        return Optional.empty();
    }

    /**
     * Get the plugins on the server
     * @return ArrayList of plugins
     */
    public ArrayList<Plugin> getLoadedPlugins() {
        return plugins;
    }

    @SuppressWarnings("deprecation")
    public void loadPlugins() throws IOException {
        // Load internal plugin
        GalliumPlugin internalPlugin = new GalliumPlugin();
        PluginMeta internalPluginMeta = getPluginMetaFromAnnotation(internalPlugin.getClass());
        internalPlugin.setMeta(internalPluginMeta);
        addPlugin(internalPlugin);
        javaPluginLoader.loadPlugin(internalPlugin);


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
                PluginMeta meta = null;
                String mainClass;
                URLClassLoader child = new URLClassLoader(new URL[] { file.toURI().toURL() }, this.getClass().getClassLoader());

                // Load from JSON
                if (zip.getEntry("plugin.json") != null) {
                    InputStreamReader configReader = new InputStreamReader(child.getResourceAsStream("plugin.json"));
                    BufferedReader br = new BufferedReader(configReader);

                    JSONTokener tokener = new JSONTokener(br);
                    JSONObject json = new JSONObject(tokener);

                    String name = json.getString("name");
                    String id = json.getString("id");
                    String description = json.getString("description");
                    String version = json.getString("version");

                    mainClass = json.getString("mainClass");

                    JSONArray authorsJSON = json.getJSONArray("authors");
                    String[] authors = new String[authorsJSON.length()];
                    for (int i = 0; i < authorsJSON.length(); i++) {
                        authors[i] = (String) authorsJSON.get(i);
                    }

                    meta = new DefaultPluginMeta(name, id, description, authors, version, mainClass);
                } else {
                    ZipEntry manifest = zip.getEntry("META-INF/MANIFEST.MF");
                    if (manifest == null) {
                        throw new BadPluginException("Could not find META-INF/MANIFEST.MF manifest file in " + file.getName());
                    } else {
                        InputStream manifestInput = zip.getInputStream(manifest);
                        InputStreamReader manifestReader = new InputStreamReader(manifestInput);
                        BufferedReader br = new BufferedReader(manifestReader);

                        Properties prop = new Properties();
                        prop.load(br);

                        mainClass = prop.getProperty("Main-Class");
                    }
                }

                try {
                    Class<?> clazz = Class.forName(mainClass, true, child);
                    Class<? extends JavaPlugin> javaPluginClass;
                    try {
                        javaPluginClass = clazz.asSubclass(JavaPlugin.class);
                    } catch (ClassCastException e) {
                        throw new BadPluginException(file.getName() + " main class does not inherit JavaPlugin! (Hint: extend JavaPlugin)");
                    }
                    if (meta == null) {
                        meta = getPluginMetaFromAnnotation(javaPluginClass);
                    }

                    Plugin plugin = javaPluginClass.newInstance();
                    plugin.setMeta(meta);
                    addPlugin(plugin);
                    javaPluginLoader.loadPlugin(plugin);
                } catch (Exception e) {
                    throw new PluginLoadFailException(e);
                }
            }
        }
    }

    /**
     * Unload all plugins on the server
     */
    public void unloadPlugins() {
        plugins.forEach(plugin -> javaPluginLoader.unloadPlugin(plugin));
    }

    /**
     * FOR INTERNAL USE ONLY. DO NOT CALL THIS METHOD.
     * Adds a plugin to the HashMap and ArrayList
     * @param plugin The plugin
     */
    public void addPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    private PluginMeta getPluginMetaFromAnnotation(Class<? extends JavaPlugin> javaPluginClass) {
        net.zenoc.gallium.api.annotations.Plugin plugin = javaPluginClass.getAnnotation(net.zenoc.gallium.api.annotations.Plugin.class);
        return new DefaultPluginMeta(plugin.name(), plugin.id(), plugin.description(), plugin.authors(), plugin.version(), javaPluginClass.getName());
    }
}
