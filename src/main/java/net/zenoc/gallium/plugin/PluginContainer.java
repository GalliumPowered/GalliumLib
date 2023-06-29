package net.zenoc.gallium.plugin;

import com.google.inject.Injector;
import net.zenoc.gallium.api.annotations.PluginLifecycleListener;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.plugin.metadata.PluginMeta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class PluginContainer {

    private Object instance;
    private PluginMeta meta;
    private Injector injector;
    private PluginLifecycleState state = PluginLifecycleState.DISABLED;
    private Logger log = LogManager.getLogger("PluginContainer");

    public Object getInstance() {
        return instance;
    }

    public PluginMeta getMeta() {
        return meta;
    }

    public Injector getInjector() {
        return injector;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void setMeta(PluginMeta meta) {
        this.meta = meta;
    }

    public Class<?> getPluginClass() {
        return instance.getClass();
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public void setLifecycleState(PluginLifecycleState state) {
        log.info("Plugin {} is transitioning to lifecycle state {}", meta.getId(), state);
        this.state = state;
        Arrays.stream(getPluginClass().getMethods())
                .filter(method -> method.isAnnotationPresent(PluginLifecycleListener.class))
                .filter(method -> method.getAnnotation(PluginLifecycleListener.class).value() == state)
                .forEach(method -> {
                    try {
                        method.invoke(instance);
                    } catch (Exception e) {
                        throw new PluginLoadFailException(e);
                    }
                });
    }
}
