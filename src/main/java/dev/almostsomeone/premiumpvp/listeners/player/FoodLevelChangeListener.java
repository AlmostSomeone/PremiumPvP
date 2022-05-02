package dev.almostsomeone.premiumpvp.listeners.player;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldManager;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldProfile;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;

        // Don't do anything if the player is not in the game
        Game game = Main.getInstance().getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();
        if(gamePlayerManager.getGamePlayer(player.getUniqueId()) == null) return;

        // Don't do anything if the world has no profile
        WorldManager worldManager = game.getWorldManager();
        WorldProfile worldProfile = worldManager.getWorldProfile(player.getWorld().getName());
        if(worldProfile == null) return;

        if(!worldProfile.getHunger())
            event.setCancelled(true);
    }
}
