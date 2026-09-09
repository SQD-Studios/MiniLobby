package net.chamosmp.minilobby.listener;

import net.chamosmp.minilobby.util.WorldUtil;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import static net.chamosmp.minilobby.util.InventoryUtil.setInventoryContents;

public class PlayerInventoryListener implements Listener {

    private final Plugin plugin;

    public PlayerInventoryListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player p) {
            final Key configWorldKey = Key.key(plugin.getConfig().getString("set-inventory-configWorldKey", "minecraft:overworld"));
            final Key playerWorldKey = p.getWorld().getKey().key();

            if (configWorldKey.equals(playerWorldKey)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        setInventoryContents(player, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            event.setCancelled(
                    WorldUtil.isSameWorldAsConfig(player, plugin)
            );
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        setInventoryContents(p, plugin);
    }
}