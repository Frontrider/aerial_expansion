package hu.frontrider.flyingapparatus.items.fluidItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static hu.frontrider.flyingapparatus.items.fluidItem.ItemWithFluid.STORED;

public class FluidItemHelper {

    static boolean drainFuel(ItemStack itemStack, int amount) {
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
