package dev.kybt.kcoords;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class Utils implements GlobalVars {

    public static final String[] DIRECTIONS = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public static int toRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b) + (a << 24);
    }

    // Credits to boomboompower
    public static int getDirection() {
        double direction = MathHelper.wrapAngleTo180_float(minecraft.thePlayer.rotationYaw) + 180.0D;

        direction += 22.5D;
        direction %= 360.0D;
        direction /= 45.0D;

        return MathHelper.floor_double(direction);
    }

    public static String fetchCCounter() {
        String c = "null";
        String renderInfo = minecraft.renderGlobal.getDebugInfoRenders();
        c = renderInfo.split(Pattern.quote("/"))[0].split(" ")[1];
        return c;
    }

    public static int fetchBiomeColor(BlockPos position) {
        if(minecraft.theWorld != null && minecraft.theWorld.isBlockLoaded(position)) {
            Chunk chunk = minecraft.theWorld.getChunkFromBlockCoords(position);
            return chunk.getBiome(position, minecraft.theWorld.getWorldChunkManager()).color;
        }

        return Colors.WHITE;
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

    // TODO: Make these do something.
    public static void saveSettings() {

    }

    public static void fetchSettings() {}

    public static class Colors {
        public static final int WHITE = Utils.toRGBA(255, 255, 255, 255);
        public static final int BLACK = Utils.toRGBA(0, 0, 0, 255);
        public static final int RED = Utils.toRGBA(255, 0, 0, 255);
        public static final int GREEN = Utils.toRGBA(0, 255, 0, 255);
        public static final int DARK_GREEN = Utils.toRGBA(0, 128, 0, 255);
        public static final int BLUE = Utils.toRGBA(0, 0, 255, 255);
        public static final int PURPLE = Utils.toRGBA(163, 73, 163, 255);
        public static final int YELLOW = Utils.toRGBA(255, 255, 0 ,255);
        public static final int DARK_GRAY = Utils.toRGBA(128, 128, 128, 255);
        public static final int LIGHT_GRAY = Utils.toRGBA(192, 192, 192, 255);
        public static final int ORANGE = Utils.toRGBA(255, 165, 0, 255);
        public static final int DARK_RED = Utils.toRGBA(64, 0, 0, 255);
        public static final int CERULEAN = Utils.toRGBA(16, 32, 75, 255);
        public static final int CREAM = Utils.toRGBA(255, 253, 208, 255);
        public static final int PERU = Utils.toRGBA(205, 133, 63, 255);
        public static final int CYAN = Utils.toRGBA(0, 255, 255, 255);
    }
}
