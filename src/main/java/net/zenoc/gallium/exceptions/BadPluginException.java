package net.zenoc.gallium.exceptions;

public class BadPluginException extends RuntimeException {
    public BadPluginException(String msg) {
        super(msg);
    }

    public BadPluginException(Throwable t) {
        super(t);
    }
}
