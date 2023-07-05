package org.galliumpowered.internal.plugin;

import com.google.inject.Inject;
import org.galliumpowered.Gallium;
import org.galliumpowered.annotation.PluginLifecycleListener;
import org.galliumpowered.command.PluginCommandManager;
import org.galliumpowered.internal.plugin.commands.GalliumCommand;
import org.galliumpowered.internal.plugin.commands.GamemodeCommand;
import org.galliumpowered.internal.plugin.commands.PingCommand;
import org.galliumpowered.internal.plugin.commands.plugin.PluginInfoCommand;
import org.galliumpowered.internal.plugin.commands.plugin.PluginListCommand;
import org.galliumpowered.internal.plugin.commands.permissions.GroupmodCommand;
import org.galliumpowered.internal.plugin.commands.permissions.PlayermodCommand;
import org.galliumpowered.internal.plugin.listeners.PlayerJoinListener;
import org.galliumpowered.annotation.Plugin;
import org.galliumpowered.plugin.PluginLifecycleState;
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
//        commandManager.registerCommand(new TestCommand());

        // Listener registration
        Gallium.getEventManager().registerEvent(new PlayerJoinListener());
    }
}
