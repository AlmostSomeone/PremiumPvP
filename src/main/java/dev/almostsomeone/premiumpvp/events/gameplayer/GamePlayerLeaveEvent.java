package dev.almostsomeone.premiumpvp.events.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public class GamePlayerLeaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @Nonnull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    private final GamePlayer gamePlayer;

    public GamePlayerLeaveEvent(UUID uuid) {
        Game game = Main.getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        gamePlayer = gamePlayerManager.getGamePlayer(uuid);
        if(gamePlayer == null) return;
        if(gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) return;

        gamePlayer.setGamePlayerState(GamePlayerState.NONE);

        // Save the GamePlayers user
        User user = gamePlayer.getUser();
        YamlConfiguration config = Settings.getConfig();
        if(config.getBoolean("performance.caching.write-on-leave", false)) user.save();
        if(Settings.getConfig().getBoolean("performance.remove-on-leave", false)) user.unload();

        // Execute the leave command
        String command = config.getString("participate.leave.execute-command", "")
                .replaceAll("\\{player}", gamePlayer.getPlayer().getName());
        if(command.length() > 0) Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
