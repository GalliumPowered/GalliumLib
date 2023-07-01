package net.zenoc.gallium.internal.plugin.commands.plugin;

import net.kyori.adventure.text.Component;
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
            ctx.getCaller().sendMessage(Component.text(Colors.WHITE + meta.getName() + " (" + meta.getId() + ")"));
        });
        ctx.getCaller().sendMessage(Component.text(Colors.GREEN + "--------------------"));
    }
}
