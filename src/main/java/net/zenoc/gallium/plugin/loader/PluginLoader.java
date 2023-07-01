package net.zenoc.gallium.plugin.loader;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.exceptions.BadPluginException;
import net.zenoc.gallium.exceptions.PluginLoadFailException;
import net.zenoc.gallium.plugin.PluginContainer;
import net.zenoc.gallium.plugin.PluginLifecycleState;
import net.zenoc.gallium.plugin.inject.modules.InjectPluginModule;
import net.zenoc.gallium.plugin.metadata.PluginMeta;
import net.zenoc.gallium.plugin.metadata.PluginMetaLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Optional;

/**
 * Loads Java plugins
 */
public class PluginLoader {
    private Logger log = LogManager.getLogger("Gallium/PluginLoader");

    /**
     * Loads a {@link File} as a {@link PluginContainer}
     * @param jar The jar {@link File}
     * @return {@link Optional} of a {@link PluginContainer} instance for the jar
     */
    public Optional<PluginContainer> loadPlugin(@Nonnull File jar) {
        try {
            PluginClassLoader pluginClassLoader = new PluginClassLoader(jar.toPath());
            Optional<PluginMeta> metaOptional = PluginMetaLoader.getPluginMetadata(jar);
            if (metaOptional.isPresent()) {
                PluginMeta meta = metaOptional.get();

                if (meta.getId().equals("gallium") || meta.getId().equals("minecraft")) {
                    throw new BadPluginException("Plugin IDs 'gallium' and 'minecraft' are reserved!");
                }

                if (meta.getId().contains(" ")) {
                    throw new BadPluginException("Plugin IDs should not contain spaces! Found: " + meta.getId());
                }

                for (PluginContainer container : Gallium.getPluginManager().getLoadedPlugins()) {
                    if (container.getMeta().getId().equals(meta.getId())) {
                        throw new PluginLoadFailException("Plugin ID " + meta.getId() + " is already in use!");
                    }
                }

                PluginContainer container = new PluginContainer();

                container.setMeta(meta);

                Injector injector = Guice.createInjector(new InjectPluginModule(container));
                container.setInjector(injector);

                Class<?> clazz = pluginClassLoader.loadClass(meta.getMainClass());
                container.setInstance(injector.getInstance(clazz));

                return Optional.of(container);
            } else {
                log.error("{} does not seem to have metadata!");
            }
        } catch (Exception e) {
            log.error("Could not load jar {}", jar.getName(), e);
            return Optional.empty();
        }
        return Optional.empty();
    }

    /**
     * Unloads a {@link PluginContainer}
     * @param container The {@link PluginContainer}
     */
    public void unloadContainer(@Nonnull PluginContainer container) {
        container.setLifecycleState(PluginLifecycleState.DISABLED);
        Gallium.getPluginManager().removePlugin(container);
    }
}
