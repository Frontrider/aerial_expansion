package hu.frontrider.flyingapparatus.items;

import cofh.core.item.IEnchantableItem;
import cofh.core.util.capabilities.FluidContainerItemWrapper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.flyingapparatus.FlyingApparatus;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

public class FlyingApparatusItem extends ItemWithFluid implements IEnchantableItem {

    public static final String ACTIVE_NAME = "active";

    private final boolean negatesFallDamage;
    private final double movementFactor;
    private final int fallDamageCost;

    public FlyingApparatusItem(int capacity, String registryName) {
        this(capacity, registryName, false, .1,500);
    }
    
    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage,int fallDamageCost) {
        this(capacity, registryName, negatesFallDamage, .1,fallDamageCost);
    }

    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage, double movementFactor,int fallDamageCost) {
        super(TFFluids.fluidAerotheum, capacity);
        this.negatesFallDamage = negatesFallDamage;
        this.maxStackSize = 1;
        this.movementFactor = movementFactor;
        this.setMaxDamage(capacity);
        this.setRegistryName(FlyingApparatus.MODID, registryName);
        this.setUnlocalizedName(FlyingApparatus.MODID + "." + registryName);
        this.fallDamageCost = fallDamageCost;

        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    public boolean getIsRepairable(ItemStack p_getIsRepairable_1_, ItemStack p_getIsRepairable_2_) {
        return p_getIsRepairable_2_.getItem() == Items.LEATHER;
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

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> infornamtion, ITooltipFlag flag) {
        infornamtion.add(StringHelper.getInfoText("info.flyingapparatus.apparatus_description.def"));
        infornamtion.add(StringHelper.getInfoText("info.flyingapparatus.apparatus_description.fuel"));
        if (negatesFallDamage) {
            StringHelper.getNoticeText(StringHelper.localize("info.flyingapparatus.apparatus_description.fall_damage"));
        }
        final int stored = getStored(itemStack);
        final int max = getMax(itemStack);
        infornamtion.add(StringHelper.getNoticeText(StringHelper.localize("info.flyingapparatus.apparatus_info.fuel") + " " + stored + "/" + max));
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack p_getEquipmentSlot_1_) {
        return EntityEquipmentSlot.CHEST;
    }

    public boolean negatesFallDamage() {
        return negatesFallDamage;
    }

    public double getMotion() {
        return movementFactor;
    }

    public int getFallDamageCost()
    {
        return fallDamageCost;
    }

    /* CAPABILITIES */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidContainerItemWrapper(stack, this, true, false);
    }

    @Override
    public boolean canEnchant(ItemStack stack, Enchantment enchantment) {
        return super.canEnchant(stack, enchantment);
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity player, int itemSlot, boolean isSelected) {
        if (itemSlot == EntityEquipmentSlot.CHEST.getSlotIndex()) {
            if (player instanceof EntityPlayerMP) {
                final Item item = stack.getItem();
                if (item instanceof FlyingApparatusItem) {
                    if (stack.hasTagCompound()) {
                        final NBTTagCompound nbt = stack.getTagCompound();
                        if (nbt.hasKey(ACTIVE_NAME)) {
                            final boolean active = nbt.getBoolean(ACTIVE_NAME);
                            if (active) {
                                if (FlyingApparatusItem.drainFuel(stack, 1, false)) {
                                    if (!player.hasNoGravity()) {
                                        player.setNoGravity(true);
                                    }
                                } else {
                                    player.setNoGravity(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void handleVerticalMovement(ItemStack stack, EntityPlayer player, boolean upwards) {
        final Item item = stack.getItem();
        if (item instanceof FlyingApparatusItem &&  drainFuel(stack,1, false)) {
            if (upwards) {
                player.motionY = ((FlyingApparatusItem) item).getMotion();
            } else {
                player.motionY = -((FlyingApparatusItem) item).getMotion();
            }
        }

    }
    public static void handleStrafe(ItemStack stack, EntityPlayer player, boolean left){
        final Item item = stack.getItem();

        if (item instanceof FlyingApparatusItem &&  drainFuel(stack,1, false)) {
            if (left) {
                player.moveStrafing = (float) ((FlyingApparatusItem) item).getMotion();
            } else {
                player.moveStrafing = (float) -((FlyingApparatusItem) item).getMotion();
            }
        }
    }


}