package org.thane.Recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class Planks {

    public static ShapelessRecipe recipe() {

        ShapelessRecipe recipe = new ShapelessRecipe(new ItemStack(Material.WOOD));
        recipe.addIngredient(Material.WOOD_STEP);
        recipe.addIngredient(Material.WOOD_STEP);

        return recipe;
    }
}
