package net.autoupdater.src.managers;

import net.autoupdater.src.AutoUpdater;
import net.autoupdater.src.models.Pair;

import java.io.File;

/**
 * @author SocketConnection
 * @github https://github.com/socketc0nnection
 **/

public class VersionManager {

    public Pair<File, Double> getLatestVersion() {
        File directory = new File("versions");

        if(!directory.exists()) {
            directory.mkdir();
        }

        File[] files = directory.listFiles();

        if(files == null) {
            return null;
        }

        Pair<File, Double> version = null;

        for(File file : files) {
            String[] dotBlocks = file.getName().split("\\.");
            int dotBlocksLength = dotBlocks.length;

            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < dotBlocksLength; i++) {
                builder.append(dotBlocks[i]);

                if(i >= dotBlocksLength - 2) {
                    break;
                }

                builder.append(".");
            }

            try {
                double versionDouble = Double.parseDouble(builder.toString());

                if(version != null && versionDouble <= version.getValue()) {
                    continue;
                }

                version = new Pair<>(file, versionDouble);
            } catch (IllegalArgumentException e) {
                AutoUpdater.getInstance().getLogger().error("Error while scanning for latest version: " + e.getMessage());
            }
        }

        return version;
    }

}
