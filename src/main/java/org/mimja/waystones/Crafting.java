package org.mimja.waystones;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.mimja.waystones.Items.WaystoneItem;

public class Crafting {
    static ShapedRecipe getWaypointRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(PluginNamespace.get().getRecipeKey(), WaystoneItem.getWaypointItem());

        recipe.shape(
            "GEG",
            "DPD",
            "OCO"
        );

        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('E', Material.END_CRYSTAL);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('P', Material.ENDER_PEARL);
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('C', Material.CRYING_OBSIDIAN);

        return recipe;
    }
}