package net.zenoc.gallium.api.annotations;

import net.zenoc.gallium.commandsys.args.ArgumentType;

public @interface Args {
    ArgumentType type();
    String name();
}
