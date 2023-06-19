package net.zenoc.gallium.api.logging;

import org.apache.logging.log4j.Logger;

public class LogManager {
    /**
     * Gets a log4j logger
     * @return a log4j logger
     */
    public static Logger getLogger() {
        return org.apache.logging.log4j.LogManager.getLogger(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass());
    }
}
