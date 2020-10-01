package dev.kybt.kcoords.gui;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.events.Subscriber;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;

public class ColorGUI extends GuiScreen implements GlobalVars {

    private final Subscriber subscribe = new Subscriber();
//    private final SurfaceHelper surfaceHelper = SurfaceHelper.getInstance();

    private GuiTextField textFieldHex;

    private GuiSlider sliderRed;
    private GuiSlider sliderGreen;
    private GuiSlider sliderBlue;
    private GuiSlider sliderAlpha;

    private GuiButton buttonSave;

//    private boolean changedHex = false;

    private int currentColor = 0;
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
        textFieldHex.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    public void updateScreen() {
        super.updateScreen();
        updateColor();
        textFieldHex.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        textFieldHex.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textFieldHex.textboxKeyTyped(typedChar, keyCode);
        updateHex();
    }

    @Override
    public void initGui() {
        textFieldHex = new GuiTextField(0, minecraft.fontRendererObj,
                (width / 2 - 60), (height / 2 - 92), 150, 20);
        textFieldHex.setMaxStringLength(9);
        textFieldHex.setText("#" + Hex.encodeHexString(Utils.toRGBAByte(currentColor)));


        sliderRed = new GuiSlider(1, (width / 2 - 60), (height / 2 - 70), 150, 20,
                "Red: ", "", 0, 255, Utils.toRGBA(currentColor)[0], false, true);
        buttonList.add(sliderRed);

        sliderGreen = new GuiSlider(2, (width / 2 - 60), (height / 2 - 48), 150, 20,
                "Green: ", "", 0, 255, Utils.toRGBA(currentColor)[1], false, true);
        buttonList.add(sliderGreen);

        sliderBlue = new GuiSlider(3, (width / 2 - 60), (height / 2 - 26), 150, 20,
                "Blue: ", "", 0, 255, Utils.toRGBA(currentColor)[2], false, true);
        buttonList.add(sliderBlue);

        sliderAlpha = new GuiSlider(4, (width / 2 - 60), (height / 2 - 4), 150, 20,
                "Alpha: ", "", 0, 255, Utils.toRGBA(currentColor)[3], false, true);
        buttonList.add(sliderAlpha);

        buttonSave = new GuiButton(5, (width / 2 - 60), (height / 2 + 18), 150, 20, "Save");
        buttonList.add(buttonSave);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 5) {
            new ConfigGUI().display();
        }
    }

    @Override
    public void onGuiClosed() {
        Utils.saveSettings();
        updateColor();
        new ConfigGUI().display();
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void updateColor() {
        currentColor = Utils.rgba(sliderRed.getValueInt(), sliderGreen.getValueInt(), sliderBlue.getValueInt(), sliderAlpha.getValueInt());
        setColor();
    }

    private void updateHex() {
        if(textFieldHex.getText().length() != 7 || textFieldHex.getText().length() != 9) {
            textFieldHex.setTextColor(Utils.rgba(255, 0, 0, 255));
            return;
        } else
            textFieldHex.setTextColor(Utils.WHITE);

        currentColor = Utils.parseHexadecimal(textFieldHex.getText());
        sliderRed.sliderValue = Utils.toRGBA(currentColor)[0];
        sliderGreen.sliderValue = Utils.toRGBA(currentColor)[1];
        sliderBlue.sliderValue = Utils.toRGBA(currentColor)[2];
        sliderAlpha.sliderValue = Utils.toRGBA(currentColor)[3];

        // Debug
        GlobalVars.LOGGER.debug("Decoded hex color: " + currentColor);
        GlobalVars.LOGGER.debug("Slider red: " + sliderRed.sliderValue);
        GlobalVars.LOGGER.debug("Slider green: " + sliderGreen.sliderValue);
        GlobalVars.LOGGER.debug("Slider blue: " + sliderBlue.sliderValue);
        GlobalVars.LOGGER.debug("Slider alpha: " + sliderAlpha.sliderValue);

        setColor();
    }

    private void setColor() {
        switch(id) {
            case 0:
                KybtCoords.keyColor = currentColor;
                break;
            case 1:
                KybtCoords.textColor = currentColor;
                break;
            case 2:
                KybtCoords.backgroundColor = currentColor;
                break;
        }
    }
}
