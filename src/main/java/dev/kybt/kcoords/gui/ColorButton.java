package dev.kybt.kcoords.gui;

import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.render.SurfaceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

// TODO: Implement this in a future version
public class ColorButton extends GuiButton {

    private final SurfaceHelper surfaceHelper = SurfaceHelper.getInstance();

    private int color;
    public int colorID;
//    private final int buttonId;

    private final int w = 20, h = 20;

    public ColorButton(int buttonId, int x, int y, String buttonText, int colorID) {
//        this.buttonId = buttonId;
        super(buttonId, x, y, buttonText);
        this.colorID = colorID;

        switch(colorID) {
            case 0:
                this.color = KybtCoords.keyColor;
                break;
            case 1:
                this.color = KybtCoords.textColor;
                break;
            case 2:
                this.color = KybtCoords.backgroundColor;
                break;
        }
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        return super.getHoverState(mouseOver);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        surfaceHelper.drawOutlinedRectFilled(xPosition, yPosition, w, h, Utils.WHITE, color, 1);
//        surfaceHelper.drawRect(this.xPosition, this.yPosition, w, h, Utils.WHITE);
//        surfaceHelper.drawRect(xPosition, yPosition, (w - 1), (h - 1), color);
    }

//    @Override
//    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
//        if(mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
//            new ColorGUI(id).display();
//        }
//        return super.mousePressed(mc, mouseX, mouseY);
//    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        super.playPressSound(soundHandlerIn);
    }
}
