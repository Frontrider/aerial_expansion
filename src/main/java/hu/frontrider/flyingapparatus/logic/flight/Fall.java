package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static hu.frontrider.flyingapparatus.items.fluidItem.ItemWithFluid.drainFuel;


@Mod.EventBusSubscriber
public class Fall {

    @SubscribeEvent
    public static void handleFall(LivingFallEvent event) {
            final EntityLivingBase living = event.getEntityLiving();

            final ItemStack stack = living.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            final Item item = stack.getItem();
            if (item instanceof FlyingApparatusItem) {
                if (((FlyingApparatusItem) item).negatesFallDamage()) {
                    if (drainFuel(stack, (int) (event.getDistance()* ((FlyingApparatusItem) item).getFallDamageCost()), true)) {
                        event.setDamageMultiplier(0);
                    }
                }
            }
        }
}