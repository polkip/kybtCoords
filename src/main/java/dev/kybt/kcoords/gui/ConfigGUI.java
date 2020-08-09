package dev.kybt.kcoords.gui;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.events.SubscribeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.List;

public class ConfigGUI extends GuiScreen implements GlobalVars {
    private final SubscribeHandler subscribe = new SubscribeHandler();
//    private GuiButton buttonCoordsEnabled;
    private GuiButton buttonShowFPS;
    private GuiButton buttonColoredBiomes;
    private GuiButton buttonShowC;
    private GuiButton buttonShowBiomes;
//    private GuiButton buttonColors;

    private GuiTextField fieldKeyColor;
    private GuiTextField fieldTextColor;
//    private GuiSlider buttonScale;

//    private GuiSlider sliderMarkerColor;
//    private GuiSlider sliderTextColor;

    @Override
    public void initGui() {
        buttonShowFPS = new GuiButton(0, (width / 2 - 60), (height / 2 - 62), 150, 10, "FPS: " + (KybtCoords.showFPS ? "Enabled" : "Disabled"));
        buttonList.add(buttonShowFPS);

        buttonColoredBiomes = new GuiButton(1, (width / 2 - 60), (height / 2 - 40), 100, 10, "Biome colors: " + (KybtCoords.coloredBiomes ? "Enabled" : "Disabled"));
        buttonList.add(buttonColoredBiomes);

        buttonShowC = new GuiButton(2, (width / 2 - 60), (height / 2 - 18), 100, 10, "C counter: " + (KybtCoords.showC ? "Enabled" : "Disabled"));
        buttonList.add(buttonShowC);

        buttonShowBiomes = new GuiButton(3, (width / 2 - 60), (height / 2 + 4), 100, 10, "Biome: " + (KybtCoords.showBiomes ? "Enabled" : "Disabled"));
        buttonList.add(buttonShowBiomes);

//        fieldKeyColor = new GuiTextField(4, minecraft.fontRendererObj, (width / 2 - 70), (height / 2 + 26), 100, 10);
//        labelList.add(fieldKeyColor);
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        minecraft.displayGuiScreen(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        subscribe.renderData();
        subscribe.updateRenders();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0:
                buttonShowFPS.displayString = ("FPS: " + ((KybtCoords.showFPS = !KybtCoords.showFPS) ? "Enabled" : "Disabled"));
                break;
            case 1:
                buttonColoredBiomes.displayString = ("Biome colors: " + ((KybtCoords.coloredBiomes = !KybtCoords.coloredBiomes) ? "Enabled" : "Disabled"));
                break;
            case 2:
                buttonShowC.displayString = ("C counter: " + ((KybtCoords.showC = !KybtCoords.showC) ? "Enabled" : "Disabled"));
                break;
            case 3:
                buttonShowBiomes.displayString = ("Biome: " + ((KybtCoords.showBiomes = !KybtCoords.showBiomes) ? "Enabled" : "Disabled"));
                break;
        }
    }

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(w, h);
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
