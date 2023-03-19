package dev.almostsomeone.premiumpvp.listeners.gameplayer;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class GamePlayerLeaveListener implements Listener {

    private final FileConfiguration config = Settings.getConfig();

    @EventHandler
    public void gamePlayerLeave(PlayerQuitEvent event) { //TODO UserQuitEvent
        if (config.isSet("participate.leave.execute-command")) {
            ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();
            String command = Objects.requireNonNull(
                    config.getString("participate.leave.execute-command"));
            //.replaceAll("\\{player}", event.getGamePlayer().getPlayer().getName());
            Bukkit.dispatchCommand(consoleSender, command);
        }
    }
}
