package net.zenoc.gallium.galliumlib.internal.plugin;

import net.zenoc.gallium.galliumlib.ChatMessage;
import net.zenoc.gallium.galliumlib.api.annotations.MinecraftCommand;
import net.zenoc.gallium.galliumlib.commands.CommandContext;
import net.zenoc.gallium.galliumlib.plugin.Plugin;
import net.zenoc.gallium.galliumlib.plugin.PluginBuilder;

public class GalliumPlugin {
    public final Plugin plugin = new PluginBuilder()
            .setName("Gallium")
            .setDescription("Gallium internal plugin")
            .setVersion("1.0.0")
            .addAuthor("SlimeDiamond")
            .addCommand(new GalliumCommand())
            .build();
}
