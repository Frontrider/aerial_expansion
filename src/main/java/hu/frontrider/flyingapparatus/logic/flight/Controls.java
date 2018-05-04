package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.ApparatusHandHeld;
import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import hu.frontrider.flyingapparatus.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static hu.frontrider.flyingapparatus.items.FlyingApparatusItem.ACTIVE_NAME;
import static hu.frontrider.flyingapparatus.items.fluidItem.ItemWithFluid.drainFuel;

@Mod.EventBusSubscriber
public class Controls {


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void control(TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if (!player.hasItemInSlot(EntityEquipmentSlot.CHEST)
                && (!player.hasItemInSlot(EntityEquipmentSlot.MAINHAND)
                && !player.hasItemInSlot(EntityEquipmentSlot.OFFHAND)))
            return;

        final ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        final Item item = chest.getItem();

        final ItemStack handItem = player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ApparatusHandHeld ? player.getHeldItem(EnumHand.MAIN_HAND) : player.getHeldItem(EnumHand.OFF_HAND);

        if (item instanceof FlyingApparatusItem) {
            NBTTagCompound nbt = chest.getTagCompound();
            if (nbt != null) {
                if (!nbt.getBoolean(ACTIVE_NAME)) {
                    return;
                }
            } else {
                return;
            }


            if (!(handItem.getItem() instanceof ApparatusHandHeld)) {
                player.motionX = 0;
                player.motionZ = 0;
                return;
            }

            final GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            if (ClientProxy.BOOST.isKeyDown()) {

                if (drainFuel(chest, 100, true)) {
                    player.motionY = 0;
                } else {
                    return;
                }
                final Vec3d forward = player.getForward();
                if (gameSettings.keyBindForward.isKeyDown()) {
                    FlyingApparatusItem.handleBoost((FlyingApparatusItem) item, player, forward.x, 0.0, forward.z);
                }
                if (gameSettings.keyBindBack.isKeyDown()) {
                    FlyingApparatusItem.handleBoost((FlyingApparatusItem) item, player, -forward.x, 0.0, -forward.z);
                }

                if (gameSettings.keyBindLeft.isKeyDown()) {
                    FlyingApparatusItem.handleBoost((FlyingApparatusItem) item, player, forward.z, 0.0, -forward.x);
                }

                if (gameSettings.keyBindRight.isKeyDown()) {
                    FlyingApparatusItem.handleBoost((FlyingApparatusItem) item, player, -forward.z, 0.0, forward.x);
                }
                if (gameSettings.keyBindJump.isKeyDown()) {
                    FlyingApparatusItem.handleBoost((FlyingApparatusItem) item, player, 0.0, 1, 0.0);
                }
                if (gameSettings.keyBindSneak.isKeyDown()) {
                    FlyingApparatusItem.handleBoost((FlyingApparatusItem) item, player, 0.0, -1, 0.0);
                }
            } else {
                if (drainFuel(chest, 1, true)) {
                    player.motionY = 0;
                } else {
                    return;
                }
                if (gameSettings.keyBindJump.isKeyDown()) {
                    FlyingApparatusItem.handleVerticalMovement((FlyingApparatusItem) chest.getItem(), player, true);
                } else {
                    if (gameSettings.keyBindSneak.isKeyDown()) {
                        FlyingApparatusItem.handleVerticalMovement((FlyingApparatusItem) chest.getItem(), player, false);
                    }
                }
            }
        }

    }
}