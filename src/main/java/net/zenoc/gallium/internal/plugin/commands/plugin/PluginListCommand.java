package net.zenoc.gallium.internal.plugin.commands.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.commandsys.CommandContext;
import net.zenoc.gallium.plugin.metadata.PluginMeta;

public class PluginListCommand {
    @Command(aliases = {"pluginlist", "plugins"}, description = "Show all plugins loaded on the server", neededPerms = "PLUGINS")
    public void pluginListCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "--- Plugin list ---"));
//        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "--------------------"));
        Gallium.getPluginManager().getLoadedPlugins().forEach(plugin -> {
            PluginMeta meta = plugin.getMeta();
            ctx.getCaller().sendMessage(
                    Component.text(Colors.WHITE + meta.getName() + " (" + meta.getId() + ")")
                            .hoverEvent(HoverEvent.showText(
                                    Component.text(
                                            "Description: " + meta.getDescription() + "\n\n" +
                                            "Click for more info"
                                    )
                            ))
                            .clickEvent(ClickEvent.runCommand("/plugininfo " + meta.getId()))
            );
        });
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "--------------------"));
    }
}
