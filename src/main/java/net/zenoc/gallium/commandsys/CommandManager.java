package net.zenoc.gallium.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.zenoc.gallium.api.annotations.MinecraftCommand;
import net.zenoc.gallium.Gallium;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {
    Gallium gallium;
    private ArrayList<CommandImpl> commands = new ArrayList<>();

    public CommandManager(Gallium gallium) {
        this.gallium = gallium;
    }
    /**
     * Register a command on the server
     */
    public void registerCommand(Object command) {
        Arrays.stream(command.getClass().getMethods())
            .filter(method -> method.isAnnotationPresent(MinecraftCommand.class))
            .map(method -> new CommandImpl(method.getAnnotation(MinecraftCommand.class), command, method))
            .forEach(cmd -> {
                for (String alias : cmd.getCommand().aliases()) {
                    gallium.getNMS().getCommands().getDispatcher().register(LiteralArgumentBuilder.literal(alias));
                }
                commands.add(cmd);
            });
    }
}
