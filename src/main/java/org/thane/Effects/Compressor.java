package org.thane.Effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.inventory.ItemStack;

public class Compressor implements Listener {

    @EventHandler
    public void pistonCrush(BlockPistonExtendEvent event) {

        Location location = event.getBlock().getRelative(event.getDirection()).getLocation();
        if (event.getBlocks().size() == 0) {
            Entity entity = location.getWorld().spawnEntity(location, EntityType.VEX);
            int overAll = 0;
                for (Entity e : entity.getNearbyEntities(1, 1, 1)) {
                    if (e.getType().equals(EntityType.DROPPED_ITEM)) {
                        Item item = (Item) e;
                        if (item.getItemStack().getType().equals(Material.COAL)) {
                            int amount = 0;
                            if (item.getItemStack().getAmount() >= 32 && item.getItemStack().getAmount() < 64) {
                                amount = 1;
                                e.remove();
                            } else if (item.getItemStack().getAmount() == 64) {
                                amount = 2;
                                e.remove();
                            }
                            overAll += amount;
                        }
                    }
                }
            entity.remove();
            for (int items = overAll; items > 64; items -= 64) {
                overAll -= 64;
                ItemStack itemStack = new ItemStack(Material.DIAMOND, 64);
                location.getWorld().dropItemNaturally(location, itemStack);
            }
            if (overAll > 0) {
                ItemStack itemStack = new ItemStack(Material.DIAMOND, overAll);
                location.getWorld().dropItemNaturally(location, itemStack);
            }
        }
    }
}
