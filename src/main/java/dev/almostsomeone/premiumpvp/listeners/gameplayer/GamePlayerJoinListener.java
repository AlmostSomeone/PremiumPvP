package dev.almostsomeone.premiumpvp.listeners.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldManager;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldProfile;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerJoinEvent;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class GamePlayerJoinListener implements Listener {

    @EventHandler
    public void gamePlayerJoin(GamePlayerJoinEvent event) {
        Game game = Main.getInstance().getGame();
        GamePlayer gamePlayer = event.getGamePlayer();
        WorldManager worldManager = game.getWorldManager();

        // Teleport the player to the spawn
        Location spawnLocation = game.getSpawnLocation();
        gamePlayer.getPlayer().teleport(spawnLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);

        // Apply world profile
        WorldProfile worldProfile = worldManager.getWorldProfile(spawnLocation.getWorld().getName());
        if(worldProfile != null)
            worldProfile.applyProfile(gamePlayer.getPlayer());
    }
}
