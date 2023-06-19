package net.zenoc.gallium.exceptions;

public class PluginLoadFailException extends RuntimeException {
    public PluginLoadFailException() {
        super();
    }
    public PluginLoadFailException(Throwable t) {
        super(t);
    }
}
