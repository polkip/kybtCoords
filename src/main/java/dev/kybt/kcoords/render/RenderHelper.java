package dev.kybt.kcoords.render;

import dev.kybt.kcoords.GlobalVars;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderHelper implements GlobalVars {

    public static void drawRect(int x, int y, int w, int h, int color) {
        GL11.glPushMatrix();
        GL11.glLineWidth(1.0F);
        Gui.drawRect(x, y, x + w, y + h, color);
        GL11.glPopMatrix();
    }

    public static void drawTextShadowCentered(String text, float  x, float y, int color) {
        float offsetX = getFontWidth(text) / 2F;
        float offsetY = getFontHeight() / 2F;
        minecraft.fontRendererObj.drawStringWithShadow(text, x - offsetX, y - offsetY, color);
    }

    public static void drawText(String text, int x, int y, int color, double scale, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.scale(scale, scale, scale);
        minecraft.fontRendererObj.drawString(text, (int) (x * (1 / scale)), (int) (y * (1 / scale)), color, shadow);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void drawText(String text, int x, int y, int color, double scale) {
        drawText(text, x, y, color, scale, false);
    }

    public static void drawTextWithShadow(String text, int x, int y, int color, double scale) {
        drawText(text, x, y, color, scale, true);
    }

    public static void drawOutlinedRectShaded(int x, int y, int w, int h, int outlineColor, int shadeColor, int shade, float width) {
        int shaded = (0x00FFFF & shade) | ((shade & 255) << 24);

        drawRect(x, y, w, h, shaded);
        drawOutlinedRect(x, y, w, h, outlineColor, width);
    }

    public static void drawOutlinedRectFilled(int x, int y, int w, int h, int outlineColor, int fillColor, float width) {
        drawRect(x, y, w, h, fillColor);
        drawOutlinedRect(x, y, w, h, outlineColor, width);
    }

    public static void drawOutlinedRect(int x, int y, int w, int h, int color, float width) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        float a = (float) (color >> 24 & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA,
                GL11.GL_ONE_MINUS_SRC_ALPHA,
                GL11.GL_ONE,
                GL11.GL_ZERO);
        GlStateManager.color(r, g, b, a);

        GL11.glLineWidth(width);

        renderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        renderer.pos((double) x, (double) y, 0.0D).endVertex();
        renderer.pos((double) x, (double) y + h, 0.0D).endVertex();
        renderer.pos((double) x + w, (double) y + h, 0.0D).endVertex();
        renderer.pos((double) x + w, (double) y, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static int getFontHeight() {
        return minecraft.fontRendererObj.FONT_HEIGHT;
    }

    public static int getScaledFontHeight(double scale) {
        return (int) (minecraft.fontRendererObj.FONT_HEIGHT * scale);
    }

    public static int getScaledFontWidth(String text, double scale) {
        return (int) (minecraft.fontRendererObj.getStringWidth(text) * scale);
    }

    public static int getFontWidth(String text) {
        return (int) (minecraft.fontRendererObj.getStringWidth(text));
    }
}
