package hu.frontrider.flyingapparatus.items;

import cofh.core.item.ItemMulti;
import cofh.core.util.core.IInitializer;
import hu.frontrider.flyingapparatus.FlyingApparatus;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Material extends ItemMulti implements IInitializer {
    public Material(){
        super(FlyingApparatus.MODID);
        setUnlocalizedName("material");
        setCreativeTab(CreativeTabs.TRANSPORTATION);
    }

    /* IInitializer */
    @Override
    public boolean preInit() {

        ForgeRegistries.ITEMS.register(setRegistryName("materials"));
        FRAME = addOreDictItem(0,"equipmentFrame");
        THRUSTER = addOreDictItem(1,"thrusterComponentBasic");
        FLUX_TUBE = addOreDictItem(2,"fluxTube");
        return true;
    }

    @Override
    public boolean initialize() {
        return true;
    }


    /*REFERENCES*/
    public static ItemStack FRAME;
    public static ItemStack THRUSTER;
    public static ItemStack FLUX_TUBE;
}
