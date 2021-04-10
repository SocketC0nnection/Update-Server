package net.autoupdater.src;

import net.autoupdater.src.managers.VersionManager;
import net.autoupdater.src.utils.Logger;
import net.autoupdater.src.server.Server;

import java.io.IOException;

/**
 * @author SocketConnection
 * @github https://github.com/socketc0nnection
 **/

public class AutoUpdater {

    private static AutoUpdater instance;

    private final VersionManager versionManager = new VersionManager();
    private final Logger logger = new Logger("Update-Server");

    public AutoUpdater() {
        instance = this;

        try {
            new Thread(new Server(50637)).start();
        } catch (IOException e) {
            getLogger().error("Error while server start: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AutoUpdater();
    }

    public VersionManager getVersionManager() {
        return versionManager;
    }

    public Logger getLogger() {
        return logger;
    }

    public static AutoUpdater getInstance() {
        return instance;
    }
}
