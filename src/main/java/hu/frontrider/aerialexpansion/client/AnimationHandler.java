package hu.frontrider.aerialexpansion.client;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A simple timer to advance the frames of the armor.
 *
 * */
@Mod.EventBusSubscriber
public class AnimationHandler {

    public static int ANIMATION_STATE = 0;
    private static int timer = 0;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onEvent(TickEvent.ClientTickEvent event) {
        timer++;
        if (timer > 10) {
            timer = 0;
            ANIMATION_STATE++;
            if(ANIMATION_STATE>6)
                ANIMATION_STATE =0;
        }
    }
}
