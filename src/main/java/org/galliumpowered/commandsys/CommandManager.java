package org.galliumpowered.commandsys;

import org.galliumpowered.api.annotations.Command;
import org.galliumpowered.Gallium;
import org.galliumpowered.plugin.metadata.PluginMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    private HashMap<String, MCommand> commands = new HashMap<>();
    private ConcurrentHashMap<String, PluginMeta> pluginCommands = new ConcurrentHashMap<>();
    private HashMap<MCommand, MCommand> subcommands = new HashMap<>();

    public CommandManager() {

    }

    /**
     * Register a command on the server
     */
    public void registerCommand(Object command, PluginMeta meta) {
        Arrays.stream(command.getClass().getMethods())
            .filter(method -> method.isAnnotationPresent(Command.class))
            .map(method -> new MCommand(method.getAnnotation(Command.class), command, method))
            .forEach(cmd -> doRegister(cmd, meta));
    }

    private void doRegister(MCommand cmd, PluginMeta meta) {
        if (!cmd.getCommand().parent().equals("")) {
            // Subcommand
            subcommands.put(commands.get(cmd.getCommand().parent()), cmd);
        } else {
            for (String alias : cmd.getCommand().aliases()) {
                internalRegister(alias, cmd, meta);

                commands.put(meta.getId() + ":" + alias, cmd);
                commands.put(alias, cmd);

                pluginCommands.put(meta.getId() + ":" + alias, meta);
                pluginCommands.put(alias, meta);
            }
        }
    }

    private void internalRegister(String alias, MCommand cmd, PluginMeta meta) {
        if (cmd.getCommand().args().length == 0) {
            Gallium.getNMSBridge().registerCommand(alias, cmd.getCommand().neededPerms());
            Gallium.getNMSBridge().registerCommand(meta.getId() + ":" + alias, cmd.getCommand().neededPerms());
        } else {
            // FIXME: Multiple args
            Gallium.getNMSBridge().registerCommand(alias, cmd.getCommand().neededPerms(), cmd.getCommand().args());
            Gallium.getNMSBridge().registerCommand(meta.getId() + ":" + alias, cmd.getCommand().neededPerms(), cmd.getCommand().args());
        }
    }

    public void unregisterCommand(String alias) {
        commands.remove(alias);
        pluginCommands.remove(alias);
    }

    public void unregisterAllPluginCommands(PluginMeta meta) {
        for (String alias : pluginCommands.keySet()) {
            if (pluginCommands.get(alias) == meta) {
                unregisterCommand(alias);
            }
        }
    }

    public HashMap<String, MCommand> getCommands() {
        return commands;
    }

    public HashMap<MCommand, MCommand> getSubcommands() {
        return subcommands;
    }
}
