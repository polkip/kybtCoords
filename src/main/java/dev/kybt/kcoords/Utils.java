package dev.kybt.kcoords;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class Utils implements GlobalVars {

    public static final String[] DIRECTIONS = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    public static final int WHITE = rgba(255, 255, 255, 255);
    private static final File SAVE_FILE = new File(minecraft.mcDataDir.getParent(), "kybtCoords.config");
//    private static final File TEST_FILE = new File(minecraft.mcDataDir.getParent() + "\\mods\\kybtCoords", "kybtCoords.config");
    private static JsonObject configData = new JsonObject();

    public static int rgba(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b) + (a << 24);
    }

    public static int[] toRGBA(int buffer) {
        return new int[] {
                (buffer >> 16 & 255), (buffer >> 8 & 255), (buffer & 255), (buffer >> 24 & 255)
        };
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
        String c;
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
        big = big.setScale(places, RoundingMode.HALF_EVEN);
        return big.doubleValue();
    }

    public static void saveSettings() {
        configData = new JsonObject();
//        LOGGER.info("Save file path1: " + Paths.get(SAVE_FILE.getPath()));
        try {
            SAVE_FILE.createNewFile();
//            LOGGER.info("Save file path2: " + Paths.get(SAVE_FILE.getPath()));
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
            configData.addProperty("backgroundColor", KybtCoords.backgroundColor);

            bufferedWriter.write(configData.toString());
            bufferedWriter.close();
            writer.close();
        } catch(IOException e) {
            LOGGER.error("ERROR: Failed to write config data to save file.");
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        configData = new JsonObject();
        if(Files.exists(Paths.get(SAVE_FILE.getPath()))) {
            LOGGER.info("Found save file, retrieving config data.");
            try(BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
                StringBuilder builder = new StringBuilder();

                String line;
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
            KybtCoords.keyColor = configData.has("keyColor") ? configData.get("keyColor").getAsInt() :  rgba(92, 144, 228, 255);
            KybtCoords.textColor = configData.has("textColor") ? configData.get("textColor").getAsInt() : WHITE;
            KybtCoords.backgroundColor = configData.has("backgroundColor") ? configData.get("backgroundColor").getAsInt() : rgba(0, 0, 0, 127);
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
}
