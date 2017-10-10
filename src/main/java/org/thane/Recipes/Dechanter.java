package org.thane.Recipes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
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

import java.util.ArrayList;
import java.util.List;

public class Dechanter implements Listener {

    public static ShapedRecipe recipe() {

        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Dechanter");
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Remove enchantments from your items");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(itemStack);
        recipe.shape("*b*", "*l*", "rir");
        recipe.setIngredient('*', Material.AIR);
        recipe.setIngredient('b', Material.BOOK);
        recipe.setIngredient('l', Material.LAVA_BUCKET);
        recipe.setIngredient('r', Material.BLAZE_ROD);
        recipe.setIngredient('i', Material.IRON_BLOCK);

        return recipe;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.RED + "Dechanter")) {
            if (event.getInventory().getItem(2) != null) {
                event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(2));
            } else {
                if (event.getInventory().getItem(5) != null || event.getInventory().getItem(6) != null) {
                    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(5));
                    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(6));
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENCHANTED_BOOK)
                    && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Dechanter")) {

                Block block = event.getClickedBlock().getRelative(event.getBlockFace());
                if (block.getType().equals(Material.AIR)
                        && new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ()).getBlock().getType().equals(Material.AIR)) {

                    block.setType(Material.MAGMA);
                    Location stand = new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ());
                    stand.getBlock().setType(Material.BREWING_STAND);
                    BrewingStand brewingStand = (BrewingStand) stand.getBlock().getState();
                    brewingStand.setCustomName(ChatColor.RED + "Dechanter");

                    ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
                    if (event.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    } else {
                        itemStack.setType(Material.AIR);
                    }
                    event.getPlayer().getInventory().setItemInMainHand(itemStack);
                }
            }
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR && event.getClickedBlock().getType().equals(Material.MAGMA)) {
                Block block = event.getPlayer().getWorld().getBlockAt(new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ()));
                if (block.getType().equals(Material.BREWING_STAND)) {
                    BrewingStand brewingStand = (BrewingStand) block.getState();
                    if (brewingStand.getCustomName().equalsIgnoreCase(ChatColor.RED + "Dechanter")) {

                        openInventory(event.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.RED + "Dechanter")
                && event.getInventory().getType().equals(InventoryType.BREWING)) {
            event.setCancelled(true);

            openInventory((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void  onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.BREWING_STAND)) {
            BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();
            if (brewingStand.getCustomName().equalsIgnoreCase(ChatColor.RED + "Dechanter")) {
                event.setCancelled(true);
                breakDechanter(event.getBlock());
                Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - 1, event.getBlock().getZ());
                location.getBlock().setType(Material.AIR);
            }
        } else if (event.getBlock().getType().equals(Material.MAGMA)) {
            Block block = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getX(), event.getBlock().getY() + 1, event.getBlock().getZ()));
            if (block.getType().equals(Material.BREWING_STAND)) {
                BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();
                if (brewingStand.getCustomName().equalsIgnoreCase(ChatColor.RED + "Dechanter")) {
                    event.setCancelled(true);
                    breakDechanter(event.getBlock());
                    Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - 1, event.getBlock().getZ());
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    private static void breakDechanter(Block block) {
        block.setType(Material.AIR);

        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Dechanter");
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Remove enchantments from your items.");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
    }

    private static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Dechanter");
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        itemStack.setDurability((short) 15);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "");
        itemStack.setItemMeta(itemMeta);
        for (int i = 0; i <= 8; i++) {
            if (i != 2 && i != 5 && i != 6) {
                inventory.setItem(i, itemStack);
            }
        }
        player.openInventory(inventory);
    }

}
