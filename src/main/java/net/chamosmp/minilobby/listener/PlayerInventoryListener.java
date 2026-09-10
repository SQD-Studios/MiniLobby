package net.chamosmp.minilobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import static net.chamosmp.minilobby.util.InventoryUtil.setInventoryContents;
import static net.chamosmp.minilobby.util.WorldUtil.isSameWorldAsConfig;

public class PlayerInventoryListener implements Listener {

    private final Plugin plugin;

    public PlayerInventoryListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(InventoryClickEvent event) {
        @Nullable Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getHolder() instanceof Player p) {
            event.setCancelled(
                    isSameWorldAsConfig(p, plugin)
            );
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
                    isSameWorldAsConfig(player, plugin)
            );
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        setInventoryContents(p, plugin);
    }
}