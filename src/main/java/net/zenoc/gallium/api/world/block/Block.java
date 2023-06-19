package net.zenoc.gallium.api.world.block;

public abstract class Block {
    String id;
    String name;

    public Block(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
