package org.thane.Recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class NameTag {

    public static ShapelessRecipe recipe() {

        ShapelessRecipe recipe = new ShapelessRecipe(new ItemStack(Material.NAME_TAG, 2));
        recipe.addIngredient(Material.STRING);
        recipe.addIngredient(Material.IRON_INGOT);

        return recipe;
    }

    public static ShapelessRecipe recipe1() {

        ShapelessRecipe recipe = new ShapelessRecipe(new ItemStack(Material.NAME_TAG, 2));
        recipe.addIngredient(Material.STRING);
        recipe.addIngredient(Material.GOLD_INGOT);

        return recipe;
    }
}
