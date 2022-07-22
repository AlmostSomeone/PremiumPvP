package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.data.user.groups.UserEconomy;
import dev.almostsomeone.premiumpvp.data.user.groups.UserLeveling;
import dev.almostsomeone.premiumpvp.data.user.groups.UserStatistics;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.world.WorldProfile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.util.UUID;

public class GamePlayerJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @Nonnull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private final GamePlayer gamePlayer;

    public GamePlayerJoinEvent(Game game, UUID uuid) {
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        // Get the GamePlayer object
        if(gamePlayerManager.getGamePlayer(uuid) != null) gamePlayer = gamePlayerManager.getGamePlayer(uuid);
        else gamePlayer = new GamePlayer(uuid);

        boolean readOnJoin = Settings.getConfig().getBoolean("performance.caching.read-on-join", false);

        // Load the user data if the plugin should read the player's data on join
        User user = gamePlayer.getUser();
        // UserLeveling
        UserLeveling userLeveling = user.getLeveling();
        if(!userLeveling.isLoaded() || readOnJoin) userLeveling.load();
        // UserEconomy
        UserEconomy userEconomy = user.getEconomy();
        if(!userEconomy.isLoaded() || readOnJoin) userEconomy.load();
        // UserStatistics
        UserStatistics userStatistics = user.getStatistics();
        if(!userStatistics.isLoaded() || readOnJoin) userStatistics.load();

        // Teleport the player to the lobby
        game.getLobby().teleport(gamePlayer);

        // Apply world profile
        WorldProfile worldProfile = game.getWorldManager().getWorldProfile(gamePlayer.getPlayer().getWorld().getName());
        if(worldProfile != null) worldProfile.applyProfile(gamePlayer.getPlayer());

        // Set the GamePlayer to the lobby
        gamePlayer.setGamePlayerState(GamePlayerState.LOBBY);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
