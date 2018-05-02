package hu.frontrider.flyingapparatus;

import cofh.CoFHCore;
import cofh.thermalfoundation.ThermalFoundation;
import hu.frontrider.flyingapparatus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = FlyingApparatus.MODID, name = FlyingApparatus.NAME, version = FlyingApparatus.VERSION,dependencies = FlyingApparatus.DEPENDENCIES)
public class FlyingApparatus {

    public static final String MODID = "flyingapparatus";
    public static final String NAME = "Flying Apparatus";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    public static final String DEPENDENCIES = CoFHCore.VERSION_GROUP + ThermalFoundation.VERSION_GROUP;
    public static Logger logger;

    public static final Random random = new Random();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @SidedProxy(clientSide = "hu.frontrider.flyingapparatus.proxy.ClientProxy", serverSide = "hu.frontrider.flyingapparatus.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
