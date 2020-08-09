package dev.kybt.kcoords;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.media.jfxmedia.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils implements GlobalVars {

    public static final String[] DIRECTIONS = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    public static final int WHITE = rgba(255, 255, 255, 255);
    private static final File SAVE_FILE = new File(new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/mods/kybtCoords"), "kybtcoords.config");
    private static JsonObject configData = new JsonObject();

    public static int rgba(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b) + (a << 24);
    }

    // Credit to boomboompower
    public static int getDirection() {
        double direction = MathHelper.wrapAngleTo180_float(minecraft.thePlayer.rotationYaw) + 180.0D;

        direction += 22.5D;
        direction %= 360.0D;
        direction /= 45.0D;

        return MathHelper.floor_double(direction);
    }

    public static String fetchCCounter() {
        String c = "Invalid";
        String renderInfo = minecraft.renderGlobal.getDebugInfoRenders();
        c = renderInfo.split(" ")[1];
        return c;
    }

    public static int fetchBiomeColor(BlockPos position) {
        if(minecraft.theWorld != null && minecraft.theWorld.isBlockLoaded(position) && KybtCoords.coloredBiomes) {
            Chunk chunk = minecraft.theWorld.getChunkFromBlockCoords(position);
            return chunk.getBiome(position, minecraft.theWorld.getWorldChunkManager()).color;
        }

        return KybtCoords.textColor;
    }

    public static String fetchBiomeName(BlockPos position) {
        if(minecraft.theWorld != null && minecraft.theWorld.isBlockLoaded(position)) {
            Chunk chunk = minecraft.theWorld.getChunkFromBlockCoords(position);
            return chunk.getBiome(position, minecraft.theWorld.getWorldChunkManager()).biomeName;
        }

        return "Invalid";
    }

    public static double roundDouble(double value, int places) {
        BigDecimal big = new BigDecimal(Double.toString(value));
        big = big.setScale(places, RoundingMode.HALF_UP);
        return big.doubleValue();
    }

//    public static int getHeight() {
//        int height = KybtCoords.positionX + 100;
//
//        if(!KybtCoords.showBiomes) height -= 10;
//        if(!KybtCoords.showC) height -= 10;
//        if(!KybtCoords.showFPS) height -= 10;
//
//        return height;
//    }

    public static void saveSettings() {
        configData = new JsonObject();
        LOGGER.info("Save file path1: " + Paths.get(SAVE_FILE.getPath()));
        try {
            SAVE_FILE.createNewFile();
            LOGGER.info("Save file path2: " + Paths.get(SAVE_FILE.getPath()));
            FileWriter writer = new FileWriter(SAVE_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            configData.addProperty("isEnabled", KybtCoords.isEnabled);
            configData.addProperty("positionX", KybtCoords.positionX);
            configData.addProperty("positionY", KybtCoords.positionY);
            configData.addProperty("coloredBiomes", KybtCoords.coloredBiomes);
            configData.addProperty("showBiomes", KybtCoords.showBiomes);
            configData.addProperty("showC", KybtCoords.showC);
            configData.addProperty("showFPS", KybtCoords.showFPS);
            configData.addProperty("keyColor", KybtCoords.keyColor);
            configData.addProperty("textColor", KybtCoords.textColor);
            configData.addProperty("backgroundOpacity", KybtCoords.backgroundOpacity);

            bufferedWriter.write(configData.toString());
            bufferedWriter.close();
            writer.close();
        } catch(IOException e) {
            LOGGER.error("ERROR: Failed to write config data to save file.");
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        if(Files.exists(Paths.get(SAVE_FILE.getPath()))) {
            LOGGER.info("Found save file, retrieving config data.");
            try(BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
                StringBuilder builder = new StringBuilder();

                java.lang.String line;
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                String data = builder.toString();
                configData = new JsonParser().parse(data).getAsJsonObject();
            } catch(Exception e) {
                LOGGER.error("ERROR: Failed to fetch config data from save file. Attempting to save");
                saveSettings();
                e.printStackTrace();
            }
            KybtCoords.positionX = configData.has("positionX") ? configData.get("positionX").getAsInt() : 0;
            KybtCoords.positionY = configData.has("positionY") ? configData.get("positionY").getAsInt() : 0;
            KybtCoords.keyColor = configData.has("keyColor") ? configData.get("keyColor").getAsInt() : rgba(54, 177, 223, 255);
            KybtCoords.textColor = configData.has("textColor") ? configData.get("textColor").getAsInt() : WHITE;
            KybtCoords.backgroundOpacity = configData.has("backgroundOpacity") ? configData.get("backgroundOpacity").getAsInt() : rgba(0, 0, 0, 127);
            KybtCoords.isEnabled = configData.has("isEnabled") && configData.get("isEnabled").getAsBoolean();
            KybtCoords.showFPS = configData.has("showFPS") && configData.get("showFPS").getAsBoolean();
            KybtCoords.showC = configData.has("showC") && configData.get("showC").getAsBoolean();
            KybtCoords.showBiomes = configData.has("showBiomes") && configData.get("showBiomes").getAsBoolean();
            KybtCoords.coloredBiomes = configData.has("coloredBiomes") && configData.get("coloredBiomes").getAsBoolean();

        } else {
            LOGGER.info("Couldn't find a save file, attempting to save.");
            saveSettings();
        }
    }

//    public static class Colors {
//        public static final int WHITE = Utils.rgba(255, 255, 255, 255);
//        public static final int BLACK = Utils.rgba(0, 0, 0, 255);
//        public static final int RED = Utils.rgba(255, 0, 0, 255);
//        public static final int GREEN = Utils.rgba(0, 255, 0, 255);
//        public static final int DARK_GREEN = Utils.rgba(0, 128, 0, 255);
//        public static final int BLUE = Utils.rgba(0, 0, 255, 255);
//        public static final int LIGHT_BLUE = Utils.rgba(189, 204, 199, 255);
//        public static final int PURPLE = Utils.rgba(163, 73, 163, 255);
//        public static final int YELLOW = Utils.rgba(255, 255, 0 ,255);
//        public static final int DARK_GRAY = Utils.rgba(128, 128, 128, 255);
//        public static final int LIGHT_GRAY = Utils.rgba(192, 192, 192, 255);
//        public static final int ORANGE = Utils.rgba(255, 165, 0, 255);
//        public static final int DARK_RED = Utils.rgba(64, 0, 0, 255);
//        public static final int CERULEAN = Utils.rgba(16, 32, 75, 255);
//        public static final int CREAM = Utils.rgba(255, 253, 208, 255);
//        public static final int PERU = Utils.rgba(205, 133, 63, 255);
//        public static final int CYAN = Utils.rgba(0, 255, 255, 255);
//    }
}
