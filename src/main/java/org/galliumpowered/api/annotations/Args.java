package org.galliumpowered.api.annotations;

import org.galliumpowered.commandsys.args.ArgumentType;

public @interface Args {
    ArgumentType type();
    String name();
}
