package net.zenoc.gallium.internal.plugin;

import net.zenoc.gallium.api.annotations.EventListener;
import net.zenoc.gallium.api.annotations.RegisterPlugin;
import net.zenoc.gallium.api.event.system.ServerStartEvent;
import net.zenoc.gallium.internal.plugin.commands.GalliumCommand;
import net.zenoc.gallium.internal.plugin.commands.PingCommand;
import net.zenoc.gallium.internal.plugin.commands.PluginListCommand;
import net.zenoc.gallium.internal.plugin.commands.permissions.GroupmodCommand;
import net.zenoc.gallium.internal.plugin.commands.permissions.PlayermodCommand;
import net.zenoc.gallium.internal.plugin.listeners.PlayerJoinListener;
import net.zenoc.gallium.plugin.Plugin;
import net.zenoc.gallium.plugin.PluginBuilder;

public class GalliumPlugin {
    @RegisterPlugin
    public final Plugin plugin = new PluginBuilder()
            .setName("Gallium")
            .setId("gallium")
            .setDescription("Gallium internal plugin")
            .setVersion("1.0.0")
            .addAuthor("SlimeDiamond")
            .addCommand(new GalliumCommand())
            .addCommand(new PluginListCommand())
            .addCommand(new PingCommand())
            .addCommand(new PlayermodCommand())
            .addCommand(new GroupmodCommand())
            .addListener(new PlayerJoinListener())
            .addListener(this)
            .build();

    @EventListener
    public void serverStartEvent(ServerStartEvent event) {
        System.out.println("Server is ready for players!");
    }
}
