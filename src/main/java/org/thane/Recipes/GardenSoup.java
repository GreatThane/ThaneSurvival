package org.thane.Recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class GardenSoup {

    public static ShapedRecipe recipe() {

        ItemStack itemStack = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Garden Salad");
        itemStack.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(itemStack);
        recipe.shape("*b*", "bwb", "*x*");
        recipe.setIngredient('*', Material.AIR);
        recipe.setIngredient('b', Material.BEETROOT_SEEDS);
        recipe.setIngredient('w', Material.INK_SACK);
        recipe.setIngredient('x', Material.BOWL);

        return recipe;
    }

    public static ShapedRecipe recipe1() {

        ItemStack itemStack = new ItemStack(Material.BEETROOT_SOUP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Garden Salad");
        itemStack.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(itemStack);
        recipe.shape("*b*", "bwb", "*x*");
        recipe.setIngredient('*', Material.AIR);
        recipe.setIngredient('b', Material.SEEDS);
        recipe.setIngredient('w', Material.INK_SACK);
        recipe.setIngredient('x', Material.BOWL);

        return recipe;
    }
}
