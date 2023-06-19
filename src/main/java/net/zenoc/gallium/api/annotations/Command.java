package net.zenoc.gallium.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MinecraftCommand {
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
