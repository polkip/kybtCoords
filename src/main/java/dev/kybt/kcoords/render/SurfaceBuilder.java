package dev.kybt.kcoords.render;

import dev.kybt.kcoords.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SurfaceBuilder {

    private static final SurfaceBuilder INSTANCE = new SurfaceBuilder();
//    private static float hue = 0.0f;
//    private int iter = 0;

    private SurfaceBuilder() {}

    public static SurfaceBuilder getInstance() {
        return INSTANCE;
    }

    public void drawRect(int x, int y, int w, int h, int color) {
        GL11.glPushMatrix();
        GL11.glLineWidth(1.0F);
        Gui.drawRect(x, y, x + w, y + h, color);
        GL11.glPopMatrix();
    }

//    public void drawRect(int x, int y, int w, int h, int color, double scale) {
//        GL11.glPushMatrix();
//        GL11.glLineWidth(1.0F);
//        GL11.glScaled(scale, scale, scale);
//        Gui.drawRect((int) (x *  scale), (int) (y * scale),
//                (int) ((x + w) * scale), (int) ((y + h) * scale), color);
//        GL11.glPopMatrix();
//    }

//    public void drawRect(int left, int right, int bottom, int top, int color, double scale) {
//        int j;
//        if(left < right) {
//            j = left;
//            left = right;
//            right = j;
//        }
//        if(top < bottom) {
//            j = top;
//            top = bottom;
//            bottom = j;
//        }
//
//        float alpha = (float) (color >> 24 & 255) / 255.0F;
//        float red = (float) (color >> 16 & 255) / 255.0F;
//        float green = (float) (color >> 8 & 255) / 255.0F;
//        float blue = (float) (color & 255) / 255.0F;
//
//        Tessellator tessellator = Tessellator.getInstance();
//        WorldRenderer renderer = tessellator.getWorldRenderer();
//        GlStateManager.enableBlend();
//        GlStateManager.disableTexture2D();
//        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
////        int j;
////        if (left < right) {
////            j = left;
////            left = right;
////            right = j;
////        }
////
////        if (top < bottom) {
////            j = top;
////            top = bottom;
////            bottom = j;
////        }
////
////        float f3 = (float)(color >> 24 & 255) / 255.0F;
////        float f = (float)(color >> 16 & 255) / 255.0F;
////        float f1 = (float)(color >> 8 & 255) / 255.0F;
////        float f2 = (float)(color & 255) / 255.0F;
////        Tessellator tessellator = Tessellator.getInstance();
////        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
////        GlStateManager.enableBlend();
////        GlStateManager.disableTexture2D();
////        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
////        GlStateManager.color(f, f1, f2, f3);
////        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
////        worldrenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
////        worldrenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
////        worldrenderer.pos((double)right, (double)top, 0.0D).endVertex();
////        worldrenderer.pos((double)left, (double)top, 0.0D).endVertex();
////        tessellator.draw();
////        GlStateManager.enableTexture2D();
////        GlStateManager.disableBlend();
//    }

//    public void drawTextShadowCentered(String text, float  x, float y, int color) {
//        float offsetX = getFontWidth(text) / 2F;
//        float offsetY = getFontHeight() / 2F;
//        minecraft.fontRendererObj.drawStringWithShadow(text, x - offsetX, y - offsetY, color);
//    }

    public void drawText(String text, int x, int y, int color, double scale, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.scale(scale, scale, scale);
        Utils.getMinecraft().fontRendererObj.drawString(text, (int) (x * (1 / scale)), (int) (y * (1 / scale)), color, shadow);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public void drawRainbowText(String text, int x, int y, double scale, boolean shadow) {
        char[] textC = text.toCharArray();
        int width = x;

        for(char c : textC) {
//            int color = Color.HSBtoRGB(((hue - (width * 0.01f) - (y * 0.01f)) % 2) / 2, 1.0f, 1.0f);
            int color = Color.HSBtoRGB((float) ((System.currentTimeMillis() - (x * 10) - (y * 10)) % 2000) / 2000.0f, 1.0f, 1.0f);
            drawText(String.valueOf(c), width, y, color, scale, shadow);
            width += getScaledFontWidth(String.valueOf(c), scale);
//            if(hue >= 359.0f)
//                hue = 0.0f;
//            hue += 0.0005;
        }
    }

//    public void drawRainbowText(String text, int x, int y, double scale, boolean shadow) {
//        char[] textC = text.toCharArray();
//        int i = 0, x2 = x;
//
//        for(char c : textC) {
//            int color = 0xFF000000 |
//                    ((int)(Math.tan(frequency * i + 0) * 127 + 128) << 16) | // * 127 + 128
//                    ((int)(Math.tan(frequency * i + 2) * 127 + 128) << 8) | // * 127 + 128
//                    ((int)(Math.tan(frequency * i + 4) * 127 + 128)); // * 127 + 128
//            drawText(String.valueOf(c), x2, y, color, scale, shadow);
//            x2 += getScaledFontWidth(String.valueOf(c), scale);
//            if(frequency >= 3.0f)
//                frequency = 0;
//            frequency += 0.001f;
//            i++;
//        }
//    }

//    public void drawText(String text, int x, int y, int color, double scale) {
//        drawText(text, x, y, color, scale, false);
//    }
//
//    public void drawTextWithShadow(String text, int x, int y, int color, double scale) {
//        drawText(text, x, y, color, scale, true);
//    }
//
//    public void drawOutlinedRectShaded(int x, int y, int w, int h, int outlineColor, int shadeColor, int shade, float width) {
//        int shaded = (0x00FFFF & shade) | ((shade & 255) << 24);
//
//        drawRect(x, y, w, h, shaded);
//        drawOutlinedRect(x, y, w, h, outlineColor, width);
//    }

    public void drawOutlinedRectFilled(int x, int y, int w, int h, int outlineColor, int fillColor, float width) {
        drawRect(x, y, w, h, fillColor);
        drawOutlinedRect(x, y, w, h, outlineColor, width);
    }

    public void drawOutlinedRect(int x, int y, int w, int h, int color, float width) {
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
        renderer.pos(x, y, 0.0D).endVertex();
        renderer.pos( x, (double) y + h, 0.0D).endVertex();
        renderer.pos((double) x + w, (double) y + h, 0.0D).endVertex();
        renderer.pos((double) x + w, y, 0.0D).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

//    public int getFontHeight() {
//        return minecraft.fontRendererObj.FONT_HEIGHT;
//    }

//    public int getScaledFontHeight(double scale) {
//        return (int) (Utils.getMinecraft().fontRendererObj.FONT_HEIGHT * scale);
//    }

    public int getScaledFontWidth(String text, double scale) {
        return (int) (Utils.getMinecraft().fontRendererObj.getStringWidth(text) * scale);
    }

//    public int getFontWidth(String text) {
//        return (int) (minecraft.fontRendererObj.getStringWidth(text));
//    }
}
