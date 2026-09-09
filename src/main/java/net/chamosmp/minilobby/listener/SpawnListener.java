package net.chamosmp.minilobby.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;
import net.chamosmp.minilobby.util.SpawnPointFinderUtil;
import net.chamosmp.minilobby.util.WorldUtil;
import net.chamosmp.sqdlib.paper.util.SchedulerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import static net.chamosmp.minilobby.util.InventoryUtil.setInventoryContents;

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


            if (WorldUtil.isSameWorldAsConfig(player, plugin)) {
                setInventoryContents(player, plugin);
            }
        }, null);
    }
}