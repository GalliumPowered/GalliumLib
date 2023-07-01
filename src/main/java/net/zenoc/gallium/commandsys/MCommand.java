package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.Gallium;
import net.zenoc.gallium.api.annotations.Command;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MCommand {
    Command command;
    Object clazz;
    Method method;

    public MCommand(Command command, Object clazz, Method method) {
        this.command = command;
        this.clazz = clazz;
        this.method = method;
    }

    /**
     * Get the command caller
     */
    public Object getCaller() {
        return clazz;
    }

    /**
     * Get command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Get method
     */
    public Method getMethod() {
        return method;
    }

    public List<String> suggest(CommandContext ctx) {
        List<String> subcmdNames = new ArrayList<>();
        Gallium.getCommandManager().getSubcommands().forEach((parent, sub) -> {
            if (parent == this) {
                AtomicBoolean canExec = new AtomicBoolean(false);
                ctx.ifConsole(caller -> canExec.set(true));
                ctx.ifPlayer(player -> canExec.set(player.hasPermission(sub.getCommand().neededPerms())));
                if (canExec.get()) {
                    subcmdNames.addAll(Arrays.asList(sub.getCommand().aliases()));
                }
            }
        });
        return subcmdNames;
    }
}
