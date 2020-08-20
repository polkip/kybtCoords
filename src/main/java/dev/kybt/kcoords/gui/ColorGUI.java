package dev.kybt.kcoords.gui;

import com.google.common.eventbus.Subscribe;
import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.events.SubscribeHandler;
import dev.kybt.kcoords.render.SurfaceHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ColorGUI extends GuiScreen implements GlobalVars {

    private final SubscribeHandler subscribe = new SubscribeHandler();
    private final SurfaceHelper surfaceHelper = SurfaceHelper.getInstance();

    private int red;
    private int green;
    private int blue;
    private int alpha;

    private GuiSlider sliderRed;
    private GuiSlider sliderGreen;
    private GuiSlider sliderBlue;
    private GuiSlider sliderAlpha;

    private GuiButton buttonSave;

    private int currentColor;
    private final int id;

    public ColorGUI(int id) {
        this.id = id;
        switch(id) {
            case 0:
                currentColor = KybtCoords.keyColor;
                break;
            case 1:
                currentColor = KybtCoords.textColor;
                break;
            case 2:
                currentColor = KybtCoords.backgroundColor;
                break;
        }

        red = Utils.toRGBA(currentColor)[0];
        green = Utils.toRGBA(currentColor)[1];
        blue = Utils.toRGBA(currentColor)[2];
        alpha = Utils.toRGBA(currentColor)[3];
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
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    public void initGui() {
        sliderRed = new GuiSlider(0, (width / 2 - 60), (height / 2 - 70), 150, 20,
                "Red: ", "", 0, 255, Utils.toRGBA(currentColor)[0], false, true);
        buttonList.add(sliderRed);

        sliderGreen = new GuiSlider(1, (width / 2 - 60), (height / 2 - 48), 150, 20,
                "Green: ", "", 0, 255, Utils.toRGBA(currentColor)[1], false, true);
        buttonList.add(sliderGreen);

        sliderBlue = new GuiSlider(2, (width / 2 - 60), (height / 2 - 26), 150, 20,
                "Blue: ", "", 0, 255, Utils.toRGBA(currentColor)[2], false, true);
        buttonList.add(sliderBlue);

        sliderAlpha = new GuiSlider(3, (width / 2 - 60), (height / 2 - 4), 150, 20,
                "Alpha: ", "", 0, 255, Utils.toRGBA(currentColor)[3], false, true);
        buttonList.add(sliderAlpha);

        buttonSave = new GuiButton(4, (width / 2 - 60), (height / 2 + 18), 150, 20, "Save");
        buttonList.add(buttonSave);
    }

    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0:
                red = sliderRed.getValueInt();
                updateColor();
                break;
            case 1:
                green = sliderGreen.getValueInt();
                updateColor();
                break;
            case 2:
                blue = sliderBlue.getValueInt();
                updateColor();
                break;
            case 3:
                alpha = sliderAlpha.getValueInt();
                updateColor();
                break;
            case 4:
                updateColor();
//                minecraft.displayGuiScreen(null);
                new ConfigGUI().display();
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        updateColor();
        new ConfigGUI().display();
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void updateColor() {
        currentColor = Utils.rgba(red, green, blue, alpha);

        switch(id) {
            case 0:
                KybtCoords.textColor = currentColor;
                break;
            case 1:
                KybtCoords.keyColor = currentColor;
                break;
            case 2:
                KybtCoords.backgroundColor = currentColor;
                break;
        }
    }
}
