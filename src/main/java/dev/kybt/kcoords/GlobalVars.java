package dev.kybt.kcoords;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface GlobalVars {
    Minecraft minecraft = Minecraft.getMinecraft();
    Logger LOGGER = LogManager.getLogger("kybtCoords");
}
