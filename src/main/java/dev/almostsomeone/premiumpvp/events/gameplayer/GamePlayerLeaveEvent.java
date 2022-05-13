package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamePlayerLeaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private final GamePlayer gamePlayer;

    public GamePlayerLeaveEvent(UUID uuid) {
        Game game = Main.getInstance().getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        this.gamePlayer = gamePlayerManager.getGamePlayer(uuid);
        if(gamePlayer == null) return;
        if(this.gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;

        this.gamePlayer.setGamePlayerState(GamePlayerState.NONE);

        // Save the GamePlayers user
        User user = this.gamePlayer.getUser();
        FileConfiguration config = Main.getInstance().getConfig();
        if(config.isSet("performance.caching.write-on-leave") && config.getBoolean("performance.caching.write-on-leave"))
            user.save();

        this.gamePlayer.getUser().unload();
        if(config.isSet("performance.remove-on-leave") && config.getBoolean("performance.remove-on-leave"))
            user.unload();
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
