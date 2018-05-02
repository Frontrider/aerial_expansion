package hu.frontrider.flyingapparatus.items;

import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.flyingapparatus.FlyingApparatus;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RocketPants extends ItemWithFluid {

    public RocketPants(int capacity, String registryName) {
        super(TFFluids.fluidFuel, capacity);
        this.maxStackSize = 1;
        this.setMaxDamage(capacity);
        this.setRegistryName(FlyingApparatus.MODID, registryName);
        this.setUnlocalizedName(FlyingApparatus.MODID + "." + registryName);
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }

    public boolean getIsRepairable(ItemStack p_getIsRepairable_1_, ItemStack p_getIsRepairable_2_) {
        return false;
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> infornamtion, ITooltipFlag flag) {
        infornamtion.add(StringHelper.getInfoText("info.flyingapparatus.rocket_pants_description.def"));
        infornamtion.add(StringHelper.getInfoText("info.flyingapparatus.rocket_pants_description.fuel"));
        final int stored = getStored(itemStack);
        final int max = getMax(itemStack);
        infornamtion.add(StringHelper.getNoticeText(StringHelper.localize("info.flyingapparatus.apparatus_info.fuel") + " " + stored + "/" + max));
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
    public EntityEquipmentSlot getEquipmentSlot(ItemStack p_getEquipmentSlot_1_) {
        return EntityEquipmentSlot.LEGS;
    }

    public static void launchHandler(ItemStack stack, EntityPlayer player) {
        final Item item = stack.getItem();

        if (item instanceof FlyingApparatusItem && drainFuel(stack, 1)) {
            final Vec3d playerLookVec = player.getLookVec();
            player.motionY = playerLookVec.y * 6;
            player.motionX = playerLookVec.x * 6;
            player.motionZ = playerLookVec.z * 6;
        }
    }
}
