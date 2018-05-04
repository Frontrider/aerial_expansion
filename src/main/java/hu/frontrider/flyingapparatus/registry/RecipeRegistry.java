package hu.frontrider.flyingapparatus.registry;

import cofh.core.util.helpers.RecipeHelper;
import cofh.thermalfoundation.init.TFItems;
import cofh.thermalfoundation.item.ItemMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeRegistry {

    public static void init() {

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.flux_tube),
                "TG ", "GCG", " GE",
                'C', ItemMaterial.powerCoilElectrum,
                'G', "blockGlassHardened",
                'T', ItemMaterial.globTar,
                'E', "ingotElectrum");

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.armor_frame),
                "PGP", " S ", "P P",
                'G', "gearCopper",
                'P', "plateSteel",
                'S', ItemMaterial.redstoneServo
        );
        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.thruster_hardened),
                " G ", "DSD", "P P",
                'G', "gearInvar",
                'P', "plateInvar",
                'S', ItemMaterial.powerCoilSilver,
                'D', "dustTin"
        );
        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.thruster_basic),
                " G ", "P P", "P P",
                'G', "gearLead",
                'P', "plateSteel"
        );

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.thruster_reinforced),
                "PIP", "GIG", "S S",
                'G', "gearElectrum",
                'P', "plateElectrum",
                'I', "ingotSteel",
                'S', ItemMaterial.powerCoilSilver
        );

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.thruster_signalum),
                "GPG", "PSP", "S S",
                'G', "gearSignalum",
                'P', "plateSignalum",
                'S', ItemMaterial.redstoneServo
        );

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.thruster_resonant),
                "GCG", "GGG", "PPP",
                'G', "gearEnderium",
                'P', "plateEnderium",
                'C', ItemMaterial.redstoneServo
        );

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.mmu_basic),
                "T T", "LFL", "CGC",
                'T', new ItemStack(ItemRegistry.flux_tube),
                'C', new ItemStack(ItemRegistry.thruster_basic),
                'F', new ItemStack(ItemRegistry.armor_frame),
                'L', new ItemStack(Items.LEATHER),
                'G', "blockGlassHardened"
        );

        RecipeHelper.addShapedUpgradeRecipe(new ItemStack(ItemRegistry.mmu_hardened),
                "PTP", "GFG", "CPC",
                'T', new ItemStack(ItemRegistry.flux_tube),
                'C', new ItemStack(ItemRegistry.thruster_hardened),
                'F', new ItemStack(ItemRegistry.mmu_basic),
                'G', "blockGlassHardened",
                'P', "plateInvar"
        );
        RecipeHelper.addShapedUpgradeRecipe(new ItemStack(ItemRegistry.mmu_reinforced),
                "GEG", "TFT", "CTC",
                'T', ItemMaterial.powerCoilGold,
                'C', new ItemStack(ItemRegistry.thruster_reinforced),
                'F', new ItemStack(ItemRegistry.mmu_hardened),
                'G', "blockGlassHardened",
                'E', "gearElectrum"
        );
        RecipeHelper.addShapedUpgradeRecipe(new ItemStack(ItemRegistry.mmu_signalum),
                "SFS", "CEC", "PGP",
                'S', ItemMaterial.redstoneServo,
                'C', new ItemStack(ItemRegistry.thruster_signalum),
                'F', new ItemStack(ItemRegistry.mmu_reinforced),
                'G', "blockGlassHardened",
                'E', "gearSignalum",
                'P', "plateSignalum"
        );
        RecipeHelper.addShapedUpgradeRecipe(new ItemStack(ItemRegistry.mmu_resonant),
                "ETE", "GFG", "CPC",
                'T', ItemMaterial.partToolCasing,
                'C', new ItemStack(ItemRegistry.thruster_resonant),
                'F', new ItemStack(ItemRegistry.mmu_signalum),
                'G', "blockGlassHardened",
                'E', "gearEnderium",
                'P', "plateEnderium"
        );

        RecipeHelper.addShapedRecipe(new ItemStack(ItemRegistry.handheld),
                " B ", " CL", " M ",
                'B', new ItemStack(Items.REDSTONE),
                'L', new ItemStack(Items.LEATHER),
                'C', ItemMaterial.partToolCasing,
                'M', TFItems.itemMeter);
    }
}
