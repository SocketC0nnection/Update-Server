package net.autoupdater.src.client;

import net.autoupdater.src.AutoUpdater;
import net.autoupdater.src.models.Pair;

import java.io.*;
import java.net.Socket;

/**
 * @author SocketConnection
 * @github https://github.com/socketc0nnection
 **/

public class Client implements Runnable {

    private final AutoUpdater instance = AutoUpdater.getInstance();

    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public Client(Socket client) throws IOException {
        socket = new Socket();
        inputStream = new DataInputStream(client.getInputStream());
        outputStream = new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            double version = inputStream.readDouble();

            Pair<File, Double> latestVersion = instance.getVersionManager().getLatestVersion();

            if(latestVersion == null || latestVersion.getValue() <= version) {
                outputStream.writeDouble(version);
                outputStream.flush();

                return;
            }

            outputStream.writeDouble(latestVersion.getValue());
            outputStream.flush();

            File file = latestVersion.getKey();
            FileInputStream fileIn = new FileInputStream(file);

            byte[] buffer = new byte[(int) file.length()];

            fileIn.read(buffer);

            outputStream.writeInt(buffer.length);
            outputStream.write(buffer);
            outputStream.flush();

            socket.close();
        } catch (Exception e) {
            instance.getLogger().error("Error while communicating with client: " + e.getMessage());
        }
    }

}
