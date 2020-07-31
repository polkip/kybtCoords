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

    public static String xDirection = "";
    public static String zDirection = "";

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(!KybtCoords.isEnabled) return;
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR || minecraft.gameSettings.showDebugInfo) return;

        renderData();
//        updateRenders();
    }

    public static void renderData() {
        int lineX = KybtCoords.positionY + 1;
        int lineY = lineX + 10;
        int lineZ = lineY + 10;
        int lineC = lineZ + 10;
        int lineBiome = lineC + 10;
        int lineFPS = lineBiome + 10;

        updateDirections();

        RenderHelper.drawRect(KybtCoords.positionX, KybtCoords.positionY,
                KybtCoords.positionX + 100, KybtCoords.positionY + 60, Utils.toRGBA(0, 0, 0, 127));

        double scale = 0.85;

        right = (KybtCoords.positionX + 62);
        width = 62;

        BlockPos playerLocation = new BlockPos(
                minecraft.getRenderViewEntity().posX,
                minecraft.getRenderViewEntity().getEntityBoundingBox().minY,
                minecraft.getRenderViewEntity().posZ);

        GlStateManager.pushMatrix();

        RenderHelper.drawText("X: ", KybtCoords.positionX + 1, lineX, Utils.Colors.LIGHT_GRAY, scale, false);
        RenderHelper.drawText("Y: ", KybtCoords.positionX + 1, lineY, Utils.Colors.LIGHT_GRAY, scale, false);
        RenderHelper.drawText("Z: ", KybtCoords.positionX + 1, lineZ, Utils.Colors.LIGHT_GRAY, scale, false);
        RenderHelper.drawText("C: ", KybtCoords.positionX + 1, lineC, Utils.Colors.LIGHT_GRAY, scale, false);
        RenderHelper.drawText("Biome: ", KybtCoords.positionX + 1, lineBiome, Utils.Colors.LIGHT_GRAY, scale, false);
        RenderHelper.drawText("FPS: ", KybtCoords.positionX + 1, lineFPS, Utils.Colors.LIGHT_GRAY, scale, false);

        RenderHelper.drawText("" + Utils.roundDouble(minecraft.thePlayer.posX, 2),
                KybtCoords.positionX + 1 + RenderHelper.getScaledFontWidth("X: ", scale), lineX, Utils.Colors.WHITE, scale, false);
        RenderHelper.drawText("" + (int) Math.round(minecraft.thePlayer.posY),
                KybtCoords.positionX + 1 + RenderHelper.getScaledFontWidth("Y: ", scale), lineY, Utils.Colors.WHITE, scale, false);
        RenderHelper.drawText("" + Utils.roundDouble(minecraft.thePlayer.posZ, 2),
                KybtCoords.positionX + 1 + RenderHelper.getScaledFontWidth("Z: ", scale), lineZ, Utils.Colors.WHITE, scale, false);
        RenderHelper.drawText("" + Utils.fetchCCounter(),
                KybtCoords.positionX + 1 + RenderHelper.getScaledFontWidth("C: ", scale), lineC, Utils.Colors.WHITE, scale, false);
        RenderHelper.drawText("" + Utils.fetchBiomeName(playerLocation),
                KybtCoords.positionX + 1 + RenderHelper.getScaledFontWidth("Biome: ", scale), lineBiome, Utils.fetchBiomeColor(playerLocation), scale, false);
        RenderHelper.drawText("" + minecraft.debug.split(" ")[0],
                KybtCoords.positionX + 1 + RenderHelper.getScaledFontWidth("FPS: ", scale), lineFPS, Utils.Colors.WHITE, scale, false);

        RenderHelper.drawText(xDirection, KybtCoords.positionX + 99 - RenderHelper.getScaledFontWidth(xDirection, scale), lineX,
                Utils.Colors.WHITE, scale, false);
        RenderHelper.drawText(Utils.DIRECTIONS[Utils.getDirection()],
                KybtCoords.positionX + 99 - RenderHelper.getScaledFontWidth(Utils.DIRECTIONS[Utils.getDirection()], scale), lineY,
                Utils.Colors.WHITE, scale, false);
        RenderHelper.drawText(zDirection, KybtCoords.positionX + 99 - RenderHelper.getScaledFontWidth(zDirection, scale), lineZ,
                Utils.Colors.WHITE, scale, false);

        GlStateManager.popMatrix();

        bottom = KybtCoords.positionY + 41;
        height = KybtCoords.positionX + 40;
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

    // TODO: There is probably a more efficient way of doing this but it's 2:00AM and I can't be bothered to think of one.
    public static void updateDirections() {
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
