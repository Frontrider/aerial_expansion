package hu.frontrider.flyingapparatus.proxy;

import hu.frontrider.flyingapparatus.FlyingApparatus;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.input.Keyboard;

import static net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding;

public class ClientProxy extends CommonProxy {

    private static final String KEY_BINDING_CATEGORY = "key." + FlyingApparatus.MODID + ".category";

    private static String createKeyBindingName(String name) {

        return "key." +
                FlyingApparatus.MODID +
                "." +
                name +
                ".desc";
    }

    public static KeyBinding STRAFE_LEFT =new KeyBinding(createKeyBindingName("boost_left"),Keyboard.KEY_LEFT,KEY_BINDING_CATEGORY);
    public static KeyBinding STRAFE_RIGHT =new KeyBinding(createKeyBindingName("boost_right"),Keyboard.KEY_RIGHT,KEY_BINDING_CATEGORY);
    public static KeyBinding ENABLE_APPARATUS =new KeyBinding(createKeyBindingName("enable_apparatus"), Keyboard.KEY_F, KEY_BINDING_CATEGORY);


    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
         registerKeyBinding(STRAFE_LEFT);
         registerKeyBinding(STRAFE_RIGHT);
         registerKeyBinding(ENABLE_APPARATUS);
    }
}
