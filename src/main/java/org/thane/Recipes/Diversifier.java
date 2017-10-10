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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Diversifier implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")) {
            if (event.getInventory().getItem(4) != null) {
                event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(4));
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.SPECTRAL_ARROW)
                    && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")) {
                event.getPlayer().sendMessage("got here!");

                Block block = event.getClickedBlock().getRelative(event.getBlockFace());
                if (block.getType().equals(Material.AIR)
                        && new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ()).getBlock().getType().equals(Material.AIR)) {

                    block.setType(Material.BONE_BLOCK);
                    Location stand = new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ());
                    stand.getBlock().setType(Material.BREWING_STAND);
                    BrewingStand brewingStand = (BrewingStand) stand.getBlock().getState();
                    brewingStand.setCustomName(ChatColor.DARK_AQUA + "Diversifier");

                    ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
                    if (event.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    } else {
                        itemStack.setType(Material.AIR);
                    }
                    event.getPlayer().getInventory().setItemInMainHand(itemStack);
                }
            }
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR && event.getClickedBlock().getType().equals(Material.BONE_BLOCK)) {
                Block block = event.getPlayer().getWorld().getBlockAt(new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ()));
                if (block.getType().equals(Material.BREWING_STAND)) {
                    BrewingStand brewingStand = (BrewingStand) block.getState();
                    if (brewingStand.getCustomName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")) {

                        openInventory(event.getPlayer());
                    }
                }
            }
        }
    }

    private static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, ChatColor.DARK_AQUA + "Diversifier");
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        itemStack.setDurability((short) 15);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "");
        itemStack.setItemMeta(itemMeta);
        for (int i = 0; i <= 17; i++) {
            if (i != 4) {
                inventory.setItem(i, itemStack);
            }
        }
        player.openInventory(inventory);
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")
                && event.getInventory().getType().equals(InventoryType.BREWING)) {

            event.setCancelled(true);
            openInventory((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.BREWING_STAND)) {
            BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();
            if (brewingStand.getCustomName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")) {
                event.setCancelled(true);
                breakDiversifier(event.getBlock());
                Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - 1, event.getBlock().getZ());
                location.getBlock().setType(Material.AIR);
            } else if (event.getBlock().getType().equals(Material.BONE_BLOCK)) {
                Block block = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getX(), event.getBlock().getY() + 1, event.getBlock().getZ()));
                if (block.getType().equals(Material.BREWING_STAND)) {
                    BrewingStand brewingStand1 = (BrewingStand) event.getBlock().getState();
                    if (brewingStand1.getCustomName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")) {
                        event.setCancelled(true);
                        breakDiversifier(event.getBlock());
                        Location location = new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - 1, event.getBlock().getZ());
                        location.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

    private static void breakDiversifier(Block block) {
        block.setType(Material.AIR);

        ItemStack diversifier = new ItemStack(Material.ARROW);
        ItemMeta diversifierMeta = diversifier.getItemMeta();
        diversifierMeta.setDisplayName(ChatColor.DARK_AQUA + "Diversifier");
        List<String> diversifierLore = new ArrayList<>();
        diversifierLore.add(ChatColor.GRAY + "Allows for you to switch the");
        diversifierLore.add(ChatColor.GRAY + "item type in exchange for experience.");
        diversifierMeta.setLore(diversifierLore);
        diversifierMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        diversifierMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        diversifier.setItemMeta(diversifierMeta);
        block.getWorld().dropItemNaturally(block.getLocation(), diversifier);
    }
}
