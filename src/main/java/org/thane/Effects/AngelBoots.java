package org.thane.Effects;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class AngelBoots implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (event.getEntityType().equals(EntityType.PLAYER)
        && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {

            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getItemMeta().hasDisplayName() && player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Angel Boots")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
