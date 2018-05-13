package hu.frontrider.aerialexpansion.registry;

import hu.frontrider.aerialexpansion.AerialExpansion;
import net.minecraftforge.common.config.Config;

@Config(modid = AerialExpansion.MODID)
public class Configuration {

    @Config.Comment({
            "if set to false items will use the vanilla tabs."
    })
    public static boolean useCustomTabs=true;

}
