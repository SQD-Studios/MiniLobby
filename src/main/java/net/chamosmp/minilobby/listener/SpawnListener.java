package net.chamosmp.minilobby.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;
import net.chamosmp.minilobby.util.InventoryParserUtil;
import net.chamosmp.minilobby.util.SpawnPointFinderUtil;
import net.chamosmp.sqdlib.paper.util.SchedulerUtil;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class SpawnListener implements Listener {

    private final SpawnPointFinderUtil finder;
    private final Plugin plugin;

    public SpawnListener(SpawnPointFinderUtil finder, Plugin plugin) {
        this.finder = finder;
        this.plugin = plugin;
    }

    @EventHandler
    public void onSelectingSpawn(AsyncPlayerSpawnLocationEvent event) {
        event.setSpawnLocation(finder.getLocation());
    }

    @EventHandler
    public void onPlayerDeath(PlayerPostRespawnEvent event) {
        Player player = event.getPlayer();

        SchedulerUtil.runForEntity(plugin, player, () -> {
            player.teleport(finder.getLocation());

            final Key configWorldKey = Key.key(plugin.getConfig().getString("set-inventory-configWorldKey", "minecraft:overworld"));
            final Key playerWorldKey = player.getWorld().getKey().key();

            if (configWorldKey.equals(playerWorldKey)) {
                PlayerInventory playerInventory = player.getInventory();
                playerInventory.setContents(InventoryParserUtil.parse(plugin, playerInventory).getContents());
            }
        }, null);
    }
}