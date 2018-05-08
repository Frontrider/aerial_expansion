package hu.frontrider.aerialexpansion.items.fluidItem;

import hu.frontrider.aerialexpansion.AerialExpansion;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;

public class ArmorWithFluid extends ItemWithFluid {

    private final EntityEquipmentSlot slot;
    private String texture;

    public ArmorWithFluid(Fluid fluid, int capacity, String name, EntityEquipmentSlot slot) {
        super(fluid, capacity,name);
        this.slot = slot;
        final String PATH_ARMOR = AerialExpansion.MODID + ":textures/armor/";
        this.texture = PATH_ARMOR + name + ".png";
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);

    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (slot == this.slot) {
            return texture;
        }

        return null;
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
}
