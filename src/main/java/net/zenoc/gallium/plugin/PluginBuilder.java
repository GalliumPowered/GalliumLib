package net.zenoc.gallium.plugin;

import net.zenoc.gallium.Gallium;

import java.util.ArrayList;

public class PluginBuilder {
    String name;
    String id;
    String description;
    ArrayList<String> authors = new ArrayList<>();
    String version;

    public PluginBuilder() {}

    public PluginBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PluginBuilder setId(String id) {
        this.id = id;
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
        Gallium.getCommandManager().registerCommand(command, this.build());
        return this;
    }

    public PluginBuilder addListener(Object listener) {
        Gallium.getEventManager().registerEvent(listener);
        return this;
    }

    public Plugin build() {
        return new PluginImpl(name, id, description, authors.toArray(new String[0]), version);
    }
}
