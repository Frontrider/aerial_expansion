package hu.frontrider.flyingapparatus.proxy;

import hu.frontrider.flyingapparatus.network.ApparatusToggleHandler;
import hu.frontrider.flyingapparatus.network.ApparatusToggleMessage;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import static hu.frontrider.flyingapparatus.FlyingApparatus.NETWORK;

public class CommonProxy {

    public void init(FMLInitializationEvent event){
        NETWORK.registerMessage(ApparatusToggleHandler.class, ApparatusToggleMessage.class, 0, Side.SERVER);
    }
}
