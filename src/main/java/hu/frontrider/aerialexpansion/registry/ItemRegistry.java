package hu.frontrider.aerialexpansion.registry;

import hu.frontrider.aerialexpansion.items.ApparatusHandHeld;
import hu.frontrider.aerialexpansion.items.FlyingApparatusItem;
import hu.frontrider.aerialexpansion.items.Material;
import hu.frontrider.aerialexpansion.items.RocketPants;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemRegistry {


    public static final FlyingApparatusItem mmu_basic = new FlyingApparatusItem(2000, "apparatus_basic");
    public static final FlyingApparatusItem mmu_hardened = new FlyingApparatusItem(5000, "apparatus_hardened", true, 400);
    public static final FlyingApparatusItem mmu_reinforced = new FlyingApparatusItem(8000, "apparatus_reinforced", true, 200, .3);
    public static final FlyingApparatusItem mmu_signalum = new FlyingApparatusItem(12000, "apparatus_signalum", true, 100, .5);
    public static final FlyingApparatusItem mmu_resonant = new FlyingApparatusItem(20000, "apparatus_resonant", true, 10, .6);

    public static final ApparatusHandHeld handheld = new ApparatusHandHeld();
    public static final RocketPants normal_rocket_pants = new RocketPants(2000, "rocket_pants_basic");

    public static final Item flux_tube = new Material("flux_tube");
    public static final Item armor_frame = new Material("equipment_frame");

    public static final Item thruster_basic = new Material("thruster_component_basic");
    public static final Item thruster_hardened = new Material("thruster_component_hardened");
    public static final Item thruster_reinforced = new Material("thruster_component_reinforced");
    public static final Item thruster_signalum = new Material("thruster_component_signalum");
    public static final Item thruster_resonant = new Material("thruster_component_resonant");

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                mmu_basic, mmu_hardened, mmu_reinforced, mmu_signalum, mmu_resonant,
               //filling isn't working, and I have no idea why. Revisiting it when it does.
               //normal_rocket_pants,
                handheld,
                flux_tube, armor_frame,
                thruster_basic,thruster_hardened,thruster_reinforced,thruster_resonant,thruster_signalum);
    }


}
