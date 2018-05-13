package hu.frontrider.aerialexpansion.client;

import hu.frontrider.aerialexpansion.network.ApparatusToggleMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static hu.frontrider.aerialexpansion.AerialExpansion.NETWORK;
import static hu.frontrider.aerialexpansion.proxy.ClientProxy.TOGGLE;

@Mod.EventBusSubscriber
public class KeyBoardListener {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(InputEvent.KeyInputEvent event) {
        if(TOGGLE.isKeyDown())
           NETWORK.sendToServer(new ApparatusToggleMessage());
    }
}
