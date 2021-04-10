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
    private final BufferedReader reader;
    private final OutputStream outputStream;

    public Client(Socket client) throws IOException {
        this.socket = new Socket();
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.outputStream = client.getOutputStream();
    }

    @Override
    public void run() {
        try {
            String msg = reader.readLine();

            if(msg == null || !msg.contains(" ")) {
                return;
            }

            String[] args = msg.split(" ");

            if(args.length != 2 || !args[0].equals("VERSION")) {
                return;
            }

            Pair<File, Double> version = instance.getVersionManager().getLatestVersion();
            PrintWriter writer = new PrintWriter(outputStream);

            if(version == null || version.getValue() <= Double.parseDouble(args[1])) {
                writer.println("VERSION LATEST");
                writer.flush();

                return;
            }

            writer.println("VERSION " + version.getValue());
            writer.flush();

            File file = version.getKey();

            FileInputStream fileIn = new FileInputStream(file);
            DataOutputStream dataOut = new DataOutputStream(outputStream);

            byte[] buffer = new byte[(int) file.length()];

            fileIn.read(buffer);

            dataOut.writeInt(buffer.length);
            dataOut.write(buffer);
            dataOut.flush();

            socket.close();
        } catch (Exception e) {
            instance.getLogger().error("Error while communicating with client: " + e.getMessage());
        }
    }

}
