package hu.frontrider.aerialexpansion.items;

import cofh.core.item.IEnchantableItem;
import cofh.core.util.capabilities.FluidContainerItemWrapper;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.aerialexpansion.client.AnimationHandler;
import hu.frontrider.aerialexpansion.logic.TierRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static hu.frontrider.aerialexpansion.AerialExpansion.MODID;
import static hu.frontrider.aerialexpansion.AerialExpansion.NullArmor;
import static hu.frontrider.aerialexpansion.AerialExpansion.aerialTab;


public class FlyingApparatusItem extends ArmorWithFluid implements IEnchantableItem {

    public static final String ACTIVE_NAME = "active";
    private final TierRegistry.Tier tier;

    public FlyingApparatusItem(String registryName,TierRegistry.Tier tier,String texture) {
        super(TFFluids.fluidAerotheum, tier.capacity, registryName, EntityEquipmentSlot.LEGS);

        this.setCreativeTab(aerialTab);
        this.texture = texture;
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
        if (tier.special) {
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
        if(type != null && type.equals("overlay"))
        {
            return MODID+":textures/armor/animation"+
                    (((ItemArmor)stack.getItem()).getArmorMaterial() == NullArmor? "":"_"+((FlyingApparatusItem)stack.getItem()).getTier().name)+
                    "_"+
                    (stack.getTagCompound().getBoolean(ACTIVE_NAME)?AnimationHandler.ANIMATION_STATE:"off")+
                    ".png"
                    ;
        }
        return texture;
    }
    public boolean negatesFallDamage() {
        return tier.special;
    }

    public double getMotion() {
        return tier.factor;
    }

    public int getFallDamageCost() {
        return tier.cost;
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
        player.motionX = dirX * (float) item.getMotion() * item.tier.factor*3;
        player.motionZ = dirZ * (float) item.getMotion() * item.tier.factor*3;
        player.motionY = dirY * (float) item.getMotion() * item.tier.factor*3;
    }

    @Override
    public boolean getIsRepairable(ItemStack p_getIsRepairable_1_, ItemStack p_getIsRepairable_2_) {
        return super.getIsRepairable(p_getIsRepairable_1_, p_getIsRepairable_2_);
    }

    public TierRegistry.Tier getTier() {
        return tier;
    }

}
