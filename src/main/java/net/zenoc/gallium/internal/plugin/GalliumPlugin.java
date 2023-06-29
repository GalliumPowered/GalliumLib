package net.zenoc.gallium.internal.plugin;

import com.google.inject.Inject;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.PluginLifecycleListener;
import net.zenoc.gallium.commandsys.PluginCommandManager;
import net.zenoc.gallium.internal.plugin.commands.GalliumCommand;
import net.zenoc.gallium.internal.plugin.commands.GamemodeCommand;
import net.zenoc.gallium.internal.plugin.commands.PingCommand;
import net.zenoc.gallium.internal.plugin.commands.plugin.PluginInfoCommand;
import net.zenoc.gallium.internal.plugin.commands.plugin.PluginListCommand;
import net.zenoc.gallium.internal.plugin.commands.permissions.GroupmodCommand;
import net.zenoc.gallium.internal.plugin.commands.permissions.PlayermodCommand;
import net.zenoc.gallium.internal.plugin.listeners.PlayerJoinListener;
import net.zenoc.gallium.api.annotations.Plugin;
import net.zenoc.gallium.plugin.PluginLifecycleState;
import org.apache.logging.log4j.Logger;

@Plugin(name = "Gallium",
        id = "gallium",
        description = "Gallium internal plugin",
        authors = { "GalliumPowered" },
        version = "1.0")
public class GalliumPlugin {
    @Inject
    private Logger log;

    @Inject
    private PluginCommandManager commandManager;

    @PluginLifecycleListener(PluginLifecycleState.ENABLED)
    public void onPluginEnable() {
        // Command registration
        commandManager.registerCommand(new GalliumCommand());
        commandManager.registerCommand(new PluginListCommand());
        commandManager.registerCommand(new PingCommand());
        commandManager.registerCommand(new PlayermodCommand());
        commandManager.registerCommand(new GroupmodCommand());
        commandManager.registerCommand(new GamemodeCommand());
        commandManager.registerCommand(new PluginInfoCommand());

        // Listener registration
        Gallium.getEventManager().registerEvent(new PlayerJoinListener());
    }
}
