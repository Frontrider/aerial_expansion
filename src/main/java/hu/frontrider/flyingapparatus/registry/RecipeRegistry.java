package hu.frontrider.flyingapparatus.registry;

import cofh.core.util.helpers.RecipeHelper;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.item.ItemStack;

public class RecipeRegistry {

    public static void init(){
        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.flux_tube,1),
                "TGT", "GCG", "TGE",
                'C', ItemMaterial.powerCoilElectrum,
                'G', "blockGlassHardened",
                'T', ItemMaterial.globTar,
                'E', "ingotElectrum");
       /* RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.armor_frame,1),
                " G ","PSP","P P",
                "G", "gearCopper",
                "P", "plateSteel",
                "S", ItemMaterial.redstoneServo
                );*/
    }
}
