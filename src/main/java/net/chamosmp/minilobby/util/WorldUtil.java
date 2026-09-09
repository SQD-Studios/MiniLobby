package net.chamosmp.minilobby.util;

import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class WorldUtil {
    private WorldUtil() {
    }


    public static boolean isSameWorldAsConfig(Player player, Plugin plugin) {
        if (player.hasPermission("minilobby.skipinventory")) return false;

        final String worldKey = plugin.getConfig().getString("set-inventory-configWorldKey", "minecraft:overworld");
        final Key configWorldKey = Key.key(worldKey);
        final Key playerWorldKey = player.getWorld().getKey().key();

        return configWorldKey.equals(playerWorldKey);
    }
}
