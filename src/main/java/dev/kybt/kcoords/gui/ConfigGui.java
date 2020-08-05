package dev.kybt.kcoords.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

import java.io.IOException;
import java.util.List;

public class ConfigGui extends GuiScreen {
//    private GuiButton buttonCoordsEnabled;
//    private GuiButton buttonFPS;
    private GuiButton buttonOutlined;
    private GuiButton buttonTextShadow;
    private GuiButton buttonChangePos;
//    private GuiButton buttonColoredBiomes;
//    private GuiButton buttonShowC;
//    private GuiButton buttonShowBiomes;

//    private GuiSlider buttonScale;

//    private GuiSlider sliderMarkerColor;
//    private GuiSlider sliderTextColor;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(w, h);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
