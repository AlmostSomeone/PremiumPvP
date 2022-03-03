package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.data.objects.UserData;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
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
        this.gamePlayer = new GamePlayer(uuid);

        // Get the GamePlayer's userdata
        UserData userData = this.gamePlayer.getUserData();

        // Load the UserLeveling data if it is not loaded yet.
        if(userData.getUserLeveling() == null)
            userData.loadUserLeveling();

        GamePlayerManager gamePlayerManager = Main.getInstance().getGame().getGamePlayerManager();
        gamePlayerManager.addGamePlayer(gamePlayer);

        this.gamePlayer.setIngame(true);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
