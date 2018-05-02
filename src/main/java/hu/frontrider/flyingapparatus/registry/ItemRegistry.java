package hu.frontrider.flyingapparatus.registry;

import hu.frontrider.flyingapparatus.items.ApparatusHandHeld;
import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import hu.frontrider.flyingapparatus.items.Material;
import hu.frontrider.flyingapparatus.items.RocketPants;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemRegistry {

    public static final FlyingApparatusItem mmu_normal = new FlyingApparatusItem(2000, "apparatus_basic");
    public static final FlyingApparatusItem mmu_hardened = new FlyingApparatusItem(5000, "apparatus_hardened", true, 400);
    public static final FlyingApparatusItem mmu_reinforced = new FlyingApparatusItem(8000, "apparatus_reinforced", true, .3, 300);
    public static final FlyingApparatusItem mmu_signalum = new FlyingApparatusItem(9000, "apparatus_signalum", true, .5, 300);
    public static final FlyingApparatusItem mmu_resonant = new FlyingApparatusItem(10000, "apparatus_resonant", true, .6, 200);

    public static final ApparatusHandHeld handheld = new ApparatusHandHeld();
    public static final RocketPants normal_rocket_pants = new RocketPants(2000, "rocket_pants_basic");
    public static final Material materials = new Material();

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                mmu_normal, mmu_hardened, mmu_reinforced, mmu_signalum, mmu_resonant,
                normal_rocket_pants,
                handheld);
        materials.preInit();
    }
}
