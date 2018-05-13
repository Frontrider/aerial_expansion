package hu.frontrider.aerialexpansion.proxy;

import hu.frontrider.aerialexpansion.network.ApparatusToggleHandler;
import hu.frontrider.aerialexpansion.network.ApparatusToggleMessage;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import static hu.frontrider.aerialexpansion.AerialExpansion.NETWORK;

public class CommonProxy {

    public void preInit() {
    }

    public void init(FMLInitializationEvent event) {
        NETWORK.registerMessage(ApparatusToggleHandler.class, ApparatusToggleMessage.class, 0, Side.SERVER);
        //RecipeRegistry.init();
    }
}
