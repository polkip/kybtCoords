package dev.kybt.kcoords;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.kybt.kcoords.events.Subscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static final int WHITE = rgba(255, 255, 255, 255);
    public static final int BLUE = Utils.rgba(92, 144, 228, 255);
    public static boolean invalid = false;
    private static final File SAVE_FILE = new File(getMinecraft().mcDataDir.getParent(), "kybtCoords.config");
    private static JsonObject configData = new JsonObject();

    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static Logger getLogger() {
        return LogManager.getLogger("kybtCoords");
    }

    public static EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static WorldClient getWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    public static int rgba(int r, int g, int b, int a) {
        return (r << 16) | (g << 8) | (b) | (a << 24);
    }

    public static int rgbaByte(byte[] rgba) {
        return (rgba[0] << 16) | (rgba[1] << 8) | (rgba[2]) | (rgba[3] << 24);
    }

    public static int[] toRGBA(int color) {
        return new int[] {
                (color >> 16 & 0xFF), (color >> 8 & 0xFF), (color & 0xFF), (color >> 24 & 0xFF)
        };
    }

    public static byte[] toRGBAByte(int buffer) {
        return new byte[] {
                ((byte) (buffer >> 16 & 0xFF)),
                ((byte) (buffer >> 8 & 0xFF)),
                ((byte) (buffer & 0xFF)),
                ((byte) (buffer >> 24 & 0xFF))
        };
    }

    public static void updateDirection() {
        double direction = MathHelper.wrapAngleTo180_float(getPlayer().rotationYaw) + 180.0D;

        direction += 22.5D;
        direction %= 360.0D;
        direction /= 45.0D;

        int result = MathHelper.floor_double(direction);
        Directions[] directions = Directions.values();

        Subscriber.direction = directions[result].direction;
        Subscriber.xDirection = directions[result].x;
        Subscriber.zDirection = directions[result].z;

//        return Directions.values()[result].direction; // MathHelper.floor_double(direction);
    }

    public static String fetchCCounter() {
        String c;
        String renderInfo = getMinecraft().renderGlobal.getDebugInfoRenders();
        c = renderInfo.split(" ")[1];
        return c;
    }

    public static int fetchBiomeColor(BlockPos position) {
        if(getWorld() != null && getWorld().isBlockLoaded(position) && KybtCoords.coloredBiomes) {
            Chunk chunk = getWorld().getChunkFromBlockCoords(position);
            return chunk.getBiome(position, getWorld().getWorldChunkManager()).color;
        }

        return KybtCoords.textColor;
    }

    public static String fetchBiomeName(BlockPos position) {
        if(getWorld() != null && getWorld().isBlockLoaded(position)) {
            Chunk chunk = getWorld().getChunkFromBlockCoords(position);
            return chunk.getBiome(position, getWorld().getWorldChunkManager()).biomeName;
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
        try {
            SAVE_FILE.createNewFile();
            FileWriter writer = new FileWriter(SAVE_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            configData.addProperty("isEnabled", KybtCoords.isEnabled);
            configData.addProperty("positionX", KybtCoords.positionX);
            configData.addProperty("positionY", KybtCoords.positionY);
            configData.addProperty("coloredBiomes", KybtCoords.coloredBiomes);
            configData.addProperty("showBiomes", KybtCoords.showBiomes);
            configData.addProperty("showC", KybtCoords.showC);
            configData.addProperty("showFPS", KybtCoords.showFPS);
            configData.addProperty("scale", KybtCoords.scale);
            configData.addProperty("labelColor", KybtCoords.labelColor);
            configData.addProperty("textColor", KybtCoords.textColor);
            configData.addProperty("backgroundColor", KybtCoords.backgroundColor);
//            configData.addProperty("xLabel", KybtCoords.xLabel);
//            configData.addProperty("xValue", KybtCoords.xValue);
//            configData.addProperty("yLabel", KybtCoords.yLabel);
//            configData.addProperty("yValue", KybtCoords.yValue);
//            configData.addProperty("zLabel", KybtCoords.zLabel);
//            configData.addProperty("zValue", KybtCoords.zValue);

            bufferedWriter.write(configData.toString());
            bufferedWriter.close();
            writer.close();
        } catch(IOException e) {
            getLogger().error("ERROR: Failed to write config data to save file.");
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        configData = new JsonObject();
        if(Files.exists(Paths.get(SAVE_FILE.getPath()))) {
            getLogger().info("Found save file, retrieving config data.");
            try(BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
                StringBuilder builder = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                String data = builder.toString();
                configData = new JsonParser().parse(data).getAsJsonObject();
            } catch(Exception e) {
                getLogger().error("ERROR: Failed to fetch config data from save file. Attempting to save");
                saveSettings();
                e.printStackTrace();
            }
            KybtCoords.positionX = configData.has("positionX") ? configData.get("positionX").getAsInt() : 0;
            KybtCoords.positionY = configData.has("positionY") ? configData.get("positionY").getAsInt() : 0;
            KybtCoords.labelColor = configData.has("labelColor") ? configData.get("labelColor").getAsInt() :  BLUE;
            KybtCoords.textColor = configData.has("textColor") ? configData.get("textColor").getAsInt() : WHITE;
            KybtCoords.backgroundColor = configData.has("backgroundColor") ? configData.get("backgroundColor").getAsInt() : rgba(0, 0, 0, 127);
            KybtCoords.scale = configData.has("scale") ? configData.get("scale").getAsDouble() : 1.0D;
            KybtCoords.isEnabled = configData.has("isEnabled") && configData.get("isEnabled").getAsBoolean();
            KybtCoords.showFPS = configData.has("showFPS") && configData.get("showFPS").getAsBoolean();
            KybtCoords.showC = configData.has("showC") && configData.get("showC").getAsBoolean();
            KybtCoords.showBiomes = configData.has("showBiomes") && configData.get("showBiomes").getAsBoolean();
            KybtCoords.coloredBiomes = configData.has("coloredBiomes") && configData.get("coloredBiomes").getAsBoolean();
//            KybtCoords.xLabel = configData.has("xLabel") ? configData.get("xLabel").getAsInt() : BLUE;
//            KybtCoords.xValue = configData.has("xValue") ? configData.get("xValue").getAsInt() : WHITE;
//            KybtCoords.yLabel = configData.has("yLabel") ? configData.get("yLabel").getAsInt() : BLUE;
//            KybtCoords.xValue = configData.has("yValue") ? configData.get("yValue").getAsInt() : WHITE;
//            KybtCoords.zLabel = configData.has("zLabel") ? configData.get("zLabel").getAsInt() : BLUE;
//            KybtCoords.zValue = configData.has("zValue") ? configData.get("zValue").getAsInt() : WHITE;

        } else {
            getLogger().info("Couldn't find a save file, attempting to save.");
            saveSettings();
        }
    }

    public static byte[] parseHexadecimal(String hex) {
        List<String> hexTemp = new ArrayList<>();
        if(hex.contains("#")) {
            for(String i : hex.split("")) {
                if(!i.equals("#")) {
                    hexTemp.add(i);
                }
            }
        }
        getPlayer().sendChatMessage("Removed #: " + hexTemp.toString());
        if(hexTemp.size() % 2 != 0) {
            invalid = true;
            return null;
        }
        invalid = false;

        String temp = "";
        for(String s : hexTemp)
            temp += s;

        char[] hexArr = temp.toCharArray();
        Utils.getPlayer().sendChatMessage("hexArr: " + Arrays.toString(hexArr));
        byte[] result = null;

        try {
            result = Hex.decodeHex(hexArr);
        } catch(DecoderException e) {
            getPlayer().sendChatMessage("You threw a DecoderException!");
            e.printStackTrace();
        }

        getPlayer().sendChatMessage("Result: " + Arrays.toString(result));

        assert result != null : "Hexadecimal decoding error";

        byte[] r = new byte[result.length];
        for(int i = 0; i <= (result.length - 1); i++) {
            r[i] = (byte)((int)result[i] & 0xFF);
        }
//        if(result != null) {
//
//        }
        return r;

//        char[] hexArr = hex.toCharArray();
//        Hex.decodeHex()
//        char[] rawHex = hex.toCharArray();
//
//        if(rawHex[0] != '#' || (rawHex.length < 7 || rawHex.length > 9)) return -1;
//
//        int r = (checkChar(rawHex[1]) * 16) + checkChar(rawHex[2]);
//        int g = (checkChar(rawHex[3]) * 16) + checkChar(rawHex[4]);
//        int b = (checkChar(rawHex[5]) * 16) + checkChar(rawHex[6]);
//        int a = rawHex.length == 7 ? 255 : (checkChar(rawHex[7]) * 16) + checkChar(rawHex[8]);
//
////        getPlayer().sendChatMessage(String.format("Raw - r: %d, g: %d, b: %d, a: %d", r, g, b, a));
////        getPlayer().sendChatMessage(String.format("Clamped - r: %d, g: %d, b: %d, a: %d",
////                (r & 0xFF), (g & 0xFF), (b & 0xFF), (a & 0xFF)));
//        return rgba(r & 0xFF, g & 0xFF, b & 0xFF, a & 0xFF);
    }

//    private static int checkChar(char c) {
//        if(c >= '1' && c <= '9') return Integer.parseInt(String.valueOf(c));
//        else {
//            switch(c) {
//                case 'A' & 'a':
//                    return 10;
//                case 'B' & 'b':
//                    return 11;
//                case 'C' & 'c':
//                    return 12;
//                case 'D' | 'd':
//                    return 13;
//                case 'E' | 'e':
//                    return 14;
//                case 'F' | 'f':
//                    return 15;
//
//                default:
//                    return 0;
//            }
//        }
//    }

    enum Directions {
        NORTH         ("N",  "",  "-"),
        NORTH_EAST    ("NE", "+", "-"),
        EAST          ("E",  "+", ""),
        SOUTH_EAST    ("SE", "+", "+"),
        SOUTH         ("S",  "",  "+"),
        SOUTH_WEST    ("SW", "-", "+"),
        WEST          ("W",  "-", ""),
        NORTH_WEST    ("NW", "-", "-");

        String direction, x, z; // Cardinal direction, x direction, z direction.

        Directions(String c, String x, String z) {
            this.direction = c;
            this.x = x;
            this.z = z;
        }
    }
}