package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GamePlayerManager {

    private final List<GamePlayer> gamePlayers;

    public GamePlayerManager() {
        this.gamePlayers = new ArrayList<>();
    }

    public void loadGamePlayers() {
        for(Player player : Bukkit.getOnlinePlayers())
            Bukkit.getPluginManager().callEvent(new GamePlayerLoadEvent(player.getUniqueId()));
    }

    public void saveGamePlayers() {
        saveAll();
    }

    public void saveAll() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gamePlayer = this.getGamePlayer(player.getUniqueId());
            gamePlayer.getUser().save();
        }
    }

    public List<GamePlayer> getGamePlayers() {
        return new ArrayList<>(this.gamePlayers);
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        Optional<GamePlayer> result = this.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getUniqueId().equals(uuid))
                .findFirst();
        return result.orElse(null);
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        if(this.getGamePlayer(gamePlayer.getUniqueId()) != null)
            return;
        this.gamePlayers.add(gamePlayer);
    }
}
