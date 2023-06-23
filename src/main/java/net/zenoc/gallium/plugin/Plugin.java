package net.zenoc.gallium.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.exceptions.PluginLoadFailException;

import javax.annotation.Nonnull;

public abstract class Plugin {
    String name;
    String id;
    String description;
    String[] authors;
    String version;
    public Plugin(@Nonnull String name, @Nonnull String id, String description, String[] authors, String version) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.authors = authors;
        this.version = version;
    }

    /**
     * The name of the plugin
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * The ID of the plugin
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * The plugin's description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The plugin authors
     * @return the authors
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * The plugin's version
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Called by server to load the plugin
     */
    public abstract void load() throws PluginLoadFailException;

    /**
     * Called by server to unload the plugin
     */
    public abstract void unload();

}
