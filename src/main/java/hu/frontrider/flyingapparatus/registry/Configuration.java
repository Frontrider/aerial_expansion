package hu.frontrider.flyingapparatus.registry;

import hu.frontrider.flyingapparatus.FlyingApparatus;
import net.minecraftforge.common.config.Config;

@Config(modid = FlyingApparatus.MODID)
public class Configuration {

    @Config.Comment({
            "if set to true, it will place item into the unified COHF creative tabs"
    })
    public static boolean useThermalTabs=false;


}
