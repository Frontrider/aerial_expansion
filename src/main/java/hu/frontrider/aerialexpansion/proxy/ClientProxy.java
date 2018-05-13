package hu.frontrider.aerialexpansion.proxy;

import hu.frontrider.aerialexpansion.AerialExpansion;
import hu.frontrider.aerialexpansion.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

import static net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding;

public class ClientProxy extends CommonProxy {

    private static final String KEY_BINDING_CATEGORY = "key." + AerialExpansion.MODID + ".category";

    private static String createKeyBindingName(String name) {

        return "key." +
                AerialExpansion.MODID +
                "." +
                name +
                ".desc";
    }

    public static KeyBinding BOOST = new KeyBinding(createKeyBindingName("boost"), Keyboard.KEY_LEFT, KEY_BINDING_CATEGORY);
    public static KeyBinding TOGGLE = new KeyBinding(createKeyBindingName("toggle"),Keyboard.KEY_RIGHT,KEY_BINDING_CATEGORY);

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        registerKeyBinding(BOOST);
        registerKeyBinding(TOGGLE);
        initClient(Minecraft.getMinecraft().getRenderItem().getItemModelMesher());
    }

    @SideOnly(Side.CLIENT)
    public static void initClient(ItemModelMesher mesher) {
        for(Item item: ItemRegistry.items) {
            ModelResourceLocation model = new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()).toString(), "inventory");
            ModelLoader.registerItemVariants(item, model);
            mesher.register(item, 0, model);
        }

    }
}
