package dev.kybt.kcoords.events;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.render.SurfaceHelper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Deprecated
public class SubscribeHandler implements GlobalVars {

    public static int right;
    public static int bottom;
    public static int width;
    public static int height;

//    private final int lineSpace = 10;

    private int boxHeight = KybtCoords.positionY; // + 60
    private int boxWidth = KybtCoords.positionX + 100; // + 100

    private String xDirection = "";
    private String zDirection = "";

    private final SurfaceHelper surfaceHelper = SurfaceHelper.getInstance();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(!KybtCoords.isEnabled) return;
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR
                || minecraft.gameSettings.showDebugInfo
                || event.isCancelable())
            return;

        renderData();
        restrictBox();
    }

    public void renderData() {
        int line = KybtCoords.positionY + 1;
        final int lineSpace = 10;

        updateDirections();

//        double scale = 1.0D;

        BlockPos playerLocation = new BlockPos(
                minecraft.getRenderViewEntity().posX,
                minecraft.getRenderViewEntity().getEntityBoundingBox().minY,
                minecraft.getRenderViewEntity().posZ);

        surfaceHelper.drawRect(KybtCoords.positionX, KybtCoords.positionY,
                (int) (boxWidth * KybtCoords.scale),
                (int) (boxHeight),
                KybtCoords.backgroundColor);

//        surfaceHelper.drawRect(KybtCoords.positionX, KybtCoords.positionY,
//                (int) (boxWidth * KybtCoords.scale), (int) (boxHeight * KybtCoords.scale),
//                KybtCoords.backgroundColor, KybtCoords.scale);

        right = KybtCoords.positionX + boxWidth; // (int) ((KybtCoords.positionX + 100));
        width = boxWidth; // (int) ((KybtCoords.positionX + 100) * KybtCoords.scale);

        // Render X coordinate and direction
        surfaceHelper.drawTextTest("X: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
        surfaceHelper.drawTextTest("" + Utils.roundDouble(minecraft.thePlayer.posX, 2),
                KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("X: ", KybtCoords.scale),
                line, KybtCoords.textColor, KybtCoords.scale, true);
        surfaceHelper.drawTextTest(xDirection, KybtCoords.positionX + ((int) (99 * KybtCoords.scale)) - surfaceHelper.getScaledFontWidth(xDirection, KybtCoords.scale), line,
                KybtCoords.textColor, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render Y coordinate and direction
        surfaceHelper.drawTextTest("Y: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
        surfaceHelper.drawTextTest("" + (int) Math.round(minecraft.thePlayer.posY),
                KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("Y: ", KybtCoords.scale),
                line, KybtCoords.textColor, KybtCoords.scale, true);
        surfaceHelper.drawTextTest(Utils.DIRECTIONS[Utils.getDirection()],
                KybtCoords.positionX + ((int) (99 * KybtCoords.scale)) - surfaceHelper.getScaledFontWidth(Utils.DIRECTIONS[Utils.getDirection()], KybtCoords.scale), line,
                KybtCoords.keyColor, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render Z coordinate and direction
        surfaceHelper.drawTextTest("Z: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
        surfaceHelper.drawTextTest("" + Utils.roundDouble(minecraft.thePlayer.posZ, 2),
                KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("Z: ", KybtCoords.scale),
                line, KybtCoords.textColor, KybtCoords.scale, true);
        surfaceHelper.drawTextTest(zDirection, KybtCoords.positionX + ((int) (99 * KybtCoords.scale)) - surfaceHelper.getScaledFontWidth(zDirection, KybtCoords.scale), line,
                KybtCoords.textColor, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render C counter if enabled
        if(KybtCoords.showC) {
            surfaceHelper.drawTextTest("C: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
            surfaceHelper.drawTextTest("" + Utils.fetchCCounter(),
                    KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("C: ", KybtCoords.scale),
                    line, KybtCoords.textColor, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        // Render biome if enabled
        if(KybtCoords.showBiomes) {
            surfaceHelper.drawTextTest("B: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
            surfaceHelper.drawTextTest("" + Utils.fetchBiomeName(playerLocation),
                    KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("B: ", KybtCoords.scale),
                    line, Utils.fetchBiomeColor(playerLocation), KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        // Render FPS counter if enabled
        if(KybtCoords.showFPS) {
            surfaceHelper.drawTextTest("FPS: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
            surfaceHelper.drawTextTest("" + minecraft.debug.split(" ")[0],
                    KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("FPS: ", KybtCoords.scale),
                    line, KybtCoords.textColor, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        bottom = KybtCoords.positionY + boxHeight;
        height = boxHeight;

        boxHeight = line - KybtCoords.positionY;
        boxWidth = (int)(boxWidth - (KybtCoords.positionX * KybtCoords.scale));
//        boxWidth = (int)(KybtCoords.scale / KybtCoords.positionX);
//        boxWidth = (int) ((KybtCoords.positionX + 100) * KybtCoords.scale);
    }

    public void restrictBox() {
        ScaledResolution resolution = new ScaledResolution(minecraft);

        if(bottom >= resolution.getScaledHeight())
            KybtCoords.positionY = resolution.getScaledHeight() - height;

        if(right >= resolution.getScaledWidth())
            KybtCoords.positionX = resolution.getScaledWidth() - width; // (int) (width * KybtCoords.scale)

        if(KybtCoords.positionX <= 0)
            KybtCoords.positionX = 0;

        if(KybtCoords.positionY <= 0)
            KybtCoords.positionY = 0;
    }

    // TODO: This sucks, fix it
    private void updateDirections() {
        String direction = Utils.DIRECTIONS[Utils.getDirection()];

        if(direction.equals("S")) {
            zDirection = "+";
            xDirection = "";
        }
        if(direction.equals("N")) {
            zDirection = "-";
            xDirection = "";
        }
        if(direction.equals("E")) {
            xDirection = "+";
            zDirection = "";
        }
        if(direction.equals("W")) {
            xDirection = "-";
            zDirection = "";
        }

        if(direction.equals("SE")) {
            zDirection = "+";
            xDirection = "+";
        }
        if(direction.equals("NE")) {
            xDirection = "+";
            zDirection = "-";
        }

        if(direction.equals("NW")) {
            xDirection = "-";
            zDirection = "-";
        }

        if(direction.equals("SW")) {
            xDirection = "-";
            zDirection = "+";
        }
    }
}
