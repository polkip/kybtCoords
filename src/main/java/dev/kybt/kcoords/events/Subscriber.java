package dev.kybt.kcoords.events;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.render.SurfaceHelper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Subscriber implements GlobalVars {

    public static int right;
    public static int bottom;
    public static int width;
    public static int height;

    private int boxHeight = KybtCoords.positionY + 60;
    private int boxWidth = KybtCoords.positionX + 100;

    private String xDirection = "";
    private String zDirection = "";

    private final SurfaceHelper surfaceHelper = SurfaceHelper.getInstance();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(!KybtCoords.isEnabled) return;
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR || minecraft.gameSettings.showDebugInfo || event.isCancelable()) return;

        renderData();
        restrictBox();
    }

    public void renderData() {
        int line = KybtCoords.positionY + 1;
        final int lineSpace = 10;

        updateDirections();

        double scale = 1.0D;

        BlockPos playerLocation = new BlockPos(
                minecraft.getRenderViewEntity().posX,
                minecraft.getRenderViewEntity().getEntityBoundingBox().minY,
                minecraft.getRenderViewEntity().posZ);

//        GlStateManager.pushMatrix();
//        GlStateManager.translate(0.0F, 0.0F, 1.0F);

        surfaceHelper.drawRect(KybtCoords.positionX, KybtCoords.positionY,
                boxWidth, boxHeight, KybtCoords.backgroundColor);

        right = (int)((KybtCoords.positionX + (100 * KybtCoords.scale))); // (int)((KybtCoords.positionX + 100) * KybtCoords.scale)
        width = (int)(100 * KybtCoords.scale); // 100

        // Render X coordinate and direction
        surfaceHelper.drawText("X: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
        surfaceHelper.drawText("" + Utils.roundDouble(minecraft.thePlayer.posX, 2),
                KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("X: ", KybtCoords.scale), line, KybtCoords.textColor, KybtCoords.scale, true);
        surfaceHelper.drawText(xDirection, KybtCoords.positionX + (int)(99 * KybtCoords.scale)
                        - surfaceHelper.getScaledFontWidth(xDirection, KybtCoords.scale), line,
                KybtCoords.textColor, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render Y coordinate and direction
        surfaceHelper.drawText("Y: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
        surfaceHelper.drawText("" + (int) Math.round(minecraft.thePlayer.posY),
                KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("Y: ", KybtCoords.scale), line, KybtCoords.textColor, KybtCoords.scale, true);
        surfaceHelper.drawText(Utils.DIRECTIONS[Utils.getDirection()],
                KybtCoords.positionX + (int)(99 * KybtCoords.scale)
                        - surfaceHelper.getScaledFontWidth(Utils.DIRECTIONS[Utils.getDirection()], KybtCoords.scale), line,
                KybtCoords.keyColor, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render Z coordinate and direction
        surfaceHelper.drawText("Z: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
        surfaceHelper.drawText("" + Utils.roundDouble(minecraft.thePlayer.posZ, 2),
                KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("Z: ", KybtCoords.scale), line, KybtCoords.textColor, KybtCoords.scale, true);
        surfaceHelper.drawText(zDirection, KybtCoords.positionX + (int)(99 * KybtCoords.scale)
                        - surfaceHelper.getScaledFontWidth(zDirection, KybtCoords.scale), line,
                KybtCoords.textColor, KybtCoords.scale, true);

        line += (lineSpace * KybtCoords.scale);

        // Render C counter if enabled
        if(KybtCoords.showC) {
            surfaceHelper.drawText("C: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
            surfaceHelper.drawText("" + Utils.fetchCCounter(),
                    KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("C: ", KybtCoords.scale), line, KybtCoords.textColor, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        // Render biome if enabled
        if(KybtCoords.showBiomes) {
            surfaceHelper.drawText("B: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
            surfaceHelper.drawText("" + Utils.fetchBiomeName(playerLocation),
                    KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("B: ", KybtCoords.scale), line, Utils.fetchBiomeColor(playerLocation), KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

        // Render FPS counter if enabled
        if(KybtCoords.showFPS) {
            surfaceHelper.drawText("FPS: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, KybtCoords.scale, true);
            surfaceHelper.drawText("" + minecraft.debug.split(" ")[0],
                    KybtCoords.positionX + 1 + surfaceHelper.getScaledFontWidth("FPS: ", KybtCoords.scale), line, KybtCoords.textColor, KybtCoords.scale, true);
            line += (lineSpace * KybtCoords.scale);
        }

//        GlStateManager.popMatrix();

//        int width = KybtCoords.positionX + 100;
//        int height = KybtCoords.positionY + line;
        bottom = KybtCoords.positionY + boxHeight;
        height = boxHeight;

        boxHeight = line - KybtCoords.positionY;
        boxWidth = (int)(100 * KybtCoords.scale);
    }

    public void restrictBox() {
        ScaledResolution resolution = new ScaledResolution(minecraft);

        if(bottom >= resolution.getScaledHeight())
            KybtCoords.positionY = resolution.getScaledHeight() - height;

        if(right >= resolution.getScaledWidth())
            KybtCoords.positionX = resolution.getScaledWidth() - width;

        if(KybtCoords.positionX <= 0)
            KybtCoords.positionX = 0;


        if(KybtCoords.positionY <= 0)
            KybtCoords.positionY = 0;
    }

    // FIXME: There's probably a more efficient way of doing this but it's 2:00AM and I can't be bothered to find one.
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