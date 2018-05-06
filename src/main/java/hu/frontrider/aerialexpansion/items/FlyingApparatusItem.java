package hu.frontrider.aerialexpansion.items;

import cofh.core.item.IEnchantableItem;
import cofh.core.util.capabilities.FluidContainerItemWrapper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.aerialexpansion.items.fluidItem.ArmorWithFluid;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;


public class FlyingApparatusItem extends ArmorWithFluid implements IEnchantableItem {

    public static final String ACTIVE_NAME = "active";

    private final boolean negatesFallDamage;
    private final double movementFactor;
    private final int fallDamageCost;

    public FlyingApparatusItem(int capacity, String registryName) {
        this(capacity, registryName, false, 500, .1);
    }

    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage, int fallDamageCost) {
        this(capacity, registryName, negatesFallDamage, fallDamageCost, .1);
    }

    public FlyingApparatusItem(int capacity, String registryName, boolean negatesFallDamage, int fallDamageCost, double movementFactor) {
        super(TFFluids.fluidAerotheum, capacity, registryName, EntityEquipmentSlot.CHEST);
        this.negatesFallDamage = negatesFallDamage;
        this.movementFactor = movementFactor;
        this.fallDamageCost = fallDamageCost;
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> infornamtion, ITooltipFlag flag) {
        infornamtion.add(StringHelper.getInfoText("info.aerialexpansion.apparatus_description.def"));
        infornamtion.add(StringHelper.getInfoText("info.aerialexpansion.apparatus_description.fuel"));
        if (negatesFallDamage) {
            StringHelper.getNoticeText(StringHelper.localize("info.aerialexpansion.apparatus_description.fall_damage"));
        }
        final int stored = getStored(itemStack);
        final int max = getMax(itemStack);
        infornamtion.add(StringHelper.getNoticeText(StringHelper.localize("info.aerialexpansion.apparatus_info.fuel") + " " + stored + "/" + max));
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
        player.motionX = dirX * (float) item.getMotion()*3;
        player.motionZ = dirZ * (float) item.getMotion()*3;
        player.motionY = dirY * (float) item.getMotion()*3;
    }
}
