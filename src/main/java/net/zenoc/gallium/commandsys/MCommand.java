package net.zenoc.gallium.commandsys;

import net.zenoc.gallium.api.annotations.Command;

import java.lang.reflect.Method;

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
}
