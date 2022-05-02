package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
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
        this.user = new User(uuid);
        Main.getInstance().getGame().getGamePlayerManager().addGamePlayer(this);
    }

    public Player getPlayer() {
        if(!Bukkit.getOfflinePlayer(this.uuid).isOnline())
            return null;
        return Bukkit.getPlayer(this.uuid);
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public GamePlayerState getGamePlayerState() {
        return this.gamePlayerState;
    }

    public void setGamePlayerState(GamePlayerState gamePlayerState) {
        this.gamePlayerState = gamePlayerState;
        Main.getInstance().getGame().getBoardManager().showBoard(this);
    }

    public User getUser() {
        return this.user;
    }
}
