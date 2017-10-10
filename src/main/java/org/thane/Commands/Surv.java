package org.thane.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.thane.Recipes.RecipeBook;
import org.thane.ThaneSurvival;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class Surv implements Listener {

    public static void loadRecipes() {
        RecipeBook.items.add("Dechanter Recipe");
        RecipeBook.items.add("Vision Helmet");
        RecipeBook.items.add("Diversifier Recipe");
        RecipeBook.items.add("Angel Boots Recipe");
        RecipeBook.items.add("Flight Plate Recipe");
    }

    public static boolean handleCommand(CommandSender sender, String[] args) {

        if (!(args.length > 0)) {
            sender.sendMessage(ChatColor.RED + "You need to specify what you want to craft!");
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "_______________");
            sender.sendMessage("");
            for (String string : RecipeBook.items) {
                sender.sendMessage(ChatColor.AQUA + string);
            }
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "_______________");
            return true;
        }

        StringBuilder command = new StringBuilder();
        for (String arg : args) {
            command.append(arg.toLowerCase().trim());
        }
        Player player = (Player) sender;
        List<String> items = new ArrayList<>();
        for (String item : RecipeBook.items) {
            items.add(item.toLowerCase().replaceAll("\\s", "").trim());
        }
        if (items.contains(command.toString())) {

            switch (command.toString().trim().toLowerCase()) {
                case "dechanterrecipe":
                    dechanterRecipe(player);
                    break;
                case "visionhelmet":
                    visionHelmetReciple(player);
                    break;
                case "diversifierrecipe":
                    diversifierRecipe(player);
                    break;
                case "angelbootsrecipe":
                    angelBootsRecipe(player);
                    break;
                case "flightplaterecipe":
                    flightPlateRecipe(player);
                    break;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is not an item you can craft!");
            return false;
        }
        return true;
    }

    private static void flightPlateRecipe(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, ChatColor.LIGHT_PURPLE + "Magic Table " + ChatColor.GRAY + "| (Example)" + ChatColor.MAGIC + "");

        ItemStack itemStack = new ItemStack(Material.LEATHER);
        inventory.setItem(11, itemStack);
        inventory.setItem(13, itemStack);
        itemStack.setType(Material.ENDER_PEARL);
        inventory.setItem(12, itemStack);
        itemStack.setType(Material.FEATHER);
        inventory.setItem(20, itemStack);
        inventory.setItem(22, itemStack);
        inventory.setItem(29, itemStack);
        inventory.setItem(31, itemStack);
        itemStack.setType(Material.LEATHER_CHESTPLATE);
        inventory.setItem(21, itemStack);

        ItemStack flightPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta flightPlateMeta = flightPlate.getItemMeta();
        flightPlateMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Flight Plate");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Allows the user to fly!");
        flightPlateMeta.setLore(lore);
        flightPlate.setItemMeta(flightPlateMeta);

        inventory.setItem(24, flightPlate);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 15);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) == null
                    && i != 30) {
                inventory.setItem(i, glass);
            }
        }
        player.openInventory(inventory);
    }

    private static void angelBootsRecipe(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, ChatColor.LIGHT_PURPLE + "Magic Table " + ChatColor.GRAY + "| (Example)" + ChatColor.MAGIC + "");

        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        inventory.setItem(11, itemStack);
        inventory.setItem(13, itemStack);
        itemStack.setType(Material.LEASH);
        inventory.setItem(12, itemStack);
        itemStack.setType(Material.FEATHER);
        inventory.setItem(20, itemStack);
        inventory.setItem(22, itemStack);
        itemStack.setType(Material.LEATHER_BOOTS);
        inventory.setItem(21, itemStack);

        ItemStack angelBoot = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta angelMeta = angelBoot.getItemMeta();
        angelMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Angel Boots");
        angelMeta.addEnchant(Enchantment.PROTECTION_FALL, 999, true);
        angelMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Removes all fall damage.");
        angelMeta.setLore(lore);
        angelBoot.setItemMeta(angelMeta);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 15);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) == null
                    && i != 29 && i != 30 && i != 31) {
                inventory.setItem(i, glass);
            }
        }
        player.openInventory(inventory);

    }

    private static void diversifierRecipe(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, ChatColor.LIGHT_PURPLE + "Magic Table " + ChatColor.GRAY + "| (Example)" + ChatColor.MAGIC + "");

        ItemStack itemStack = new ItemStack(Material.LOG);
        inventory.setItem(31, itemStack);
        itemStack.setDurability((short) 1);
        inventory.setItem(11, itemStack);
        itemStack.setDurability((short) 3);
        inventory.setItem(13, itemStack);
        itemStack.setType(Material.LOG_2);
        itemStack.setDurability((short) 1);
        inventory.setItem(29, itemStack);
        itemStack.setType(Material.INK_SACK);
        itemStack.setDurability((short) 4);
        inventory.setItem(12, itemStack);
        itemStack.setDurability((short) 3);
        inventory.setItem(20, itemStack);
        itemStack.setDurability((short) 15);
        inventory.setItem(21, itemStack);
        itemStack.setDurability((short) 0);
        inventory.setItem(22, itemStack);
        itemStack.setDurability((short) 1);
        inventory.setItem(30, itemStack);

        ItemStack diversifier = new ItemStack(Material.ARROW);
        ItemMeta diversiferMeta = diversifier.getItemMeta();
        diversiferMeta.setDisplayName(ChatColor.DARK_AQUA + "Diversifier");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Allows for you to switch the");
        lore.add(ChatColor.GRAY + "item type in exchange for experience.");
        lore.add(ChatColor.GRAY + " ");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "With this table, you are able");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "to change the data value of items.");
        diversiferMeta.setLore(lore);
        diversiferMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        diversiferMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diversifier.setItemMeta(diversiferMeta);
        inventory.setItem(24, diversifier);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 15);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
        player.openInventory(inventory);

    }

    private static void visionHelmetReciple(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, ChatColor.LIGHT_PURPLE + "Magic Table " + ChatColor.GRAY + "| (Example)" + ChatColor.MAGIC + "");

        ItemStack itemStack = new ItemStack(Material.GLASS);
        inventory.setItem(11, itemStack);
        inventory.setItem(12, itemStack);
        inventory.setItem(13, itemStack);
        itemStack.setType(Material.GOLDEN_CARROT);
        inventory.setItem(20, itemStack);
        inventory.setItem(22, itemStack);
        itemStack.setType(Material.LEATHER_HELMET);
        inventory.setItem(21, itemStack);
        itemStack.setType(Material.LEATHER);
        inventory.setItem(29, itemStack);
        inventory.setItem(31, itemStack);

        ItemStack visionHelmet = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta visionHelmetMeta = visionHelmet.getItemMeta();
        visionHelmetMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vision Helmet");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Allows for you to see in the dark!");
        lore.add(" ");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "This helmet will additionally");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "give you permanent night vision");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "while you are wearing it.");
        visionHelmetMeta.setLore(lore);
        visionHelmetMeta.addEnchant(Enchantment.WATER_WORKER, 2, true);
        visionHelmet.setItemMeta(visionHelmetMeta);
        inventory.setItem(24, visionHelmet);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 15);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
        itemStack.setType(Material.AIR);
        inventory.setItem(30, itemStack);
        player.openInventory(inventory);

    }

    private static void dechanterRecipe(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 45, ChatColor.DARK_GRAY + "Crafting " + ChatColor.GRAY + "| (Example)" + ChatColor.MAGIC + "");

        ItemStack itemStack = new ItemStack(Material.BOOK);
        inventory.setItem(12, itemStack);
        itemStack.setType(Material.LAVA_BUCKET);
        inventory.setItem(21, itemStack);
        itemStack.setType(Material.BLAZE_ROD);
        inventory.setItem(29, itemStack);
        inventory.setItem(31, itemStack);
        itemStack.setType(Material.IRON_BLOCK);
        inventory.setItem(30, itemStack);
        player.closeInventory();

        ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Dechanter");
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Remove enchantments from your items");
        lore.add("");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Place items into the first slot to");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "reveal your unenchanted item and an");
        lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "enchanted book with said enchantments.");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(lore);
        stack.setItemMeta(itemMeta);
        inventory.setItem(24, stack);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 15);
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
        itemStack.setType(Material.AIR);
        inventory.setItem(11, itemStack);
        inventory.setItem(13, itemStack);
        inventory.setItem(20, itemStack);
        inventory.setItem(22, itemStack);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onMenu(InventoryClickEvent event) {
        if (event.getInventory().getTitle().contains(ChatColor.MAGIC + "")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {

        if (event.getInventory().getSize() >= 24 && event.getInventory().getItem(24) != null
                && event.getInventory().getItem(24).hasItemMeta()) {

            if (event.getInventory().getItem(24).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Vision Helmet")) {
                List<Material> helmets = new ArrayList<>();
                helmets.add(Material.CHAINMAIL_HELMET);
                helmets.add(Material.IRON_HELMET);
                helmets.add(Material.GOLD_HELMET);
                helmets.add(Material.DIAMOND_HELMET);
                int delay = 60;
                for (Material helmet : helmets) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ItemStack visionHelmet = new ItemStack(helmet);
                            event.getInventory().setItem(21, visionHelmet);

                            ItemMeta visionHelmetMeta = visionHelmet.getItemMeta();
                            visionHelmetMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vision Helmet");
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.GRAY + "Allows for you to see in the dark!");
                            lore.add(" ");
                            lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "This helmet will additionally");
                            lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "give you permanent night vision");
                            lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "while you are wearing it.");
                            visionHelmetMeta.setLore(lore);
                            visionHelmetMeta.addEnchant(Enchantment.WATER_WORKER, 2, true);
                            visionHelmet.setItemMeta(visionHelmetMeta);

                            event.getInventory().setItem(24, visionHelmet);
                        }
                    }.runTaskLater(ThaneSurvival.plugin(), delay);
                    delay += 60;
                }
            } else if (event.getInventory().getItem(24).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Angel Boots")) {
                List<Material> boots = new ArrayList<>();
                boots.add(Material.CHAINMAIL_BOOTS);
                boots.add(Material.IRON_BOOTS);
                boots.add(Material.GOLD_BOOTS);
                boots.add(Material.DIAMOND_BOOTS);
                int delay = 60;
                for (Material boot : boots) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ItemStack angelBoot = new ItemStack(boot);
                            event.getInventory().setItem(21, angelBoot);

                            ItemMeta angelMeta = angelBoot.getItemMeta();
                            angelMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Angel Boots");
                            angelMeta.addEnchant(Enchantment.PROTECTION_FALL, 999, true);
                            angelMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Removes all fall damage.");
                            angelMeta.setLore(lore);
                            angelBoot.setItemMeta(angelMeta);

                            event.getInventory().setItem(24, angelBoot);
                        }
                    }.runTaskLater(ThaneSurvival.plugin(), delay);
                    delay += 60;
                }
            } else if (event.getInventory().getItem(24).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Flight Plate")) {
                List<Material> boots = new ArrayList<>();
                boots.add(Material.CHAINMAIL_CHESTPLATE);
                boots.add(Material.IRON_CHESTPLATE);
                boots.add(Material.GOLD_CHESTPLATE);
                boots.add(Material.DIAMOND_CHESTPLATE);
                int delay = 60;
                for (Material boot : boots) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ItemStack flightPlate = new ItemStack(boot);
                            event.getInventory().setItem(21, flightPlate);

                            ItemMeta flightPlateMeta = flightPlate.getItemMeta();
                            flightPlateMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Flight Plate");
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.WHITE + "" + ChatColor.ITALIC + "Allows the user to fly!");
                            flightPlateMeta.setLore(lore);
                            flightPlate.setItemMeta(flightPlateMeta);

                            event.getInventory().setItem(24, flightPlate);
                        }
                    }.runTaskLater(ThaneSurvival.plugin(), delay);
                    delay += 60;
                }
            }
        }
    }
}
