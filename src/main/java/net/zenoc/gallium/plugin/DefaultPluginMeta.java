package net.zenoc.gallium.plugin;

public class DefaultPluginMeta implements PluginMeta {

    String name;
    String id;
    String description;
    String[] authors;
    String version;
    String mainClass;

    public DefaultPluginMeta(String name, String id, String description, String[] authors, String version, String mainClass) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.authors = authors;
        this.version = version;
        this.mainClass = mainClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String[] getAuthors() {
        return authors;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getMainClass() {
        return mainClass;
    }

}
