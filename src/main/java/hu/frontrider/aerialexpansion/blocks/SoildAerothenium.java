package hu.frontrider.aerialexpansion.blocks;

import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.aerialexpansion.AerialExpansion;
import hu.frontrider.aerialexpansion.items.FlyingApparatusItem;
import hu.frontrider.aerialexpansion.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static hu.frontrider.aerialexpansion.items.FlyingApparatusItem.ACTIVE_NAME;

public class SoildAerothenium extends Block {

    private static IBlockState decayed = TFFluids.blockFluidAerotheum.getStateFromMeta(2);

    public SoildAerothenium() {
        super(Material.GLASS, MapColor.CLAY);
        setRegistryName(AerialExpansion.MODID, "solid_aerothenium");
        setUnlocalizedName(AerialExpansion.MODID + ".solid_aerothenium");
        setBlockUnbreakable();
        setTickRandomly(true);
        fullBlock = false;
        translucent = true;
        setLightLevel(3.2f);
        setLightOpacity(16);
    }

    @Override
    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }


    @Override
    public void randomTick(World world, BlockPos blockPos, IBlockState blockState, Random random) {
        final EntityPlayer closestPlayer = world.getClosestPlayer(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), 1, true);
        if (closestPlayer != null) {
            final ItemStack slot = closestPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if (slot.getItem() instanceof FlyingApparatusItem) {
                final NBTTagCompound tagCompound = slot.getTagCompound();
                if (tagCompound != null) {
                    if (tagCompound.getBoolean(FlyingApparatusItem.ACTIVE_NAME)) {
                        return;
                    }
                }

            }
        }
        world.setBlockState(blockPos, decayed);
        world.notifyBlockUpdate(blockPos, getDefaultState(), decayed, 0);
    }

    public static void place(BlockPos target, EntityPlayerMP caster, ItemStack stack) {
        if (stack.getItem() instanceof FlyingApparatusItem) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null) {
                if (nbt.hasKey(ACTIVE_NAME)) {
                    if (nbt.getBoolean(ACTIVE_NAME)) {
                        final World world = caster.world;
                        if (world.getBlockState(target).getBlock() == Blocks.AIR) {
                            world.setBlockState(
                                    target,
                                    BlockRegistry.solidAerothenium.getDefaultState(),
                                    1);
                            world.notifyBlockUpdate(target, Blocks.AIR.getDefaultState(), BlockRegistry.solidAerothenium.getDefaultState(), 0);
                        }
                        stack.damageItem(1, caster);
                    }
                }
            }
        }
    }

    public static void dissapate(World world,BlockPos pos,int radius){

    }
}
