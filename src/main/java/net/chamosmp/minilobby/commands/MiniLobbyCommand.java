package net.chamosmp.minilobby.commands;

import net.chamosmp.minilobby.MiniLobbyPlugin;
import net.chamosmp.sqdlib.paper.util.ColorUtil;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Executor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Command("minilobby")
public class MiniLobbyCommand {

    private final MiniLobbyPlugin plugin;

    public MiniLobbyCommand(MiniLobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Executes("setspawn")
    public void setSpawn(@Executor Player sender) {
        FileConfiguration config = plugin.getConfig();
        config.set("spawn-location", sender.getLocation());
        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(ColorUtil.parse("<green>Set spawn point for all new joins and respawns!"));
    }

    @Executes("setinventory")
    public void setInventory(@Executor Player sender) {
        FileConfiguration config = plugin.getConfig();
        config.set("inventory", Arrays.stream(sender.getInventory().getContents()).toList());
        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(ColorUtil.parse("<green>Set inventory!"));
    }

    @Executes("setinventoryworld")
    public void setInventoryWorld(@Executor Player sender) {
        FileConfiguration config = plugin.getConfig();
        config.set("set-inventory-world", sender.getWorld().getKey().asString());
        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(ColorUtil.parse("<green>Set inventory world!"));
    }

    @Executes("reload")
    public void reload(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage(ColorUtil.parse("<green>Reloaded!"));
    }
}
