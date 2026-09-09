package net.chamosmp.minilobby.util;

import net.chamosmp.sqdlib.paper.util.SchedulerUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class InventoryUtil {

    private InventoryUtil() {
    }

    public static Inventory parse(Plugin p, Inventory inventory) {
        FileConfiguration config = p.getConfig();

        @NotNull List<?> items = Objects.requireNonNullElse(config.getList("inventory"), List.of());
        @NotNull List<ItemStack> itemStacks = new ArrayList<>();
        items.forEach(item -> {
            if (item instanceof ItemStack itemStack) {
                itemStacks.add(itemStack);
            } else {
                itemStacks.add(null);
            }
        });

        inventory.clear();
        inventory.setContents(itemStacks.toArray(new ItemStack[0]));

        return inventory;
    }

    public static void setInventoryContents(Player player, Plugin plugin) {
        SchedulerUtil.runForEntity(plugin, player, () -> {
            if (WorldUtil.isSameWorldAsConfig(player, plugin)) {
                PlayerInventory playerInventory = player.getInventory();
                playerInventory.setContents(InventoryUtil.parse(plugin, playerInventory).getContents());
            }
        }, null);
    }
}