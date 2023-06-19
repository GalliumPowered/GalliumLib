package net.zenoc.gallium.galliumlib.plugin;

import net.zenoc.gallium.galliumlib.Gallium;
import net.zenoc.gallium.galliumlib.exceptions.PluginLoadFailException;

public abstract class Plugin {
    Gallium gallium;
    String name;
    String description;
    String[] authors;
    String version;
    public Plugin(Gallium gallium, String name, String description, String[] authors, String version) {
        this.gallium = gallium;
        this.name = name;
        this.description = description;
        this.authors = authors;
        this.version = version;
    }

    public Gallium getGallium() {
        return gallium;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAuthors() {
        return authors;
    }

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
