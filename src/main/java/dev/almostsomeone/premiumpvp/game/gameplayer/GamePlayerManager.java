package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerLoadEvent;
import dev.almostsomeone.premiumpvp.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayerManager {

    private final Game game;
    private final List<GamePlayer> gamePlayers;

    public GamePlayerManager(Game game) {
        this.game = game;
        gamePlayers = new ArrayList<>();
    }

    public void loadGamePlayers() {
        for(Player player : Bukkit.getOnlinePlayers())
            Bukkit.getPluginManager().callEvent(new GamePlayerLoadEvent(game, player.getUniqueId()));
    }

    public void saveAll() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gamePlayer = getGamePlayer(player.getUniqueId());
            gamePlayer.getUser().save();
        }
    }

    public List<GamePlayer> getGamePlayers() {
        return new ArrayList<>(gamePlayers);
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        if(getGamePlayer(gamePlayer.getUniqueId()) != null) return;
        gamePlayers.add(gamePlayer);
    }
}
