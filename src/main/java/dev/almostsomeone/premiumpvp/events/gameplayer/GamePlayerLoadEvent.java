package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class GamePlayerLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private GamePlayer gamePlayer;

    public GamePlayerLoadEvent(UUID uuid) {
        Game game = Main.getInstance().getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        if(gamePlayerManager.getGamePlayer(uuid) == null)
            this.gamePlayer = new GamePlayer(uuid);

        YamlConfiguration config = Main.getInstance().config.get();
        if(!config.isSet("participate.join.server") || config.getBoolean("participate.join.server")) // Check if the player should join the game on server join
            Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(this.gamePlayer.getUniqueId())); // Trigger the GamePlayerJoinEvent
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
