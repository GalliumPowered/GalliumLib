package net.zenoc.gallium.internal.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.PluginLifecycleListener;
import net.zenoc.gallium.internal.plugin.commands.GalliumCommand;
import net.zenoc.gallium.internal.plugin.commands.GamemodeCommand;
import net.zenoc.gallium.internal.plugin.commands.PingCommand;
import net.zenoc.gallium.internal.plugin.commands.PluginListCommand;
import net.zenoc.gallium.internal.plugin.commands.permissions.GroupmodCommand;
import net.zenoc.gallium.internal.plugin.commands.permissions.PlayermodCommand;
import net.zenoc.gallium.plugin.java.JavaPlugin;
import net.zenoc.gallium.api.annotations.Plugin;
import net.zenoc.gallium.plugin.PluginLifecycleState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Plugin(name = "Gallium", id = "gallium", description = "Gallium internal plugin", authors = { "SlimeDiamond" }, version = "1.0")
public class GalliumPlugin extends JavaPlugin {
    private static final Logger log = LogManager.getLogger();
    @PluginLifecycleListener(PluginLifecycleState.ENABLED)
    public void onPluginEnable() {
        log.info("Plugin enabled!");
        Gallium.getCommandManager().registerCommand(new GalliumCommand(), this);
        Gallium.getCommandManager().registerCommand(new PluginListCommand(), this);
        Gallium.getCommandManager().registerCommand(new PingCommand(), this);
        Gallium.getCommandManager().registerCommand(new PlayermodCommand(), this);
        Gallium.getCommandManager().registerCommand(new GroupmodCommand(), this);
        Gallium.getCommandManager().registerCommand(new GamemodeCommand(), this);
    }

    @PluginLifecycleListener(PluginLifecycleState.DISABLED)
    public void onPluginDisable() {
        log.info("Plugin being disabled");
    }
}
