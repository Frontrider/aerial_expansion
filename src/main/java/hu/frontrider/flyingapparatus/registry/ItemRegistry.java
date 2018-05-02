package hu.frontrider.flyingapparatus.registry;

import hu.frontrider.flyingapparatus.items.ApparatusHandHeld;
import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import hu.frontrider.flyingapparatus.items.RocketPants;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemRegistry {

    public static final FlyingApparatusItem normal = new FlyingApparatusItem(2000,"apparatus_basic");
    public static final FlyingApparatusItem hardened = new FlyingApparatusItem(5000,"apparatus_hardened",true);
    public static final FlyingApparatusItem reinforced = new FlyingApparatusItem(8000,"apparatus_reinforced",true,.3);
    public static final FlyingApparatusItem signalum = new FlyingApparatusItem(12000,"apparatus_signalum",true,.5);
    public static final FlyingApparatusItem resonant = new FlyingApparatusItem(20000,"apparatus_resonant",true,.6);
    public static final ApparatusHandHeld handheld = new ApparatusHandHeld();
    public static final RocketPants normal_rocket_pants = new RocketPants(2000,"rocket_pants_basic");
    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(normal,hardened,handheld,reinforced,signalum,resonant,normal_rocket_pants);
    }
}
