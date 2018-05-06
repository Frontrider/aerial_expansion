package hu.frontrider.aerialexpansion.registry;

import hu.frontrider.aerialexpansion.AerialExpansion;
import net.minecraftforge.common.config.Config;

@Config(modid = AerialExpansion.MODID)
public class Configuration {

    @Config.Comment({
            "if set to true, it will place item into the unified COHF creative tabs"
    })
    public static boolean useThermalTabs=false;


}
