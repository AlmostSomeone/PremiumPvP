package dev.almostsomeone.premiumpvp.listeners.player;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private YamlConfiguration config = Main.getInstance().config.get();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!config.isSet("participate.join.server") || config.getBoolean("participate.join.server")) // Check if the player should join the game on server join
            Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(player.getUniqueId())); // Trigger the GamePlayerJoinEvent
    }
}
