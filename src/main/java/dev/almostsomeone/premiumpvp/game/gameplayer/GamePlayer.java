package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.PremiumPvP;
import dev.almostsomeone.premiumpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    private final UUID uuid;
    private GamePlayerState gamePlayerState;
    private final User user;

    public GamePlayer(final UUID uuid) {
        this.uuid = uuid;
        setGamePlayerState(GamePlayerState.NONE);
        user = new User(uuid);
        PremiumPvP.getGame().getGamePlayerManager().addGamePlayer(this);
    }

    public Player getPlayer() {
        if(!Bukkit.getOfflinePlayer(uuid).isOnline())
            return null;
        return Bukkit.getPlayer(uuid);
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public GamePlayerState getGamePlayerState() {
        return gamePlayerState;
    }

    public void setGamePlayerState(GamePlayerState gamePlayerState) {
        this.gamePlayerState = gamePlayerState;
        PremiumPvP.getGame().getBoardManager().showBoard(this);
    }

    public User getUser() {
        return user;
    }
}
