package hu.frontrider.aerialexpansion.registry;

import hu.frontrider.aerialexpansion.blocks.SoildAerothenium;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlockRegistry {

    public static final Block solidAerothenium = new SoildAerothenium();

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(
        //solidAerothenium
        );
    }
}
