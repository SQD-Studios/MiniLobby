package net.chamosmp.minilobby.listener;

import net.chamosmp.minilobby.util.InventoryParserUtil;
import net.chamosmp.sqdlib.paper.util.SchedulerUtil;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

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

        setInventoryContents(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player p) {
            final Key configWorldKey = Key.key(plugin.getConfig().getString("set-inventory-configWorldKey", "minecraft:overworld"));
            final Key playerWorldKey = p.getWorld().getKey().key();

            if (configWorldKey.equals(playerWorldKey)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        setInventoryContents(p);
    }

    private void setInventoryContents(Player player) {
        SchedulerUtil.runForEntity(plugin, player, () -> {
            final Key configWorldKey = Key.key(plugin.getConfig().getString("set-inventory-configWorldKey", "minecraft:overworld"));
            final Key playerWorldKey = player.getWorld().getKey().key();

            if (configWorldKey.equals(playerWorldKey)) {
                PlayerInventory playerInventory = player.getInventory();
                playerInventory.setContents(InventoryParserUtil.parse(plugin, playerInventory).getContents());
            }
        }, null);
    }
}