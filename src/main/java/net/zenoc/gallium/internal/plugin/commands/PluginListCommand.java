package net.zenoc.gallium.internal.plugin.commands;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.commandsys.CommandContext;
import net.zenoc.gallium.api.annotations.Plugin;

public class PluginListCommand {
    @Command(aliases = {"pluginlist", "plugins"}, description = "Show all plugins loaded on the server", neededPerms = "PLUGINS")
    public void pluginListCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--- Plugin list ---"));
//        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--------------------"));
        Gallium.getPluginManager().getLoadedPlugins().forEach(plugin -> {
            Plugin meta = plugin.getClass().getAnnotation(Plugin.class);
            ctx.getCaller().sendMessage(ChatMessage.from(Colors.WHITE + meta.name() + " (" + meta.id() + ")"));
        });
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--------------------"));
    }
}
