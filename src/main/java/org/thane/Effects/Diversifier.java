package org.thane.Effects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.thane.ThaneSurvival;

public class Diversifier implements Listener {

    private static final int INPUT = 4;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_AQUA + "Diversifier")) {
            Inventory inventory = event.getClickedInventory();
            boolean place = false;
            if (event.getCursor() != null
                    && !event.getCursor().getType().equals(Material.AIR)) {
                place = true;
            }
            if (event.getRawSlot() > 35) {
                return;
            }
            if (inventory.getItem(event.getSlot()) != null
                    && inventory.getItem(event.getSlot()).getItemMeta().hasDisplayName()
                    && inventory.getItem(event.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "")) {
                event.setCancelled(true);
                return;
            }
            if (event.getSlot() > 18 && place) {
                event.setCancelled(true);
                return;
            }
            if (!place && inventory.getItem(event.getSlot()) != null && ((Player) event.getWhoClicked()).getLevel() >= 1 && event.getSlot() >= 18) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        inventory.setItem(INPUT, new ItemStack(Material.AIR));
                        for (int i = 18; i <= inventory.getSize() - 1; i++) {
                            inventory.setItem(i, new ItemStack(Material.AIR));
                        }
                        ((Player) event.getWhoClicked()).setLevel(((Player) event.getWhoClicked()).getLevel() - 1);
                    }
                }.runTaskLater(ThaneSurvival.plugin(), 1);
                return;
            }
            if (!place && event.getSlot() == INPUT) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int i = 18; i <= inventory.getSize() - 1; i++) {
                            inventory.setItem(i, new ItemStack(Material.AIR));
                        }
                    }
                }.runTaskLater(ThaneSurvival.plugin(), 1);
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    diversify(inventory);
                }
            }.runTaskLater(ThaneSurvival.plugin(), 1);
        }
    }

    private static void diversify(Inventory inventory) {
        ItemStack item = inventory.getItem(INPUT);
        if (item != null && !item.getType().equals(Material.AIR)) {
            int count = 0;
            int amount = item.getAmount();
            switch (item.getType()) {

                case THIN_GLASS:
                case STAINED_GLASS_PANE:
                    inventory.setItem(18, new ItemStack(Material.THIN_GLASS));
                    count = 19;
                    for (int i = 0; i <= 15; i++) {
                        inventory.setItem(count, new ItemStack(Material.STAINED_GLASS_PANE, amount, (short) i));
                        count++;
                    }
                    break;
                case LOG:
                case LOG_2:
                    inventory.setItem(18, new ItemStack(Material.LOG, amount));
                    inventory.setItem(19, new ItemStack(Material.LOG, amount, (short) 1));
                    inventory.setItem(20, new ItemStack(Material.LOG, amount, (short) 2));
                    inventory.setItem(21, new ItemStack(Material.LOG, amount, (short) 3));
                    inventory.setItem(22, new ItemStack(Material.LOG_2, amount));
                    inventory.setItem(23, new ItemStack(Material.LOG_2, amount, (short) 1));
                    break;
                case SAPLING:
                    count = 18;
                    for (int i = 0; i <= 5; i++) {
                        inventory.setItem(count, new ItemStack(Material.SAPLING, amount, (short) i));
                        count++;
                    }
                    break;
                case GLASS:
                case STAINED_GLASS:
                    inventory.setItem(18, new ItemStack(Material.GLASS, amount));
                    count = 19;
                    for (int i = 0; i <= 15; i++) {
                        inventory.setItem(count, new ItemStack(Material.STAINED_GLASS, amount, (short) i));
                        count++;
                    }
                    break;
                case INK_SACK:
                    count = 18;
                    for (int i = 0; i <= 15; i++) {
                        inventory.setItem(count, new ItemStack(Material.INK_SACK, amount, (short) i));
                        count++;
                    }
                    break;
                case CLAY:
                case HARD_CLAY:
                case STAINED_CLAY:
                    inventory.setItem(18, new ItemStack(Material.CLAY));
                    inventory.setItem(19, new ItemStack(Material.HARD_CLAY));
                    count = 20;
                    for (int i = 0; i <= 15; i++) {
                        inventory.setItem(count, new ItemStack(Material.STAINED_GLASS, amount, (short) i));
                        count++;
                    }
                    break;
                case WOOL:
                    count = 18;
                    for (int i = 0; i <= 15; i++) {
                        inventory.setItem(count, new ItemStack(Material.WOOL, amount, (short) i));
                        count++;
                    }
                    break;
                case WOOD:
                    count = 18;
                    for (int i = 0; i <= 5; i++) {
                        inventory.setItem(count, new ItemStack(Material.WOOD, amount, (short) i));
                        count++;
                    }
                    break;
                case FENCE:
                case BIRCH_FENCE:
                case ACACIA_FENCE:
                case NETHER_FENCE:
                case SPRUCE_FENCE:
                case JUNGLE_FENCE:
                case DARK_OAK_FENCE:
                    inventory.setItem(18, new ItemStack(Material.FENCE, amount));
                    inventory.setItem(19, new ItemStack(Material.BIRCH_FENCE, amount));
                    inventory.setItem(20, new ItemStack(Material.SPRUCE_FENCE, amount));
                    inventory.setItem(21, new ItemStack(Material.JUNGLE_FENCE, amount));
                    inventory.setItem(22, new ItemStack(Material.ACACIA_FENCE, amount));
                    inventory.setItem(23, new ItemStack(Material.DARK_OAK_FENCE, amount));
                    inventory.setItem(24, new ItemStack(Material.NETHER_FENCE, amount));
                    break;
                case FENCE_GATE:
                case BIRCH_FENCE_GATE:
                case JUNGLE_FENCE_GATE:
                case ACACIA_FENCE_GATE:
                case SPRUCE_FENCE_GATE:
                case DARK_OAK_FENCE_GATE:
                    inventory.setItem(18, new ItemStack(Material.FENCE_GATE, amount));
                    inventory.setItem(19, new ItemStack(Material.BIRCH_FENCE_GATE, amount));
                    inventory.setItem(20, new ItemStack(Material.SPRUCE_FENCE_GATE, amount));
                    inventory.setItem(21, new ItemStack(Material.JUNGLE_FENCE_GATE, amount));
                    inventory.setItem(22, new ItemStack(Material.ACACIA_FENCE_GATE, amount));
                    inventory.setItem(23, new ItemStack(Material.DARK_OAK_FENCE_GATE, amount));
                    break;
                case WOOD_DOOR:
                case IRON_DOOR:
                case BIRCH_DOOR:
                case ACACIA_DOOR:
                case JUNGLE_DOOR:
                case SPRUCE_DOOR:
                case WOODEN_DOOR:
                case DARK_OAK_DOOR:
                    inventory.setItem(18, new ItemStack(Material.WOOD_DOOR, amount));
                    inventory.setItem(19, new ItemStack(Material.BIRCH_DOOR, amount));
                    inventory.setItem(20, new ItemStack(Material.SPRUCE_DOOR, amount));
                    inventory.setItem(21, new ItemStack(Material.JUNGLE_DOOR, amount));
                    inventory.setItem(22, new ItemStack(Material.ACACIA_DOOR, amount));
                    inventory.setItem(23, new ItemStack(Material.DARK_OAK_DOOR, amount));
                    inventory.setItem(24, new ItemStack(Material.IRON_DOOR, amount));
                    inventory.setItem(25, new ItemStack(Material.WOODEN_DOOR, amount));
                    break;
                case STONE:
                case SMOOTH_BRICK:
                case COBBLESTONE:
                case MOSSY_COBBLESTONE:
                    count = 20;
                    for (int i = 0; i <= 6; i++) {
                        inventory.setItem(count, new ItemStack(Material.STONE, amount, (short) i));
                        count++;
                    }
                    inventory.setItem(18, new ItemStack(Material.COBBLESTONE, amount));
                    inventory.setItem(19, new ItemStack(Material.MOSSY_COBBLESTONE, amount));
                    for (int i = 0; i <= 3; i++) {
                        inventory.setItem(count, new ItemStack(Material.SMOOTH_BRICK, amount, (short) i));
                        count++;
                    }
                    break;
                case WOOD_STAIRS:
                case DARK_OAK_STAIRS:
                case ACACIA_STAIRS:
                case BIRCH_WOOD_STAIRS:
                case JUNGLE_WOOD_STAIRS:
                case SPRUCE_WOOD_STAIRS:
                    inventory.setItem(18, new ItemStack(Material.WOOD_STAIRS, amount));
                    inventory.setItem(19, new ItemStack(Material.BIRCH_WOOD_STAIRS, amount));
                    inventory.setItem(20, new ItemStack(Material.SPRUCE_WOOD_STAIRS, amount));
                    inventory.setItem(21, new ItemStack(Material.JUNGLE_WOOD_STAIRS, amount));
                    inventory.setItem(22, new ItemStack(Material.ACACIA_STAIRS, amount));
                    inventory.setItem(23, new ItemStack(Material.DARK_OAK_STAIRS, amount));
                    break;
                case SMOOTH_STAIRS:
                case COBBLESTONE_STAIRS:
                    inventory.setItem(18, new ItemStack(Material.COBBLESTONE_STAIRS, amount));
                    inventory.setItem(19, new ItemStack(Material.SMOOTH_STAIRS, amount));
                case SANDSTONE:
                case RED_SANDSTONE:
                    count = 18;
                    for (int i = 0; i <= 2; i++) {
                        inventory.setItem(count, new ItemStack(Material.SANDSTONE, amount, (short) i));
                        count++;
                    }
                    for (int i = 0; i <= 2; i++) {
                        inventory.setItem(count, new ItemStack(Material.RED_SANDSTONE, amount, (short) i));
                        count++;
                    }
                    break;
                case SANDSTONE_STAIRS:
                case RED_SANDSTONE_STAIRS:
                    inventory.setItem(18, new ItemStack(Material.SANDSTONE_STAIRS, amount));
                    inventory.setItem(19, new ItemStack(Material.RED_SANDSTONE_STAIRS, amount));
                    break;
                case WOOD_STEP:
                    count = 18;
                    for (int i = 0; i <= 5; i++) {
                        inventory.setItem(count, new ItemStack(Material.WOOD_STEP, amount, (short) i));
                        count++;
                    }
                    break;
            }
        }
    }
}
