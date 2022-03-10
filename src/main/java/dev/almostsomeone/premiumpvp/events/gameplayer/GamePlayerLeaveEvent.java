package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;
import java.util.logging.Level;

public class GamePlayerLeaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private GamePlayer gamePlayer;

    public GamePlayerLeaveEvent(UUID uuid) {
        Game game = Main.getInstance().getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        this.gamePlayer = gamePlayerManager.getGamePlayer(uuid);
        if(gamePlayer == null) {
            Bukkit.getLogger().log(Level.WARNING, "Tried to trigger GamePlayerLeaveEvent, but GamePlayer can not be found.");
            return;
        }
        if(this.gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;

        this.gamePlayer.setGamePlayerState(GamePlayerState.NONE);

        // Save the GamePlayers user
        User user = this.gamePlayer.getUser();
        user.save();
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
