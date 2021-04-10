package net.autoupdater.src.utils;

/**
 * @author SocketConnection
 * @github https://github.com/socketc0nnection
 **/

public class Logger {

    private final String loggerName;

    public Logger(String loggerName) {
        this.loggerName = loggerName;
    }

    public void info(String info) {
        System.out.printf("[%s] %s\n", loggerName, info);
    }

    public void error(String error) {
        System.err.printf("[%s] %s\n", loggerName, error);
    }

}
