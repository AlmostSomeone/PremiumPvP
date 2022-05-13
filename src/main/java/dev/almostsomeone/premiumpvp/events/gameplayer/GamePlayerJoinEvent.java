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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamePlayerJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
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

        User user = this.gamePlayer.getUser();

        FileConfiguration config = Main.getInstance().getConfig();

        // Load the UserLeveling data
        UserLeveling userLeveling = user.getLeveling();
        if(!userLeveling.isLoaded() || (config.isSet("performance.caching.read-on-join") && config.getBoolean("performance.caching.read-on-join")))
            userLeveling.load();

        // Load the UserEconomy data
        UserEconomy userEconomy = user.getEconomy();
        if(!userEconomy.isLoaded() || (config.isSet("performance.caching.read-on-join") && config.getBoolean("performance.caching.read-on-join")))
            userEconomy.load();

        // Load the UserStatistics data
        UserStatistics userStatistics = user.getStatistics();
        if(!userStatistics.isLoaded() || (config.isSet("performance.caching.read-on-join") && config.getBoolean("performance.caching.read-on-join")))
            userStatistics.load();

        this.gamePlayer.setGamePlayerState(GamePlayerState.LOBBY);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
