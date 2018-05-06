package hu.frontrider.aerialexpansion.items;

import cofh.core.util.helpers.StringHelper;
import hu.frontrider.aerialexpansion.AerialExpansion;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class Material extends Item {


    public Material(String name){
        setRegistryName(AerialExpansion.MODID, name) ;
        setUnlocalizedName(getMaterialUnlocalizedName(name));
        setCreativeTab(CreativeTabs.TRANSPORTATION);
    }

    private static String getMaterialUnlocalizedName(String name){
        return AerialExpansion.MODID + ".material."+name;
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        final String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getResourcePath();
        tooltip.add(StringHelper.getInfoText(StringHelper.localize("info.aerialexpansion.material."+name+".desc")));
    }
}
