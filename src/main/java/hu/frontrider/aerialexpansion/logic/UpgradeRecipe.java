package hu.frontrider.aerialexpansion.logic;

import com.google.gson.JsonObject;
import hu.frontrider.aerialexpansion.items.FlyingApparatusItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class UpgradeRecipe extends ShapedOreRecipe {
    public UpgradeRecipe(ResourceLocation group, @Nonnull ItemStack result, CraftingHelper.ShapedPrimer primer) {
        super(group, result, primer);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack output = super.getCraftingResult(inv);
        if (!output.isEmpty()) {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stackInSlot = inv.getStackInSlot(i);
                if(!stackInSlot.isEmpty() && stackInSlot.getItem() instanceof FlyingApparatusItem){
                    NBTTagCompound tagCompound = stackInSlot.getTagCompound();
                    output.setTagCompound(tagCompound);
                    return output;
                }
            }
        }
        return output;
    }

    @Override
    public String getGroup() {
        return group == null ? "" : group.toString();
    }
    public static class Factory implements IRecipeFactory{

        @Override
        public IRecipe parse(JsonContext context, JsonObject json) {
            final String group = JsonUtils.getString(json, "group", "");
            final CraftingHelper.ShapedPrimer primer = RecipeUtil.parseShaped(context, json);
            final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

            return new UpgradeRecipe(group.isEmpty() ? null : new ResourceLocation(group),result,primer);
        }
    }
}
