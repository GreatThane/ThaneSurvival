package org.thane.Recipes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Dropper;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.thane.ThaneSurvival;

import java.util.ArrayList;
import java.util.List;

public class MagicTable implements Listener {

    public static ShapedRecipe recipe() {

        ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Magic Table");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Allows for magical crafting.");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        itemStack.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(itemStack);
        recipe.shape("cec", "*d*", "fff");
        recipe.setIngredient('c', Material.WORKBENCH);
        recipe.setIngredient('e', Material.ENCHANTMENT_TABLE);
        recipe.setIngredient('*', Material.AIR);
        recipe.setIngredient('f', Material.FURNACE);
        recipe.setIngredient('d', Material.DROPPER);

        return recipe;
    }

    @EventHandler
    public void magicTablePlace(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {

                if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.BLAZE_POWDER)
                        && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table")) {

                    Block block = event.getClickedBlock().getRelative(event.getBlockFace());
                    if (block.getType().equals(Material.AIR)
                            && new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ()).getBlock().getType().equals(Material.AIR)) {

                        block.setType(Material.DROPPER);
                        block.setData((byte) 1);
                        Dropper dropper = (Dropper) block.getState();
                        dropper.setCustomName(ChatColor.LIGHT_PURPLE + "Magic Table");
                        Location stand = new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ());
                        stand.getBlock().setType(Material.BREWING_STAND);
                        BrewingStand brewingStand = (BrewingStand) stand.getBlock().getState();
                        brewingStand.setCustomName(ChatColor.LIGHT_PURPLE + "Magic Table");

                        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
                        if (event.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                            itemStack.setAmount(itemStack.getAmount() - 1);
                        } else {
                            itemStack.setType(Material.AIR);
                        }
                        event.getPlayer().getInventory().setItemInMainHand(itemStack);
                    }
                }
            }
        }
    }

    @EventHandler
    public void standInteract(InventoryOpenEvent event) {

        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table") && (event.getInventory().getType().equals(InventoryType.BREWING)) || event.getInventory().getType().equals(InventoryType.DROPPER)) {
            event.setCancelled(true);
            openInventory((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table") && event.getInventory().getType().equals(InventoryType.CHEST)) {
            List<Integer> slots = new ArrayList<>();
            slots.add(11);
            slots.add(12);
            slots.add(13);
            slots.add(20);
            slots.add(21);
            slots.add(22);
            slots.add(29);
            slots.add(30);
            slots.add(31);
            for (Integer i : slots) {
                if (event.getInventory().getItem(i) != null) {
                    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(i));
                }
            }
        }
    }


    @EventHandler
    public void breakTable(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.DROPPER)) {
            Dropper dropper = (Dropper) event.getBlock().getState();
            if (dropper.getCustomName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table")) {

                event.setCancelled(true);
                breakMagicTable(event.getBlock());
                Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() + 1, event.getBlock().getZ());
                location.getBlock().setType(Material.AIR);

            }
        } else if (event.getBlock().getType().equals(Material.BREWING_STAND)) {
            BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();
            if (brewingStand.getCustomName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Magic Table")) {

                event.setCancelled(true);
                breakMagicTable(event.getBlock());
                Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - 1, event.getBlock().getZ());
                location.getBlock().setType(Material.AIR);
            }
        }
    }

    private static void breakMagicTable(Block block) {
        block.setType(Material.AIR);

        ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Magic Table");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Allows for magical crafting.");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        itemStack.setItemMeta(itemMeta);

        block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
    }

    private static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, ChatColor.LIGHT_PURPLE + "Magic Table");
        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glass.setDurability((short) 15);
        glassMeta.setDisplayName(ChatColor.RED + "");
        glass.setItemMeta(glassMeta);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            if (i != 11 && i != 12 && i != 13 && i != 20 && i != 21 && i != 22 && i != 29 && i != 30 && i != 31 && i != 24) {
                inventory.setItem(i, glass);
            }
        }
        player.openInventory(inventory);

    }
}
