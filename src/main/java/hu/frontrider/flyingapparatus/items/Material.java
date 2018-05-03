package hu.frontrider.flyingapparatus.items;

import cofh.core.util.helpers.StringHelper;
import hu.frontrider.flyingapparatus.FlyingApparatus;
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
        setRegistryName(FlyingApparatus.MODID, name) ;
        setUnlocalizedName(getMaterialUnlocalizedName(name));
        setCreativeTab(CreativeTabs.TRANSPORTATION);
    }

    private static String getMaterialUnlocalizedName(String name){
        return FlyingApparatus.MODID + ".material."+name;
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        final String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getResourcePath();
        tooltip.add(StringHelper.getFlavorText(StringHelper.localize("info.flyingapparatus.material."+name+".desc")));
    }
}
