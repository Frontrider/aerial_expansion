package hu.frontrider.aerialexpansion.items.fluidItem;

import cofh.api.fluid.IFluidContainerItem;
import cofh.core.init.CoreEnchantments;
import cofh.core.item.IEnchantableItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class ItemWithFluid extends Item implements IFluidContainerItem, IEnchantableItem {

    String fluid;
    int capacity;

    public static String STORED = "stored";

    public ItemWithFluid( Fluid fluid, int capacity) {
        this.fluid = fluid.getName();
        this.capacity = capacity;
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        return new FluidStack(FluidRegistry.getFluid(fluid), getStored(container));
    }

    @Override
    public int getCapacity(ItemStack container) {
        final Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(container);
        if (enchantments.containsKey(CoreEnchantments.holding)) {
            return enchantments.get(CoreEnchantments.holding) * capacity;
        }
        return capacity;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return 1D - ((double) getStored(stack) / (double) getMax(stack));
    }

    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        if (resource.getFluid() == FluidRegistry.getFluid(fluid) && resource.amount > 0) {
            final int space = getEmptySpace(container);
            final int remaining = resource.amount - space;
            if (remaining >= 0) {
                if (doFill) {
                    addAmount(container, space);
                }
            } else {
                if (resource.amount < space) {
                    if (doFill)
                        addAmount(container, resource.amount);
                    return resource.amount;
                } else {
                    if (doFill)
                        addAmount(container, space);
                }
            }
            return space;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        return null;
    }


    @Override
    public boolean canEnchant(ItemStack stack, Enchantment enchantment) {
        return enchantment == CoreEnchantments.holding;
    }

    public static boolean drainFuel(ItemStack itemStack, int amount) {
        return drainFuel(itemStack, amount, true);
    }

    public static boolean drainFuel(ItemStack itemStack, int amount, boolean doDrain) {
        if (itemStack.hasTagCompound()) {
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound.hasKey(STORED)) {
                final int capacity = tagCompound.getInteger(STORED);
                if (capacity > amount) {
                    if (doDrain) {
                        tagCompound.setInteger(STORED, capacity - amount);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    static void addAmount(ItemStack stack, int amount) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbt = stack.getTagCompound();
        if (!nbt.hasKey(STORED)) {
            nbt.setInteger(STORED, amount);
        } else {
            final int capacity = nbt.getInteger(STORED);
            nbt.setInteger(STORED, capacity + amount);
        }
    }

    public static int getStored(ItemStack stack) {

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound().getInteger(STORED);
    }

    static int getEmptySpace(ItemStack stack) {
        return getMax(stack) - getStored(stack);
    }

    public static int getMax(ItemStack stack) {
        return ((ItemWithFluid) stack.getItem()).capacity;
    }


}