package dev.kybt.kcoords;

import dev.kybt.kcoords.commands.CommandKybtCoords;
import dev.kybt.kcoords.events.SubscribeHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = KybtCoords.NAME, modid = KybtCoords.MODID, version = KybtCoords.VERSION)
public class KybtCoords {

//    High priority tasks (Bare minimum requirements for first release)
    // TO-DO: Create a command for configuring the mod. (Done)
    // TO-DO: Figure out how to render a rectangle with transparency and color. (Done)
    // TO-DO: Figure out how to render text on said rectangle. (Done)
    // TO-DO: Make something that fetches the required data for display. (Done)
    // TO-DO: Add default colors. (Done)

//    Low priority tasks (Useful features to be implemented in future versions)
    // TODO: Make it customizable.
    // TODO: Allow the coordinates box to be moved freely with ease.
    // TODO: Make a gui for on the fly adjustments of the mod.
    // TODO: Implement saving.

//    Mental Notes
    // TODO: Avoid changing code at all costs to avoid false bans and controversy.
    // TODO: Required data: X, Y, and Z coordinates, the cardinal direction of the player, the + and - directions for each coordinate, the C counter, the current biome, and the framerate.

    public static boolean isEnabled = true;

    public static int positionX = 0;
    public static int positionY = 0;

    public static final String NAME = "kybtCoords";
    public static final String MODID = "kcoords";
    public static final String VERSION = "1.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SubscribeHandler());
        ClientCommandHandler.instance.registerCommand(new CommandKybtCoords());
    }
}
