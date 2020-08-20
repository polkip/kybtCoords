package dev.kybt.kcoords.gui;

import dev.kybt.kcoords.GlobalVars;
import dev.kybt.kcoords.KybtCoords;
import dev.kybt.kcoords.Utils;
import dev.kybt.kcoords.events.SubscribeHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

public class PositionGUI extends GuiScreen implements GlobalVars {
    private boolean drag = false;
    private int lastX;
    private int lastY;

    private final SubscribeHandler subscribe = new SubscribeHandler();

    public void display() {
//        MinecraftForge.EVENT_BUS.register(this);
        minecraft.displayGuiScreen(this);
    }

//    @SubscribeEvent
//    public void onTick(TickEvent.ClientTickEvent event) {
//        MinecraftForge.EVENT_BUS.unregister(this);
//        minecraft.displayGuiScreen(this);
//    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        subscribe.renderData();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void keyTyped(char c, int key) {
        if(key == 1) minecraft.displayGuiScreen(null);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int minX = KybtCoords.positionX;
        int minY = KybtCoords.positionY;
        int maxX = SubscribeHandler.right;
        int maxY = SubscribeHandler.bottom;

        if((mouseX >= minX) && (mouseX <= maxX) && (mouseY >= minY) && (mouseY <= maxY)) {
            drag = true;
            lastX = mouseX;
            lastY = mouseY;
        }

        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if(state == 0 && drag) drag = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(drag) {
            subscribe.updateRenders();

            KybtCoords.positionX += mouseX - lastX;
            KybtCoords.positionY += mouseY - lastY;

            lastX = mouseX;
            lastY = mouseY;
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void onGuiClosed() {
        Utils.saveSettings();
        super.onGuiClosed();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
