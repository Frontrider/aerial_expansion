package hu.frontrider.aerialexpansion.items;

import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.init.TFFluids;
import hu.frontrider.aerialexpansion.items.fluidItem.ArmorWithFluid;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


public class RocketPants extends ArmorWithFluid {

    public RocketPants(int capacity, String registryName) {
        super(TFFluids.fluidAerotheum, capacity,registryName,EntityEquipmentSlot.LEGS);

        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
    }


    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> infornamtion, ITooltipFlag flag) {
        infornamtion.add(StringHelper.getInfoText("info.aerialexpansion.rocket_pants_description.def"));
        infornamtion.add(StringHelper.getInfoText("info.aerialexpansion.rocket_pants_description.fuel"));
        final int stored = getStored(itemStack);
        final int max = getMax(itemStack);
        infornamtion.add(StringHelper.getNoticeText(StringHelper.localize("info.aerialexpansion.apparatus_info.fuel") + " " + stored + "/" + max));
    }

    public static void launchHandler(ItemStack stack, EntityPlayer player) {
        final Item item = stack.getItem();

        if (item instanceof FlyingApparatusItem && drainFuel(stack, 1, false)) {
            final Vec3d playerLookVec = player.getLookVec().scale(60);
            player.motionY = playerLookVec.y;
            player.motionX = playerLookVec.x;
            player.motionZ = playerLookVec.z;
        }
    }

}
