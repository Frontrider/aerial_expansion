package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
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
            if (stack.getItem() instanceof FlyingApparatusItem) {
                if (((FlyingApparatusItem) stack.getItem()).negatesFallDamage()) {
                    if (FlyingApparatusItem.drainFuel(stack, 300)) {
                        event.setDamageMultiplier(0);
                    }
                }
            }
        }
    }
}