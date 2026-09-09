package net.chamosmp.minilobby;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.chamosmp.minilobby.commands.MiniLobbyCommandBrigadier;
import net.chamosmp.minilobby.listener.PlayerInventoryListener;
import net.chamosmp.minilobby.listener.SpawnListener;
import net.chamosmp.minilobby.util.SpawnPointFinderUtil;
import net.chamosmp.sqdlib.exceptions.CommandRegisterException;
import net.chamosmp.sqdlib.paper.util.ConfigUtil;
import net.chamosmp.sqdlib.paper.util.LoggerUtil;
import net.chamosmp.sqdlib.util.LogType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniLobbyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new LoggerUtil("<green>MiniLobby</green>| ");

        ConfigUtil.loadDataFile(this, "config.yml");

        SpawnPointFinderUtil finder = new SpawnPointFinderUtil(this);
        Bukkit.getPluginManager().registerEvents(new SpawnListener(finder, this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(this), this);
        LoggerUtil.log(LogType.INFO, "Registered listeners");

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS.newHandler(event -> {
            try {
                MiniLobbyCommandBrigadier.register(event.registrar(), this);
                LoggerUtil.log(LogType.INFO, "Registered commands");
            } catch (Exception e) {
                throw new CommandRegisterException("Failed to registered commands", e);
            }
        }));
    }
}