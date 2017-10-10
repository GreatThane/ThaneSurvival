package org.thane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.thane.Enums.CustomRecipe;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.*;

public class CraftHandler implements Listener {

    private static HashMap<String, CustomRecipe> recipes = new HashMap<>();
    private static final Map<Integer, Integer> SLOTS = new HashMap<Integer, Integer>() {{
        put(1, 11);
        put(2, 12);
        put(3, 13);
        put(4, 20);
        put(5, 21);
        put(6, 22);
        put(7, 29);
        put(8, 30);
        put(9, 31);
    }};
    private static final int RESULT_SLOT = 24;

    public static void loadRecipes() {
        recipes.put("22C56AF4EFA640CC066F867EC50787BA55DAA2E0", CustomRecipe.VISION_HELMET);
        recipes.put("AE48A6178E3778673DE665F09BCCC3B62239F9EE", CustomRecipe.DIVERSIFIER);
        recipes.put("88576F68F37A08D21F5B49F7809B4F37D987D755", CustomRecipe.ANGEL_BOOTS);
        recipes.put("57CADA6ADF8EEEAD8CD5BF806558474CEE319D30", CustomRecipe.FLIGHT_PLATE);
    }

    private static ItemStack craftCustomRecipe(CustomRecipe recipe, Map<Integer, ItemStack> grid) {

        switch (recipe) {
            case VISION_HELMET:
                //grab the helmet
                ItemStack helmet = new ItemStack(grid.get(SLOTS.get(5)));
                ItemMeta meta = helmet.getItemMeta();
                meta.addEnchant(Enchantment.WATER_WORKER, 2, true);
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Vision Helmet");
                helmet.setItemMeta(meta);
                return helmet;

            case DIVERSIFIER:
                ItemStack diversifier = new ItemStack(Material.SPECTRAL_ARROW);
                ItemMeta diversifierMeta = diversifier.getItemMeta();
                diversifierMeta.setDisplayName(ChatColor.DARK_AQUA + "Diversifier");
                List<String> diversifierLore = new ArrayList<>();
                diversifierLore.add(ChatColor.GRAY + "Allows for you to switch the");
                diversifierLore.add(ChatColor.GRAY + "item type in exchange for experience.");
                diversifierMeta.setLore(diversifierLore);
                diversifierMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                diversifierMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                diversifier.setItemMeta(diversifierMeta);
                return diversifier;

            case ANGEL_BOOTS:
                ItemStack angelBoot = new ItemStack(grid.get(SLOTS.get(5)));
                ItemMeta angelMeta = angelBoot.getItemMeta();
                angelMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Angel Boots");
                angelBoot.setItemMeta(angelMeta);
                return angelBoot;

            case FLIGHT_PLATE:
                ItemStack flightPlate = new ItemStack(grid.get(SLOTS.get(5)));
                ItemMeta flightPlateMeta = flightPlate.getItemMeta();
                flightPlateMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Flight Plate");
                flightPlate.setItemMeta(flightPlateMeta);
                return flightPlate;
            default:
                Bukkit.getServer().getLogger().warning("I couldn't craft the thingy! (" + recipe.toString() + ")");
        }
        return null;
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {

        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table")) {
            Inventory inventory = event.getInventory();

            if (event.getInventorySlots().contains(RESULT_SLOT)) {
                event.setCancelled(true);
                return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    calculateGrid(inventory);
                }
            }.runTaskLater(ThaneSurvival.plugin(), 1);
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlot() != -999 && event.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table")) {
            Inventory inventory = event.getClickedInventory();
            boolean place = false;
            if (event.getCursor() != null
                    && !event.getCursor().getType().equals(Material.AIR)) {
                place = true;
            }

            //Don't let player place anything outside of our 9 slots.. kthxbye
            if (!SLOTS.containsValue(event.getSlot())
                    && event.getSlot() != RESULT_SLOT) {
                event.setCancelled(true);
                return;
            }
            //Don't let player place in Result Slot
            if (event.getSlot() == RESULT_SLOT && place) {
                event.setCancelled(true);
                return;
            }

            if (!place && event.getSlot() == RESULT_SLOT) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 9; i++) {
                            if (inventory.getItem(SLOTS.get(i)) != null && inventory.getItem(SLOTS.get(i)).getAmount() > 1) {
                                inventory.getItem(SLOTS.get(i)).setAmount(inventory.getItem(SLOTS.get(i)).getAmount() - 1);
                            } else {
                                inventory.setItem(SLOTS.get(i), new ItemStack(Material.AIR));
                            }
                        }
                    }
                }.runTaskLater(ThaneSurvival.plugin(), 1);
                return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    calculateGrid(inventory);
                }
            }.runTaskLater(ThaneSurvival.plugin(), 1);

        }
    }

    private static void calculateGrid(Inventory inventory) {

        LinkedHashMap<Integer, ItemStack> items = new LinkedHashMap<>();

        for (int i = 1; i <= 9; i++) {
            items.put(SLOTS.get(i), inventory.getItem(SLOTS.get(i)));
        }

        String recipeHash = hashRecipe(items);
        Bukkit.getServer().getLogger().info("Your hash:" + recipeHash);

        if (recipes.containsKey(recipeHash)) {
            ItemStack newItem = craftCustomRecipe(recipes.get(recipeHash), items);
            inventory.setItem(RESULT_SLOT, newItem);
        } else {
            inventory.setItem(RESULT_SLOT, new ItemStack(Material.AIR));
        }
    }

    private static String hashRecipe(Map<Integer, ItemStack> grid) {

        String slotSeparator = "|";
        String propertySeparator = ";";

        StringBuilder recipe = new StringBuilder();
        for (Map.Entry<Integer, ItemStack> entry : grid.entrySet()) {
            ItemStack item = genericizeItem(entry.getValue());
            if (item != null && !item.getType().equals(Material.AIR)) {
                recipe.append(item.getType().toString()).append(propertySeparator)
                        .append(item.getAmount()).append(propertySeparator)
                        .append(item.getDurability()).append(propertySeparator)
                        .append(item.getItemMeta().getDisplayName());
            }
            recipe.append(slotSeparator);
        }

        //Bukkit.getServer().getLogger().info(recipe.toString());

        return getHash(recipe.toString());
    }

    private static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter
                    .printHexBinary(digest); //.toUpperCase();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static ItemStack genericizeItem(ItemStack itemIn) {

        if (itemIn == null) {
            return null;
        }
        if (itemIn.getType().toString().endsWith("_HELMET")) {
            return copyItem(itemIn, Material.DIAMOND_HELMET);
        } else if (itemIn.getType().equals(Material.GLASS) ||
                itemIn.getType().equals(Material.STAINED_GLASS)) {
            return copyItem(itemIn, Material.GLASS);
        } else if (itemIn.getType().toString().endsWith("_BOOTS")) {
            return copyItem(itemIn, Material.DIAMOND_BOOTS);
        } else if (itemIn.getType().toString().endsWith("_CHESTPLATE")) {
            return copyItem(itemIn, Material.DIAMOND_CHESTPLATE);
        }
        return itemIn;
    }

    private static ItemStack copyItem(ItemStack from, Material newType) {

        return new ItemStack(newType, from.getAmount());
    }
}