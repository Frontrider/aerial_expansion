package hu.frontrider.flyingapparatus.items;

import cofh.api.item.IMultiModeItem;
import cofh.core.util.helpers.StringHelper;
import hu.frontrider.flyingapparatus.FlyingApparatus;
import hu.frontrider.flyingapparatus.network.ApparatusToggleMessage;
import hu.frontrider.flyingapparatus.proxy.ClientProxy;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static hu.frontrider.flyingapparatus.FlyingApparatus.NETWORK;

public class ApparatusHandHeld extends Item implements IMultiModeItem {

    public ApparatusHandHeld() {
        setRegistryName(FlyingApparatus.MODID, "apparatus_handheld");
        this.setUnlocalizedName(FlyingApparatus.MODID + "." + "apparatus_handheld");
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
        this.maxStackSize = 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        final ItemStack heldItem = player.getHeldItem(hand);
        if (player.hasItemInSlot(EntityEquipmentSlot.LEGS)) {
            if (getMode(heldItem) > 0) {
                final ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
                if (stack.getItem() instanceof RocketPants)
                    RocketPants.launchHandler(stack, player);
            }
        }
        return ActionResult.newResult(EnumActionResult.FAIL, heldItem);
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> list, ITooltipFlag flag) {

        list.add(
                StringHelper.getInfoText(
                        StringHelper.localize("info.flyingapparatus.apparatus_handheld.controls_levitators")));

        if (StringHelper.isShiftKeyDown()) {
            list.add(
                    StringHelper.getInfoText(
                            StringHelper.localizeFormat("info.flyingapparatus.apparatus_handheld.boost",
                                    StringHelper.getKeyName(ClientProxy.BOOST.getKeyCode()))));
            list.add(
                    StringHelper.getFlavorText(
                            StringHelper.localize("info.flyingapparatus.apparatus_handheld.holdit")));
        } else {
            list.add(StringHelper.shiftForDetails());
        }
    }

    @Override
    public int getNumModes(ItemStack stack) {
        return 2;
    }

    @Override
    public void onModeChange(EntityPlayer player, ItemStack stack) {
        NETWORK.sendToServer(new ApparatusToggleMessage());
    }
}
