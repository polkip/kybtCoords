package dev.kybt.kcoords.events;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.render.RenderHelper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SubscribeHandler implements GlobalVars {

    public static int right;
    public static int bottom;
    public static int width;
    public static int height;

    private int boxHeight = KybtCoords.positionY + 60;
    private final int boxWidth = KybtCoords.positionX + 100;

    private String xDirection = "";
    private String zDirection = "";

    private final RenderHelper render = RenderHelper.getInstance();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(!KybtCoords.isEnabled) return;
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR || minecraft.gameSettings.showDebugInfo || event.isCancelable()) return;

        renderTest();
        updateRenders();
    }

    public void renderData() {
        int lineX = KybtCoords.positionY + 1;
        int lineY = lineX + 10;
        int lineZ = lineY + 10;
        int lineC = lineZ + 10;
        int lineBiome = lineC + 10;
        int lineFPS = lineBiome + 10;

        int height2 = KybtCoords.positionX + 100;
        int width2 = KybtCoords.positionY + 60;

        updateDirections();

        double scale = 0.85;

        render.drawRect(KybtCoords.positionX, KybtCoords.positionY,
                width2, height2, Utils.rgba(0, 0, 0, KybtCoords.backgroundOpacity));

        right = (KybtCoords.positionX + 60);
        width = 60;

        BlockPos playerLocation = new BlockPos(
                minecraft.getRenderViewEntity().posX,
                minecraft.getRenderViewEntity().getEntityBoundingBox().minY,
                minecraft.getRenderViewEntity().posZ);

        GlStateManager.pushMatrix();

        render.drawText("X: ", KybtCoords.positionX + 1, lineX, KybtCoords.keyColor, scale, true);
        render.drawText("Y: ", KybtCoords.positionX + 1, lineY, KybtCoords.keyColor, scale, true);
        render.drawText("Z: ", KybtCoords.positionX + 1, lineZ, KybtCoords.keyColor, scale, true);
        render.drawText("C: ", KybtCoords.positionX + 1, lineC, KybtCoords.keyColor, scale, true);
        render.drawText("Biome: ", KybtCoords.positionX + 1, lineBiome, KybtCoords.keyColor, scale, true);
        render.drawText("FPS: ", KybtCoords.positionX + 1, lineFPS, KybtCoords.keyColor, scale, true);

        render.drawText("" + Utils.roundDouble(minecraft.thePlayer.posX, 2),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("X: ", scale), lineX, KybtCoords.textColor, scale, true);
        render.drawText("" + (int) Math.round(minecraft.thePlayer.posY),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("Y: ", scale), lineY, KybtCoords.textColor, scale, true);
        render.drawText("" + Utils.roundDouble(minecraft.thePlayer.posZ, 2),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("Z: ", scale), lineZ, KybtCoords.textColor, scale, true);
        render.drawText("" + Utils.fetchCCounter(),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("C: ", scale), lineC, KybtCoords.textColor, scale, true);
        render.drawText("" + Utils.fetchBiomeName(playerLocation),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("Biome: ", scale), lineBiome, Utils.fetchBiomeColor(playerLocation), scale, true);
        render.drawText("" + minecraft.debug.split(" ")[0],
                KybtCoords.positionX + 1 + render.getScaledFontWidth("FPS: ", scale), lineFPS, KybtCoords.textColor, scale, true);

        render.drawText(xDirection, KybtCoords.positionX + 99 - render.getScaledFontWidth(xDirection, scale), lineX,
                KybtCoords.textColor, scale, true);
        render.drawText(Utils.DIRECTIONS[Utils.getDirection()],
                KybtCoords.positionX + 99 - render.getScaledFontWidth(Utils.DIRECTIONS[Utils.getDirection()], scale), lineY,
                KybtCoords.keyColor, scale, true);
        render.drawText(zDirection, KybtCoords.positionX + 99 - render.getScaledFontWidth(zDirection, scale), lineZ,
                KybtCoords.textColor, scale, true);

        GlStateManager.popMatrix();

        bottom = KybtCoords.positionY + 41;
        height = KybtCoords.positionX + 40;
    }

    public void renderTest() {
        int line = KybtCoords.positionY + 2;

        updateDirections();

        double scale = 1.0D;

        BlockPos playerLocation = new BlockPos(
                minecraft.getRenderViewEntity().posX,
                minecraft.getRenderViewEntity().getEntityBoundingBox().minY,
                minecraft.getRenderViewEntity().posZ);

//        GlStateManager.pushMatrix();
//        GlStateManager.translate(0.0F, 0.0F, 1.0F);

        render.drawRect(KybtCoords.positionX, KybtCoords.positionY,
                boxWidth, boxHeight, Utils.rgba(0, 0, 0, KybtCoords.backgroundOpacity));

        right = (KybtCoords.positionX + 60);
        width = 60;

        // Render X coordinate and direction
        render.drawText("X: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, scale, true);
        render.drawText("" + Utils.roundDouble(minecraft.thePlayer.posX, 2),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("X: ", scale), line, KybtCoords.textColor, scale, true);
        render.drawText(xDirection, KybtCoords.positionX + 99 - render.getScaledFontWidth(xDirection, scale), line,
                KybtCoords.textColor, scale, true);

        line += 10;

        // Render Y coordinate and direction
        render.drawText("Y: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, scale, true);
        render.drawText("" + (int) Math.round(minecraft.thePlayer.posY),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("Y: ", scale), line, KybtCoords.textColor, scale, true);
        render.drawText(Utils.DIRECTIONS[Utils.getDirection()],
                KybtCoords.positionX + 99 - render.getScaledFontWidth(Utils.DIRECTIONS[Utils.getDirection()], scale), line,
                KybtCoords.keyColor, scale, true);

        line += 10;

        // Render Z coordinate and direction
        render.drawText("Z: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, scale, true);
        render.drawText("" + Utils.roundDouble(minecraft.thePlayer.posZ, 2),
                KybtCoords.positionX + 1 + render.getScaledFontWidth("Z: ", scale), line, KybtCoords.textColor, scale, true);
        render.drawText(zDirection, KybtCoords.positionX + 99 - render.getScaledFontWidth(zDirection, scale), line,
                KybtCoords.textColor, scale, true);

        line += 10;

        // Render C counter if enabled
        if(KybtCoords.showC) {
            render.drawText("C: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, scale, true);
            render.drawText("" + Utils.fetchCCounter(),
                    KybtCoords.positionX + 1 + render.getScaledFontWidth("C: ", scale), line, KybtCoords.textColor, scale, true);
            line += 10;
        }

        // Render biome if enabled
        if(KybtCoords.showBiomes) {
            render.drawText("B: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, scale, true);
            render.drawText("" + Utils.fetchBiomeName(playerLocation),
                    KybtCoords.positionX + 1 + render.getScaledFontWidth("B: ", scale), line, Utils.fetchBiomeColor(playerLocation), scale, true);
            line += 10;
        }

        // Render FPS counter if enabled
        if(KybtCoords.showFPS) {
            render.drawText("FPS: ", KybtCoords.positionX + 1, line, KybtCoords.keyColor, scale, true);
            render.drawText("" + minecraft.debug.split(" ")[0],
                    KybtCoords.positionX + 1 + render.getScaledFontWidth("FPS: ", scale), line, KybtCoords.textColor, scale, true);
            line += 10;
        }

//        GlStateManager.popMatrix();

//        int width = KybtCoords.positionX + 100;
//        int height = KybtCoords.positionY + line;
        bottom = KybtCoords.positionY + 100;
        height = KybtCoords.positionX + 101;

        boxHeight = KybtCoords.positionY + line;
    }

    public static void updateRenders() {
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

    // FIXME: There is probably a more efficient way of doing this but it's 2:00AM and I can't be bothered to find one.
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
