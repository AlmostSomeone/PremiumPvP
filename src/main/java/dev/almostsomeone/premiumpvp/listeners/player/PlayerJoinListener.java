package dev.almostsomeone.premiumpvp.listeners.player;

import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Bukkit.getPluginManager().callEvent(new GamePlayerLoadEvent(event.getPlayer().getUniqueId()));
    }
}
