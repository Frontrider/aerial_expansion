package hu.frontrider.aerialexpansion;

import cofh.core.gui.CreativeTabCore;
import hu.frontrider.aerialexpansion.proxy.CommonProxy;
import hu.frontrider.aerialexpansion.registry.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = AerialExpansion.MODID, name = AerialExpansion.NAME, version = AerialExpansion.VERSION,dependencies = AerialExpansion.DEPENDENCIES)
public class AerialExpansion {

    public static final String MODID = "aerialexpansion";
    public static final String NAME = "Aerial Expansion";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    public static final String DEPENDENCIES = "required-after:thermalfoundation@[2.6.0,4.7);";
    public static Logger logger;

    public static CreativeTabs aerialTab = new CreativeTabCore(MODID) {
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
                                return new ItemStack(ItemRegistry.flux_tube);
			}
		};

    public static final Random random = new Random();

    public static ItemArmor.ArmorMaterial NullArmor;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit();
        NullArmor = EnumHelper.addArmorMaterial("AE:NULL","null",0,new int[]{0,0,0,0},0,SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0F);

    }

    @SidedProxy(clientSide = "hu.frontrider.aerialexpansion.proxy.ClientProxy", serverSide = "hu.frontrider.aerialexpansion.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
