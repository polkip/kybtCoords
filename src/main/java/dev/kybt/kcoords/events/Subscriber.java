package dev.kybt.kcoords.events;

import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.render.SurfaceBuilder;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class Subscriber {

    public static int right;
    public static int bottom;
    public static int width;
    public static int height;

    private int boxHeight = KybtCoords.positionY + 60;
    private int boxWidth = KybtCoords.positionX + 100;

    public static String direction = "";
    public static String xDirection = "";
    public static String zDirection = "";

    private final SurfaceBuilder surfaceBuilder = SurfaceBuilder.getInstance();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(!KybtCoords.isEnabled) return;
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR
                || Utils.getMinecraft().gameSettings.showDebugInfo
                || event.isCancelable())
            return;

        renderData();
        restrictBox();
    }

    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        if(event.message.startsWith("#")) {
            Utils.getPlayer().sendChatMessage("Parsing hexadecimal text " + event.message);
            byte[] decodedHex = Utils.parseHexadecimal(event.message);
//            assert decodedHex != null : "Illegal hex value";
            Utils.getPlayer().sendChatMessage("Decoded hex string: " + Arrays.toString(decodedHex));
        }
    }

    public void renderData() {
        int line = KybtCoords.positionY + 1;
        final int lineSpace = 10;

        Utils.updateDirection();

//        updateDirections();

//        double scale = 1.0D;

        BlockPos playerLocation = new BlockPos(
                Utils.getMinecraft().getRenderViewEntity().posX,
                Utils.getMinecraft().getRenderViewEntity().getEntityBoundingBox().minY,
                Utils.getMinecraft().getRenderViewEntity().posZ);

//        GlStateManager.pushMatrix();
//        GlStateManager.translate(0.0F, 0.0F, 1.0F);

        surfaceBuilder.drawRect(KybtCoords.positionX, KybtCoords.positionY,
                boxWidth, boxHeight, KybtCoords.backgroundColor);

        right = (int)((KybtCoords.positionX + (100 * KybtCoords.scale))); // (int)((KybtCoords.positionX + 100) * KybtCoords.scale)
        width = (int)(100 * KybtCoords.scale); // 100

        // begin
        // Render X coordinate and direction
        surfaceBuilder.drawRainbowText("X: ", KybtCoords.positionX + 1, line, KybtCoords.scale, true); // KybtCoords.labelColor
        surfaceBuilder.drawRainbowText("" + Utils.roundDouble(Utils.getPlayer().posX, 2),
                KybtCoords.positionX + 1 + surfaceBuilder.getScaledFontWidth("X: ", KybtCoords.scale), line, KybtCoords.scale, true);
        surfaceBuilder.drawRainbowText(xDirection, KybtCoords.positionX + (int)(99 * KybtCoords.scale)
                        - surfaceBuilder.getScaledFontWidth(xDirection, KybtCoords.scale), line, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render Y coordinate and direction
        surfaceBuilder.drawRainbowText("Y: ", KybtCoords.positionX + 1, line, KybtCoords.scale, true); // KybtCoords.labelColor
        surfaceBuilder.drawRainbowText("" + (int) Math.round(Utils.getPlayer().posY),
                KybtCoords.positionX + 1 + surfaceBuilder.getScaledFontWidth("Y: ", KybtCoords.scale), line, KybtCoords.scale, true);
        surfaceBuilder.drawRainbowText(direction, // Utils.CARDINAL_DIRECTIONS[Utils.getDirection()]
                KybtCoords.positionX + (int)(99 * KybtCoords.scale)
                        - surfaceBuilder.getScaledFontWidth(direction, KybtCoords.scale), line, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render Z coordinate and direction
        surfaceBuilder.drawRainbowText("Z: ", KybtCoords.positionX + 1, line, KybtCoords.scale, true); // KybtCoords.labelColor
        surfaceBuilder.drawRainbowText("" + Utils.roundDouble(Utils.getPlayer().posZ, 2),
                KybtCoords.positionX + 1 + surfaceBuilder.getScaledFontWidth("Z: ", KybtCoords.scale), line, KybtCoords.scale, true);
        surfaceBuilder.drawRainbowText(zDirection, KybtCoords.positionX + (int)(99 * KybtCoords.scale)
                        - surfaceBuilder.getScaledFontWidth(zDirection, KybtCoords.scale), line, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render C counter if enabled
        if(KybtCoords.showC) {
            surfaceBuilder.drawRainbowText("C: ", KybtCoords.positionX + 1, line, KybtCoords.scale, true); // KybtCoords.labelColor
            surfaceBuilder.drawRainbowText("" + Utils.fetchCCounter(),
                    KybtCoords.positionX + 1 + surfaceBuilder.getScaledFontWidth("C: ", KybtCoords.scale), line, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        // Render biome if enabled
        if(KybtCoords.showBiomes) {
            surfaceBuilder.drawRainbowText("B: ", KybtCoords.positionX + 1, line, KybtCoords.scale, true); // KybtCoords.labelColor
            surfaceBuilder.drawRainbowText("" + Utils.fetchBiomeName(playerLocation),
                    KybtCoords.positionX + 1 + surfaceBuilder.getScaledFontWidth("B: ", KybtCoords.scale), line, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        // Render FPS counter if enabled
        if(KybtCoords.showFPS) {
            surfaceBuilder.drawRainbowText("FPS: " +
                    "", KybtCoords.positionX + 1, line, KybtCoords.scale, true);
            surfaceBuilder.drawRainbowText("" + Utils.getMinecraft().debug.split(" ")[0],
                    KybtCoords.positionX + 1 + surfaceBuilder.getScaledFontWidth("FPS: ", KybtCoords.scale), line, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }
        // end

//        GlStateManager.popMatrix();

//        int width = KybtCoords.positionX + 100;
//        int height = KybtCoords.positionY + line;
        bottom = KybtCoords.positionY + boxHeight;
        height = boxHeight;

        boxHeight = line - KybtCoords.positionY;
        boxWidth = (int)(100 * KybtCoords.scale);
    }

    public void restrictBox() {
        ScaledResolution resolution = new ScaledResolution(Utils.getMinecraft());

        if(bottom >= resolution.getScaledHeight())
            KybtCoords.positionY = resolution.getScaledHeight() - height;

        if(right >= resolution.getScaledWidth())
            KybtCoords.positionX = resolution.getScaledWidth() - width;

        if(KybtCoords.positionX <= 0)
            KybtCoords.positionX = 0;


        if(KybtCoords.positionY <= 0)
            KybtCoords.positionY = 0;
    }

//    @Deprecated
//    private void updateDirections() {
//        String direction = Utils.getDirection();
//
//        if(direction.equals("S")) {
//            zDirection = "+";
//            xDirection = "";
//        }
//        if(direction.equals("N")) {
//            zDirection = "-";
//            xDirection = "";
//        }
//        if(direction.equals("E")) {
//            xDirection = "+";
//            zDirection = "";
//        }
//        if(direction.equals("W")) {
//            xDirection = "-";
//            zDirection = "";
//        }
//
//        if(direction.equals("SE")) {
//            zDirection = "+";
//            xDirection = "+";
//        }
//        if(direction.equals("NE")) {
//            xDirection = "+";
//            zDirection = "-";
//        }
//
//        if(direction.equals("NW")) {
//            xDirection = "-";
//            zDirection = "-";
//        }
//
//        if(direction.equals("SW")) {
//            xDirection = "-";
//            zDirection = "+";
//        }
//    }
}