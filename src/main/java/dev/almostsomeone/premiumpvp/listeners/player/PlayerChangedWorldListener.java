package dev.almostsomeone.premiumpvp.listeners.player;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.world.WorldManager;
import dev.almostsomeone.premiumpvp.world.WorldProfile;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void playerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        // Don't do anything if the player is not in the game
        Game game = Main.getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();
        GamePlayer gamePlayer = gamePlayerManager.getGamePlayer(player.getUniqueId());
        if(gamePlayer == null || gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;

        // Don't do anything if the world has no profile
        WorldManager worldManager = game.getWorldManager();
        WorldProfile worldProfile = worldManager.getWorldProfile(player.getWorld().getName());
        if(worldProfile != null) worldProfile.applyProfile(player);
    }
}
