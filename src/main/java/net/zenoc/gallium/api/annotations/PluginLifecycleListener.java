package net.zenoc.gallium.api.annotations;

import net.zenoc.gallium.plugin.PluginLifecycleState;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A plugin lifecycle listener
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginLifecycleListener {
    PluginLifecycleState value();
}
