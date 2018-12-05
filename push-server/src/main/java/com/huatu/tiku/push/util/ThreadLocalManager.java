package com.huatu.tiku.push.util;


/**
 * @author biguodong
 */
public class ThreadLocalManager {
    private static final ThreadLocal<ConsoleContext> manager = new ThreadLocal<ConsoleContext>();
    public static void setConsoleContext(ConsoleContext consoleContext) {
        ConsoleContext context = getConsoleContext();
        if (context != null) {
            manager.remove();
        }
        manager.set(consoleContext);
    }
    public static ConsoleContext getConsoleContext() {
        return manager.get();
    }

    public static void clear() {
        manager.remove();
    }
}
