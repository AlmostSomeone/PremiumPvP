package dev.almostsomeone.premiumpvp.game.gameplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    private final UUID uuid;

    private Boolean isIngame;

    public GamePlayer(final UUID uuid) {
        this.uuid = uuid;
        this.isIngame = false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public Boolean getIngame() {
        return this.isIngame;
    }

    public void setIngame(Boolean ingame) {
        this.isIngame = ingame;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }
}
