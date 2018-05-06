package hu.frontrider.aerialexpansion;

import hu.frontrider.aerialexpansion.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = AerialExpansion.MODID, name = AerialExpansion.NAME, version = AerialExpansion.VERSION,dependencies = AerialExpansion.DEPENDENCIES)
public class AerialExpansion {

    public static final String MODID = "aerialexpansion";
    public static final String NAME = "Aerial Expansion";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    public static final String DEPENDENCIES = "required-after:thermalfoundation@[2.4.1,2.5.0);";
    public static Logger logger;

    public static final Random random = new Random();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @SidedProxy(clientSide = "hu.frontrider.aerialexpansion.proxy.ClientProxy", serverSide = "hu.frontrider.aerialexpansion.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
