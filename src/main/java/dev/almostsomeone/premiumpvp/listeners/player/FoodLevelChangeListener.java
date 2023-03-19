package dev.almostsomeone.premiumpvp.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    private void foodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
        }

        // Don't do anything if the player is not in the game
        /*Game game = PremiumPvP.getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();
        GamePlayer gamePlayer = gamePlayerManager.getGamePlayer(player.getUniqueId());
        if(gamePlayer == null || gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;

        // Don't do anything if the world has no profile
        WorldManager worldManager = game.getWorldManager();
        WorldProfile worldProfile = worldManager.getWorldProfile(player.getWorld().getName());
        if(worldProfile != null && !worldProfile.getHunger())
            event.setCancelled(true);*/
    }
}
