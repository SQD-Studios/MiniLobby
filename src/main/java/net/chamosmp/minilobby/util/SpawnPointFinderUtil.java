package net.chamosmp.minilobby.util;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SpawnPointFinderUtil {
    private Location location;

    private final Plugin plugin;

    public SpawnPointFinderUtil(Plugin plugin) {
        this.plugin = plugin;
    }

    public @NotNull Location getLocation() {
        if (location == null) {
            FileConfiguration config = plugin.getConfig();

            Location loc = new Location(
                    plugin.getServer().getRespawnWorld(),
                    0.0,
                    0.0,
                    0.0
            );

            location = config.getObject("spawn-location", Location.class, loc);
        }
        return location;
    }
}