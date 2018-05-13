package hu.frontrider.aerialexpansion.logic.flight;

import hu.frontrider.aerialexpansion.items.FlyingApparatusItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static hu.frontrider.aerialexpansion.items.ArmorWithFluid.drainFuel;
import static hu.frontrider.aerialexpansion.items.FlyingApparatusItem.ACTIVE_NAME;

@Mod.EventBusSubscriber
public class Controls {


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void control(TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;

        final ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        final Item item = chest.getItem();
        if (item instanceof FlyingApparatusItem) {


            NBTTagCompound nbt = chest.getTagCompound();
            if (nbt != null) {
                if (!nbt.getBoolean(ACTIVE_NAME)) {
                    final GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
                    if (player.isElytraFlying() && gameSettings.keyBindSprint.isKeyDown()) {
                        final Vec3d forward = player.getForward();
                        if (drainFuel(chest, 30)) {
                            player.motionZ = forward.z * 10;
                            player.motionY = forward.y * 10;
                            player.motionX = forward.x * 10;
                        }
                    }
                    return;
                }
            } else {
                return;
            }
            final GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            if (gameSettings.keyBindSprint.isKeyDown()) {

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