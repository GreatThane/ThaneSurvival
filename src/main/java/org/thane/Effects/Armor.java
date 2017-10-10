package org.thane.Effects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.thane.ThaneSurvival;

import java.util.ArrayList;
import java.util.List;

public class Armor implements Listener {

    private static List<Material> helmets = new ArrayList<Material>() {{
        add(Material.LEATHER_HELMET);
        add(Material.CHAINMAIL_HELMET);
        add(Material.GOLD_HELMET);
        add(Material.DIAMOND_HELMET);
        add(Material.IRON_HELMET);
    }};

    @EventHandler
    public void armorEquip(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (event.getPlayer().getInventory().getItemInMainHand() != null
                    && event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()
                    && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
                String itemName = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();

                if (itemName.equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Vision Helmet")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            visionHelmet(event.getPlayer());
                        }
                    }.runTaskLater(ThaneSurvival.plugin(), 1);
                }
            }
        }
    }

    @EventHandler
    public void inventoryEquip(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
            item(event.getCurrentItem(), (Player) event.getWhoClicked());
        }
        if (event.getCursor() != null && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasDisplayName()) {
            item(event.getCursor(), (Player) event.getWhoClicked());
        }
    }

    private static void visionHelmet(Player player) {
        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Vision Helmet")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false));
        } else {
            try {
                if (player.getPotionEffect(PotionEffectType.NIGHT_VISION).getAmplifier() == 1) {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }
            } catch (NullPointerException e) {
                return;
            }
        }
    }

    private static void item(ItemStack itemStack, Player player) {
        if (helmets.contains(itemStack.getType())) {
            String name = itemStack.getItemMeta().getDisplayName();

            if (name.equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Vision Helmet")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        visionHelmet(player);
                    }
                }.runTaskLater(ThaneSurvival.plugin(), 2);
            }
        }
    }
}
