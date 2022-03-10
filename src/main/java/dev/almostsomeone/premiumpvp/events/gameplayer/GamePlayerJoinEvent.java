package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.data.user.groups.UserEconomy;
import dev.almostsomeone.premiumpvp.data.user.groups.UserLeveling;
import dev.almostsomeone.premiumpvp.data.user.groups.UserStatistics;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class GamePlayerJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private final GamePlayer gamePlayer;

    public GamePlayerJoinEvent(UUID uuid) {
        Game game = Main.getInstance().getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        if(gamePlayerManager.getGamePlayer(uuid) != null)
            this.gamePlayer = gamePlayerManager.getGamePlayer(uuid);
        else
            this.gamePlayer = new GamePlayer(uuid);

        if(!this.gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;

        this.gamePlayer.setGamePlayerState(GamePlayerState.LOBBY);

        User user = this.gamePlayer.getUser();

        // Load the UserLeveling data
        UserLeveling userLeveling = user.getLeveling();
        userLeveling.load();

        // Load the UserEconomy data
        UserEconomy userEconomy = user.getEconomy();
        userEconomy.load();

        // Load the UserStatistics data
        UserStatistics userStatistics = user.getStatistics();
        userStatistics.load();
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
