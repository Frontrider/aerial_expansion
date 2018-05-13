package hu.frontrider.aerialexpansion.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static hu.frontrider.aerialexpansion.AerialExpansion.random;
import static hu.frontrider.aerialexpansion.items.ArmorWithFluid.drainFuel;
import static hu.frontrider.aerialexpansion.items.FlyingApparatusItem.ACTIVE_NAME;

@Mod.EventBusSubscriber
public class ApparatusParticleHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void emitParticles(TickEvent.PlayerTickEvent tickEvent) {

        final EntityPlayer player = tickEvent.player;
        final ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        final NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            if (nbt.hasKey(ACTIVE_NAME)) {
                if (nbt.getBoolean(ACTIVE_NAME)) {
                    if (drainFuel(stack, 1, false)) {
                        final World world = player.world;
                        emitDirection(world,
                                player.posX,
                                player.posY + 1,
                                player.posZ,
                                random.nextDouble(),
                                -1 + random.nextDouble(),
                                random.nextDouble() * -1 + random.nextDouble());
                    }
                }
            }
        }
    }

    private static void emitDirection(World world, double posX, double posY, double posZ, Vec3d direction) {
        emitDirection(world, posX, posY, posZ, direction.x, direction.y, direction.z);
    }

    private static void emitDirection(World world, double posX, double posY, double posZ, double directionX, double directionY, double directionZ) {
        if (random.nextInt(16) > 6)
            world.spawnParticle(EnumParticleTypes.CLOUD,
                    posX,
                    posY,
                    posZ,
                    directionX,
                    directionY,
                    directionZ, 1);
    }

}
