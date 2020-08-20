package dev.kybt.kcoords.gui;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.events.SubscribeHandler;
import dev.kybt.kcoords.render.SurfaceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ConfigGUI extends GuiScreen implements GlobalVars {
    private final SubscribeHandler subscribe = new SubscribeHandler();
    private final SurfaceHelper surface = SurfaceHelper.getInstance();
//    private GuiButton buttonCoordsEnabled;
    private GuiButton buttonShowFPS;
    private GuiButton buttonColoredBiomes;
    private GuiButton buttonShowC;
    private GuiButton buttonShowBiomes;

    private GuiSlider sliderScale;

    private ColorButton buttonKeyColor;
    private ColorButton buttonTextColor;
    private ColorButton buttonBackgroundColor;
//    private GuiButton buttonColors;

//    private GuiTextField fieldKeyColor;
//    private GuiTextField fieldTextColor;
//    private GuiSlider buttonScale;

//    private GuiSlider sliderMarkerColor;
//    private GuiSlider sliderTextColor;

    @Override
    public void initGui() {
        buttonShowFPS = new GuiButton(0, (width / 2 - 60), (height / 2 - 62), 150, 20, "FPS: " + (KybtCoords.showFPS ? "Enabled" : "Disabled"));
        buttonList.add(buttonShowFPS);

        buttonColoredBiomes = new GuiButton(1, (width / 2 - 60), (height / 2 - 40), 150, 20, "Biome colors: " + (KybtCoords.coloredBiomes ? "Enabled" : "Disabled"));
        buttonList.add(buttonColoredBiomes);

        buttonShowC = new GuiButton(2, (width / 2 - 60), (height / 2 - 18), 150, 20, "C counter: " + (KybtCoords.showC ? "Enabled" : "Disabled"));
        buttonList.add(buttonShowC);

        buttonShowBiomes = new GuiButton(3, (width / 2 - 60), (height / 2 + 4), 150, 20, "Biome: " + (KybtCoords.showBiomes ? "Enabled" : "Disabled"));
        buttonList.add(buttonShowBiomes);

        sliderScale = new GuiSlider(4, (width / 2 - 60), (height / 2 + 26), 150, 20,
                "Blue: ", "", 0.5, 1.0, KybtCoords.scale, false, true);
        buttonList.add(sliderScale);

        surface.drawText("Key color: ", (width / 2 - 60), (height / 2 + 48), Utils.WHITE, 1.0, true);
        buttonKeyColor = new ColorButton(5, (width / 2 - 60) + surface.getFontWidth("Key color: "), (height / 2 + 48), "", 0);
        buttonList.add(buttonKeyColor);

        surface.drawText("Text color: ", (width / 2 - 60), (height / 2 + 69), Utils.WHITE, 1.0, true);
        buttonTextColor = new ColorButton(6, (width / 2 - 60) + surface.getFontWidth("Text color: "), (height / 2 + 69), "", 1);
        buttonList.add(buttonTextColor);

        surface.drawText("Background color: ", (width / 2 - 60), (height / 2 + 90), Utils.WHITE, 1.0, true);
        buttonBackgroundColor = new ColorButton(7, (width / 2 - 60) + surface.getFontWidth("Background color: "), (height / 2 + 90), "", 2);
        buttonList.add(buttonBackgroundColor);
//        fieldKeyColor = new GuiTextField(4, minecraft.fontRendererObj, (width / 2 - 70), (height / 2 + 26), 100, 10);
//        labelList.add(fieldKeyColor);
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
//        minecraft.displayGuiScreen(this);
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
            case 4:
                sliderScale.displayString = ("Scale: " + (KybtCoords.scale = sliderScale.sliderValue));
                break;
            case 5:
                minecraft.displayGuiScreen(null);
                new ColorGUI(buttonKeyColor.id).display();
                break;
            case 6:
                minecraft.displayGuiScreen(null);
                new ColorGUI(buttonTextColor.id).display();
                break;
            case 7:
                minecraft.displayGuiScreen(null);
                new ColorGUI(buttonBackgroundColor.id).display();
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
