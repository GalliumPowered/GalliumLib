package net.zenoc.gallium.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A command
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    /**
     * Command aliases
     */
    String[] aliases();

    /**
     * Command description
     */
    String description() default "(no command description)";

    /**
     * Required permissions
     * TODO-ish
     */
    String neededPerms() default "NONE";
}
