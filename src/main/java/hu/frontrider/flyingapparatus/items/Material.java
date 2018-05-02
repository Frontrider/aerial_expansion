package hu.frontrider.flyingapparatus.items;

import cofh.core.item.ItemMulti;
import hu.frontrider.flyingapparatus.FlyingApparatus;
import net.minecraft.item.Item;

public class Material extends ItemMulti {
    public Material(){
        super(FlyingApparatus.MODID);
    }


    /*REFERENCES*/
    public static Item FRAME;
    public static Item THRUSTER;
    public static Item FLUX_TUBE;
}
