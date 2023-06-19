package net.zenoc.gallium.galliumlib.plugin;

import net.zenoc.gallium.galliumlib.Gallium;
import net.zenoc.gallium.galliumlib.GalliumServer;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class PluginBuilder {
    Gallium gallium = GalliumServer.getInstance();
    String name;
    String description;
    ArrayList<String> authors;
    String version;

    public PluginBuilder() {}

    public PluginBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PluginBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public PluginBuilder addAuthor(String authorName) {
        authors.add(authorName);
        return this;
    }

    public PluginBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    public PluginBuilder addCommand(Object command) {
        gallium.getCommandHandler().registerCommand(command);
        return this;
    }

    public Plugin build() {
        return new Plugin(gallium, name, description, authors.toArray(new String[0]), version) {
            // FIXME: This is a bit silly
            @Override
            public void load() {
                gallium.getPluginManager().loadPlugin(this);
            }

            @Override
            public void unload() {
                gallium.getPluginManager().unloadPlugin(this);
            }
        };
    }
}
