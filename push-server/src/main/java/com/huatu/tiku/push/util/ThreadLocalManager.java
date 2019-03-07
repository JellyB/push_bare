package com.huatu.tiku.push.util;


import lombok.extern.slf4j.Slf4j;

/**
 * @author biguodong
 */
@Slf4j
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
