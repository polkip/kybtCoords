package dev.kybt.kcoords;

import dev.kybt.kcoords.commands.CommandKybtCoords;
import dev.kybt.kcoords.events.Subscriber;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = "kybtCoords", modid = "kcoords", version = KybtCoords.VERSION)
public class KybtCoords {

    public static boolean isEnabled = true;

    public static int positionX = 0;
    public static int positionY = 0;

    public static double scale = 1.0;

    public static boolean coloredBiomes = true;
    public static boolean showFPS = true;
    public static boolean showC = true;
    public static boolean showBiomes = true;
    public static boolean chroma = false;
//    public static boolean showCoordinates = true;

    public static volatile int labelColor = Utils.BLUE; // Changed by chroma modifier
    public static volatile int textColor = Utils.WHITE; // Changed by chroma modifier

//    public static int xLabel = Utils.BLUE, xValue = Utils.WHITE;
//    public static int yLabel = Utils.BLUE, yValue = Utils.WHITE;
//    public static int zLabel = Utils.BLUE, zValue = Utils.WHITE;

    public static int backgroundColor = Utils.rgba(0, 0, 0, 127);

//    public static final String NAME = "kybtCoords";
//    public static final String MODID = "kcoords";
    public static final String VERSION = "3.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Subscriber());
        ClientCommandHandler.instance.registerCommand(new CommandKybtCoords());
        Utils.loadSettings();
        Utils.getLogger().info("Successfully initialized kybtCoords.");
    }
}
