package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.util.UUID;

public class GamePlayerLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @Nonnull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private final GamePlayer gamePlayer;

    public GamePlayerLoadEvent(Game game, UUID uuid) {
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        // Create the game player if it does not exist yet
        if(gamePlayerManager.getGamePlayer(uuid) == null) gamePlayer = new GamePlayer(uuid);
        else gamePlayer = gamePlayerManager.getGamePlayer(uuid);

        // Show the player the scoreboard
        game.getBoardManager().showBoard(gamePlayer);

        // Join the server if join on server-join is enabled
        if(Settings.getConfig().getBoolean("participate.join.server", true))
            Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(game, gamePlayer.getUniqueId()));
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
