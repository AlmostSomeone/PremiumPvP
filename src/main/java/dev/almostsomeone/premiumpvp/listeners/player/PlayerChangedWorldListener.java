package dev.almostsomeone.premiumpvp.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    private void playerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        // Don't do anything if the player is not in the game
        /*Game game = PremiumPvP.getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();
        GamePlayer gamePlayer = gamePlayerManager.getGamePlayer(player.getUniqueId());
        if(gamePlayer == null || gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;*/

        // Don't do anything if the world has no profile
        /*WorldManager worldManager = game.getWorldManager();
        WorldProfile worldProfile = worldManager.getWorldProfile(player.getWorld().getName());
        if(worldProfile != null) worldProfile.applyProfile(player);*/
    }
}
