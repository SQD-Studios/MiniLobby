package net.chamosmp.minilobby.util;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SpawnPointFinderUtil {

    private final Plugin plugin;

    public SpawnPointFinderUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull Location getLocation() {
        FileConfiguration config = plugin.getConfig();

        Location loc = new Location(
                plugin.getServer().getRespawnWorld(),
                0.0,
                0.0,
                0.0
        );
        return config.getObject("spawn-location", Location.class, loc);
    }
}