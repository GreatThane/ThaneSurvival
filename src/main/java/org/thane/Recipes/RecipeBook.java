package org.thane.Recipes;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftMetaBook;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;


public class RecipeBook {

    public static List<String> items = new ArrayList<>();

    public static ShapelessRecipe recipe() {

        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setAuthor(ChatColor.ITALIC + "The Wizard");
        bookMeta.setTitle(ChatColor.GOLD + "Crafting for the Creative");
        List<IChatBaseComponent> pages = new ArrayList<>();
        try {
            pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }

        int timesDone = 0;

        TextComponent textComponent = new TextComponent("Ello");

//        for (String string : items) {
//            textComponent.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
//            textComponent.setBold(true);
//            textComponent.addExtra(string + "\n  ");
//            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "surv " + string));
//            timesDone++;
//            if (timesDone == 5) {
//                IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(textComponent));
//                pages.add(page);
//                textComponent.setText("");
//                timesDone = 0;
//            }
//        }
        itemStack.setItemMeta(bookMeta);

        ShapelessRecipe recipe = new ShapelessRecipe(itemStack);
        recipe.addIngredient(Material.QUARTZ);
        recipe.addIngredient(Material.LEATHER);
        return recipe;
    }
}
