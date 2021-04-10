package net.autoupdater.src.server;

import net.autoupdater.src.AutoUpdater;
import net.autoupdater.src.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author SocketConnection
 * @github https://github.com/socketc0nnection
 **/

public class Server implements Runnable {

    private final ServerSocket server;

    public Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (!server.isClosed()) {
            try {
                Socket client = server.accept();

                new Thread(new Client(client)).start();
            } catch (IOException e) {
                AutoUpdater.getInstance().getLogger().error("Error while client connects: " + e.getMessage());
            }
        }
    }
}
