package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static hu.frontrider.flyingapparatus.items.FlyingApparatusItem.ACTIVE_NAME;

@Mod.EventBusSubscriber
public class EquipmenChange {

    @SubscribeEvent
    public static void equipmentChanged(LivingEquipmentChangeEvent event) {
        final Entity player = event.getEntity();
        if (event.getSlot() == EntityEquipmentSlot.CHEST) {
            if (player instanceof EntityPlayerMP) {
                final ItemStack chest = ((EntityPlayerMP) player).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                final Item item = chest.getItem();
                if (item instanceof FlyingApparatusItem) {
                    if (chest.hasTagCompound()) {
                        final NBTTagCompound nbt = chest.getTagCompound();
                        if (nbt.hasKey(ACTIVE_NAME)) {
                            final boolean active = nbt.getBoolean(ACTIVE_NAME);
                            if (active) {
                                if (FlyingApparatusItem.drainFuel(chest, 1, false)) {
                                    if (player.hasNoGravity() == active) {
                                       // player.setNoGravity(!active);
                                    } else {
                                      //  player.setNoGravity(false);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (player.hasNoGravity())
                        player.setNoGravity(false);
                }
            }
        }
    }
}