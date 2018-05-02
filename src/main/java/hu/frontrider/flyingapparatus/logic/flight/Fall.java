package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Fall {


    @SubscribeEvent
    public static void handleFall(LivingFallEvent event) {
        if (event.getDistance() > 3) {
            final EntityLivingBase living = event.getEntityLiving();

            final ItemStack stack = living.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            final Item item = stack.getItem();
            if (item instanceof FlyingApparatusItem) {
                if (((FlyingApparatusItem) item).negatesFallDamage()) {
                    if (FlyingApparatusItem.drainFuel(stack, ((FlyingApparatusItem) item).getFalldamageCost(), true)) {
                        event.setDamageMultiplier(0);
                    }
                }
            }
        }
    }
}