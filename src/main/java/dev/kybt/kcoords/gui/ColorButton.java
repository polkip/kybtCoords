package dev.kybt.kcoords.gui;

import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.render.RenderHelper;
import javafx.scene.paint.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

// TODO: Implement this in a future version
public class ColorButton extends GuiButton {

    private final RenderHelper render = RenderHelper.getInstance();

    private int color;

    private final int w = 10, h = 10;

    public ColorButton(int buttonId, int x, int y, String buttonText, int color) {
        super(buttonId, x, y, buttonText);
        this.color = color;
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        return super.getHoverState(mouseOver);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        render.drawRect(this.xPosition, this.yPosition, width, height, Utils.WHITE);
        render.drawRect(xPosition, yPosition, (width - 1), (height - 1), color);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return super.mousePressed(mc, mouseX, mouseY);
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        super.playPressSound(soundHandlerIn);
    }
}
