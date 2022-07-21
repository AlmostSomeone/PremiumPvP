package dev.almostsomeone.premiumpvp.listeners.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.world.WorldManager;
import dev.almostsomeone.premiumpvp.world.WorldProfile;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GamePlayerJoinListener implements Listener {

    @EventHandler
    public void gamePlayerJoin(GamePlayerJoinEvent event) {
        GamePlayer gamePlayer = event.getGamePlayer();
        Game game = Main.getGame();
        WorldManager worldManager = game.getWorldManager();

        game.getLobby().teleport(gamePlayer);

        // Apply world profile
        WorldProfile worldProfile = worldManager.getWorldProfile(gamePlayer.getPlayer().getWorld().getName());
        if(worldProfile != null)
            worldProfile.applyProfile(gamePlayer.getPlayer());
    }
}
