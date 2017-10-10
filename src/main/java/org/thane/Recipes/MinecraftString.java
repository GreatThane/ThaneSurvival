package org.thane.Recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class MinecraftString {

    public static ShapelessRecipe recipe() {

        ShapelessRecipe recipe = new ShapelessRecipe(new ItemStack(Material.STRING, 4));
        recipe.addIngredient(Material.WOOL);
        return recipe;
    }
}
