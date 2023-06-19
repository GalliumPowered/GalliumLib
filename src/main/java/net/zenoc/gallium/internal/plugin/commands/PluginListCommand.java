package net.zenoc.gallium.internal.plugin;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.chat.ChatMessage;
import net.zenoc.gallium.api.chat.Colors;
import net.zenoc.gallium.api.annotations.MinecraftCommand;
import net.zenoc.gallium.commandsys.CommandContext;

public class PluginListCommand {
    @MinecraftCommand(aliases = {"pluginlist", "plugins"}, description = "Show all plugins loaded on the server", neededPerms = "PLUGINS")
    public void pluginListCommand(CommandContext ctx) {
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "Plugin list"));
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--------------------"));
        Gallium.getPluginManager().getLoadedPlugins().forEach(plugin -> {
            ctx.getCaller().sendMessage(ChatMessage.from(plugin.getName()));
        });
        ctx.getCaller().sendMessage(ChatMessage.from(Colors.GREEN + "--------------------"));
    }
}
