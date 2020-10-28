package dev.kybt.kcoords.update;

import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import net.minecraft.util.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater {
    private final String downloadLink = "https://kybt.dev/kybtcoords/download/latest";
//    private final String versionCheckerLink = "https://kybt.dev/kybtcoords/version";

    private final String version;
    private String latest;

    public Updater(String ver) {
        this.version = ver;
    }

    private boolean checkVersion() throws IOException {
        URL currentVersion = new URL("https://kybt.dev/kybtcoords/version");
//        String latest;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(currentVersion.openStream()))) {
            latest = reader.readLine();
            return latest.equals(this.version);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void inquire() {
        try {
            if(checkVersion()) {
                Utils.getPlayer().addChatMessage(
                        new ChatComponentText("A new version of kybtCoords is available (v" + this.latest + ")")
                        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BLUE)));
//                Utils.getPlayer().addChatMessage(new ChatComponentText("A new version of ")
//                .appendSibling(new ChatComponentTranslation("§b§9", "kybtCoords")
//                .appendSibling(new ChatComponentTranslation("§r", " is available"))));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
