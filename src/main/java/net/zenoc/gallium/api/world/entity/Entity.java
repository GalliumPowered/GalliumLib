package net.zenoc.gallium.api.world.entity;

public abstract class Entity {
    String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
