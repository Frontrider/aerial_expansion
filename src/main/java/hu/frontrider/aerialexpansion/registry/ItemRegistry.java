package hu.frontrider.aerialexpansion.registry;

import hu.frontrider.aerialexpansion.items.FlyingApparatusItem;
import hu.frontrider.aerialexpansion.items.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static hu.frontrider.aerialexpansion.AerialExpansion.MODID;
import static hu.frontrider.aerialexpansion.logic.TierRegistry.*;

@Mod.EventBusSubscriber
public class ItemRegistry {


    public static final FlyingApparatusItem mmu_basic = new FlyingApparatusItem("apparatus_basic",BASIC,MODID+":textures/armor/basic.png");
    public static final FlyingApparatusItem mmu_hardened = new FlyingApparatusItem( "apparatus_hardened",HARDENED,MODID+":textures/armor/hardened.png");
    public static final FlyingApparatusItem mmu_reinforced = new FlyingApparatusItem( "apparatus_reinforced",REINFORCED,MODID+":textures/armor/reinforced.png");
    public static final FlyingApparatusItem mmu_signalum = new FlyingApparatusItem( "apparatus_signalum", SIGNALUM,MODID+":textures/armor/signalum.png");
    public static final FlyingApparatusItem mmu_resonant = new FlyingApparatusItem( "apparatus_resonant", RESONANT,MODID+":textures/armor/resonant.png");

    public static final Item flux_tube = new Material("flux_tube");

    public static final Item[] items = {
                mmu_basic, mmu_hardened, mmu_reinforced, mmu_signalum, mmu_resonant,
                flux_tube
    };

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(items);
    }


}
