package dev.almostsomeone.premiumpvp.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    private void playerJoin(PlayerJoinEvent event) {
        //Bukkit.getPluginManager().callEvent(new GamePlayerLoadEvent(PremiumPvP.getGame(), event.getPlayer().getUniqueId()));
    }
}
