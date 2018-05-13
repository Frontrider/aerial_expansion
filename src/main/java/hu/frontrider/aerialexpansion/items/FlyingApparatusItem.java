package hu.frontrider.aerialexpansion.items;

import cofh.core.item.IEnchantableItem;
import cofh.core.util.capabilities.FluidContainerItemWrapper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.aerialexpansion.AerialExpansion;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

import static hu.frontrider.aerialexpansion.AerialExpansion.aerialTab;
import static hu.frontrider.aerialexpansion.client.AnimationHandler.ANIMATION_STATE;


public class FlyingApparatusItem extends ArmorWithFluid implements IEnchantableItem, IItemColor {

    public static final String ACTIVE_NAME = "active";

    private final boolean negatesFallDamage;
    private final double movementFactor;
    private final int fallDamageCost;
    private int tier = 0;

    private static final int[] TIERS = {
            8289918, 13095368, 2719615, 14041361, 2719615
    };

    private static final int[] ARMOR_COLORS = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    public FlyingApparatusItem(int capacity, String registryName) {
        this(capacity, registryName, false, 500, .1, 0);
    }

    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage, int fallDamageCost) {
        this(capacity, registryName, negatesFallDamage, fallDamageCost, .1, 0);
    }

    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage, int fallDamageCost, int tier) {
        this(capacity, registryName, negatesFallDamage, fallDamageCost, .1, tier);
    }

    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage, int fallDamageCost, double movementFactor, int tier) {
        super(TFFluids.fluidAerotheum, capacity, registryName, EntityEquipmentSlot.LEGS);
        this.negatesFallDamage = negatesFallDamage;
        this.movementFactor = movementFactor;
        this.fallDamageCost = fallDamageCost;
        this.setCreativeTab(aerialTab);
        this.texture = AerialExpansion.MODID + ":textures/armor/" + registryName;
        this.tier = tier;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> information, ITooltipFlag flag) {
        information.add(StringHelper.getInfoText("info.aerialexpansion.apparatus_description.def"));
        information.add(StringHelper.getInfoText("info.aerialexpansion.apparatus_description.fuel"));
        if (negatesFallDamage) {
            StringHelper.getNoticeText(StringHelper.localize("info.aerialexpansion.apparatus_description.fall_damage"));
        }
        final int stored = getStored(itemStack);
        final int max = getMax(itemStack);

        if (itemStack.hasTagCompound()) {
            final NBTTagCompound nbt = itemStack.getTagCompound();
            if (nbt.hasKey("armor")) {
                final byte armor = nbt.getByte("armor");
                StringHelper.getInfoText(
                        StringHelper.localize("info.aerialexpansion.apparatus_description.armour")
                                + ":"
                                + StringHelper.localize("info.aerialexpansion.material.mat_" + armor + ".name"));

            }
        }
        information.add(StringHelper.getNoticeText(StringHelper.localize("info.aerialexpansion.apparatus_info.fuel") + " " + stored + "/" + max));
        information.add(StringHelper.getInfoText(StringHelper.localize("info.aerialexpansion.apparatus_description.tm")));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (slot == this.slot) {
            if (stack.hasTagCompound()) {
                final NBTTagCompound nbt = stack.getTagCompound();
                if (nbt.hasKey(ACTIVE_NAME) && nbt.hasKey(STORED)) {
                    if (nbt.getBoolean(ACTIVE_NAME) && nbt.getInteger(STORED)>0)
                        return texture + "_" + ANIMATION_STATE + ".png";
                }
            }
            return texture + "_off.png";
        }
        return null;
    }

    public boolean negatesFallDamage() {
        return negatesFallDamage;
    }

    public double getMotion() {
        return movementFactor;
    }

    public int getFallDamageCost() {
        return fallDamageCost;
    }

    /* CAPABILITIES */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new FluidContainerItemWrapper(stack, this, true, false);
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    public static void handleVerticalMovement(FlyingApparatusItem item, EntityPlayer player, boolean upwards) {
        if (upwards) {
            player.motionY = item.getMotion();
        } else {
            player.motionY = -item.getMotion();
        }
    }

    public static void handleBoost(FlyingApparatusItem item, EntityPlayer player, double dirX, double dirY, double dirZ) {
        player.motionX = dirX * (float) item.getMotion() * 3;
        player.motionZ = dirZ * (float) item.getMotion() * 3;
        player.motionY = dirY * (float) item.getMotion() * 3;
    }

    @Override
    public int colorMultiplier(ItemStack itemStack, int tintIndex) {
        switch (tintIndex) {
            case 0:
                return Color.WHITE.getRGB();
            case 1: {
                if (itemStack.hasTagCompound()) {
                    return TIERS[tier];
                }
            }
            default: {
                return Color.BLACK.getRGB();
            }
        }
    }

    @Override
    public boolean hasColor(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack p_getIsRepairable_1_, ItemStack p_getIsRepairable_2_) {
        return super.getIsRepairable(p_getIsRepairable_1_, p_getIsRepairable_2_);
    }
}
