package dev.kybt.kcoords;

import dev.kybt.kcoords.commands.CommandKybtCoords;
import dev.kybt.kcoords.events.SubscribeHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = "kybtCoords", modid = "kcoords", version = "1.2")
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

    public static boolean isEnabled = true;

    public static int positionX = 0;
    public static int positionY = 0;

//    public static boolean outlined = true;
//    public static boolean textShadow = true;
//    public static boolean coloredBiomes = true;

//    public static boolean showFPS = true;
//    public static boolean showC = true;
//    public static boolean showBiomes = true;
//    public static boolean showCoordinates = true;

//    public static int backgroundOpacity = 127;

//    public static final String NAME = "kybtCoords";
//    public static final String MODID = "kcoords";
//    public static final String VERSION = "2.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SubscribeHandler());
        ClientCommandHandler.instance.registerCommand(new CommandKybtCoords());
    }
}
