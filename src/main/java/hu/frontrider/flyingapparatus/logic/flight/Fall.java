package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Fall {

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public static void handleFall(LivingFallEvent event) {
        if (event.getDistance() > 3) {
            final EntityLivingBase living = event.getEntityLiving();

            final ItemStack stack = living.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            final Item item = stack.getItem();
            if (item instanceof FlyingApparatusItem) {
                if (((FlyingApparatusItem) item).negatesFallDamage()) {
                    if (FlyingApparatusItem.drainFuel(stack, ((FlyingApparatusItem) item).getFallDamageCost(), true)) {
                        event.setDamageMultiplier(0);
                    }
                }
            }
        }
    }
}