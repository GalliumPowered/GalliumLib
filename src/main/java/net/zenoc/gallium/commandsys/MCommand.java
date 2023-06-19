package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.api.annotations.MinecraftCommand;

import java.lang.reflect.Method;

public class CommandImpl {
    MinecraftCommand command;
    Object clazz;
    Method method;

    public CommandImpl(MinecraftCommand command, Object clazz, Method method) {
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
    public MinecraftCommand getCommand() {
        return command;
    }

    /**
     * Get method
     */
    public Method getMethod() {
        return method;
    }
}
