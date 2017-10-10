package org.thane.Effects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.thane.ThaneSurvival;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dechanter implements Listener {

    private static final Map<Integer, Integer> RESULTS = new HashMap<Integer, Integer>() {{
        put(1, 5);
        put(2, 6);
    }};
    private static final int INPUT = 2;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.RED + "Dechanter")) {
            Inventory inventory = event.getClickedInventory();
            boolean place = false;
            if (event.getCursor() != null
                    && !event.getCursor().getType().equals(Material.AIR)) {
                place = true;
            }
            if (inventory.getItem(event.getSlot()) != null && inventory.getItem(event.getSlot()).getType().equals(Material.STAINED_GLASS_PANE)) {
                event.setCancelled(true);
                return;
            }
            if ((event.getSlot() == RESULTS.get(1) || event.getSlot() == RESULTS.get(2)) && place) {
                event.setCancelled(true);
                return;
            }
            if (!place && RESULTS.containsValue(event.getSlot())) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        inventory.setItem(INPUT, new ItemStack(Material.AIR));
                    }
                }.runTaskLater(ThaneSurvival.plugin(), 1);
                return;
            }
            if (!place && event.getSlot() == INPUT) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        inventory.setItem(RESULTS.get(1), new ItemStack(Material.AIR));
                        inventory.setItem(RESULTS.get(2), new ItemStack(Material.AIR));
                    }
                }.runTaskLater(ThaneSurvival.plugin(), 1);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    dechant(inventory);
                }
            }.runTaskLater(ThaneSurvival.plugin(), 1);
        }
    }

    private static void dechant(Inventory inventory) {
        ItemStack item = inventory.getItem(INPUT);
        if (item != null && item.hasItemMeta()) {
            Map<Enchantment, Integer> enchants = item.getItemMeta().getEnchants();
            if (enchants.size() > 0) {
                ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
                for (Map.Entry<Enchantment, Integer> e : enchants.entrySet()) {
                    itemMeta.addStoredEnchant(e.getKey(), e.getValue(), true);
                }
                itemMeta.setDisplayName(ChatColor.RED + "Dechanted Book");
                itemStack.setItemMeta(itemMeta);

                ItemStack itemStack1 = new ItemStack(item.getType(), item.getAmount());
                ItemMeta meta = itemStack1.getItemMeta();
                meta.setDisplayName(item.getItemMeta().getDisplayName());
                meta.setLore(item.getItemMeta().getLore());
                item.setDurability(item.getDurability());
                itemStack1.setItemMeta(meta);

                inventory.setItem(RESULTS.get(1), itemStack);
                inventory.setItem(RESULTS.get(2), itemStack1);
            }
        }
    }
}
