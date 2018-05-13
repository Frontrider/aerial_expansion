package hu.frontrider.aerialexpansion.items;

import cofh.api.fluid.IFluidContainerItem;
import cofh.core.init.CoreEnchantments;
import cofh.core.item.IEnchantableItem;
import hu.frontrider.aerialexpansion.AerialExpansion;
import net.minecraft.block.BlockDispenser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Map;

import static hu.frontrider.aerialexpansion.AerialExpansion.NullArmor;

public class ArmorWithFluid extends ItemArmor implements IFluidContainerItem, IEnchantableItem {

    final EntityEquipmentSlot slot;
    String texture;
    protected String fluid;
    private int capacity;

    public static String STORED = "stored";

    public ArmorWithFluid(String fluid, int capacity, String name, EntityEquipmentSlot slot) {
        super(NullArmor, 0, slot);
        this.slot = slot;
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
        this.fluid = fluid;
        this.capacity = capacity;
        this.maxStackSize = 1;
        this.setRegistryName(AerialExpansion.MODID, name);
        this.setUnlocalizedName(AerialExpansion.MODID + "." + name);
    }

    public ArmorWithFluid(Fluid fluid, int capacity, String name, EntityEquipmentSlot slot) {
        this(fluid.getName(), capacity, name, slot);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        EntityEquipmentSlot slotForItemStack = EntityLiving.getSlotForItemStack(heldItem);
        ItemStack stack = player.getItemStackFromSlot(slotForItemStack);
        if (stack.isEmpty()) {
            player.setItemStackToSlot(slotForItemStack, heldItem.copy());
            heldItem.setCount(0);
            return new ActionResult(EnumActionResult.SUCCESS, heldItem);
        } else {
            return new ActionResult(EnumActionResult.FAIL, heldItem);
        }
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack itemStack) {
        return slot;
    }

    public boolean getIsRepairable(ItemStack p_getIsRepairable_1_, ItemStack p_getIsRepairable_2_) {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return false;
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
        if (resource.getFluid().getName().equals(fluid) && resource.amount > 0) {
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
            return 0;
        }
        return stack.getTagCompound().getInteger(STORED);
    }

    static int getEmptySpace(ItemStack stack) {
        return getMax(stack) - getStored(stack);
    }

    public static int getMax(ItemStack stack) {
        return ((ArmorWithFluid) stack.getItem()).capacity;
    }


}
