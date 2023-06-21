package net.zenoc.gallium.commandsys;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.zenoc.gallium.api.annotations.Command;
import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.world.entity.Player;
import net.zenoc.gallium.exceptions.CommandException;
import net.zenoc.gallium.plugin.Plugin;
import net.zenoc.gallium.plugin.PluginMeta;
import net.zenoc.gallium.plugin.java.JavaPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class CommandManager {
    private static final Logger log = LogManager.getLogger();
    private HashMap<String, MCommand> commands = new HashMap<>();

    public CommandManager() {

    }

    /**
     * Register a command on the server
     */
    public void registerCommand(Object command, Plugin plugin) {
        PluginMeta meta = plugin.getMeta();

        Arrays.stream(command.getClass().getMethods())
            .filter(method -> method.isAnnotationPresent(Command.class))
            .map(method -> new MCommand(method.getAnnotation(Command.class), command, method))
            .forEach(cmd -> doRegister(cmd, meta));
    }

    private void doRegister(MCommand cmd, PluginMeta meta) {
        for (String alias : cmd.getCommand().aliases()) {
            internalRegister(alias, cmd.getCommand().neededPerms());
            internalRegister(meta.getId() + ":" + alias, cmd.getCommand().neededPerms());

            commands.put(meta.getId() + ":" + alias, cmd);
            commands.put(alias, cmd);
        }
    }

    private void internalRegister(String alias, String permission) {
        Gallium.getNMS().getCommands().getDispatcher().register(LiteralArgumentBuilder.<CommandSourceStack>literal(alias)
                .requires(commandSourceStack -> {
                    if (commandSourceStack.getDisplayName().getContents().equals("Server")) {
                        return true;
                    } else {
                        ServerPlayer serverPlayer;
                        try {
                            serverPlayer = commandSourceStack.getPlayerOrException();
                        } catch (CommandSyntaxException e) {
                            throw new RuntimeException(e);
                        }
                        return Gallium.getPermissionManager().playerHasPermission(
                                new Player(serverPlayer),
                                permission
                        );
                    }
                })
                .executes(this::executeCommand)
                // TODO: Command suggestion for args
                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("arguments", StringArgumentType.greedyString())
                        .executes(this::executeCommand)
                )
        );
    }

    private int executeCommand(CommandContext<CommandSourceStack> ctx) {
        // FIXME: This might not properly account for permissions. It should though
        String[] args = ctx.getInput().split(" ");
        if (args.length == 0) {
            return 0;
        }

        String alias = args[0].toLowerCase();
        alias = alias.startsWith("/") ? alias.substring(1) : alias;

        MCommand command = commands.get(alias);
        if (command == null) {
            return 0;
        }

        Method method = command.getMethod();

        log.info("Invoking!");
        try {
            method.invoke(command.getCaller(), new net.zenoc.gallium.commandsys.CommandContext() {
                @Override
                public CommandCaller getCaller() {
                    CommandCallerType type = CommandCallerType.PLAYER;
                    if (ctx.getSource().getTextName().equals("Server")) {
                        type = CommandCallerType.CONSOLE;
                    }
                    return new CommandCaller(ctx.getSource().getTextName(), type);
                }

                @Override
                public String[] getCommandArgs() {
                    return args;
                }

                @Override
                public net.zenoc.gallium.commandsys.CommandContext ifPlayer(Consumer<Player> consumer) {
                    getCaller().getPlayer().ifPresent(consumer);
                    return this;
                }

                @Override
                public net.zenoc.gallium.commandsys.CommandContext ifConsole(Consumer<CommandCaller> consumer) {
                    if (getCaller().getPlayer().isEmpty()) {
                        consumer.accept(getCaller());
                    }
                    return this;
                }
            });
        } catch (Exception e) {
            throw new CommandException(e);
        }
        return 1;
    }
}
