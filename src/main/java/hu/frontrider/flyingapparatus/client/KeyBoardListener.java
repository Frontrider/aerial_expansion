package hu.frontrider.flyingapparatus.client;

import hu.frontrider.flyingapparatus.network.ApparatusToggleMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static hu.frontrider.flyingapparatus.FlyingApparatus.NETWORK;
import static hu.frontrider.flyingapparatus.proxy.ClientProxy.ENABLE_APPARATUS;

@Mod.EventBusSubscriber
public class KeyBoardListener {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(InputEvent.KeyInputEvent event) {

        if (ENABLE_APPARATUS.isPressed()) {
            NETWORK.sendToServer(new ApparatusToggleMessage());
        }

    }
}
