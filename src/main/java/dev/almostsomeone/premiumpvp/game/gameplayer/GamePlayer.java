package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    private final UUID uuid;
    private Boolean ingame;

    private User user;

    public GamePlayer(final UUID uuid) {
        this.uuid = uuid;
        this.ingame = false;

        this.user = new User(uuid);

        Main.getInstance().getGame().getGamePlayerManager().addGamePlayer(this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public Boolean isIngame() {
        return this.ingame;
    }

    public void setIngame(Boolean ingame) {
        this.ingame = ingame;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public User getUser() {
        return this.user;
    }
}
