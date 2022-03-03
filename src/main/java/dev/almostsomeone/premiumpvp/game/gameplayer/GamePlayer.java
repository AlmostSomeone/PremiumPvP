package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.data.objects.UserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    private final UUID uuid;
    private Boolean ingame;

    private UserData userData;

    public GamePlayer(final UUID uuid) {
        this.uuid = uuid;
        this.ingame = false;

        this.userData = new UserData(uuid);
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

    public UserData getUserData() {
        return this.userData;
    }
}
