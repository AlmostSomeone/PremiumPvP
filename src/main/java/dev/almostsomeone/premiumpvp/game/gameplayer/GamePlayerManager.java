package dev.almostsomeone.premiumpvp.game.gameplayer;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GamePlayerManager {

    private List<GamePlayer> gamePlayers;

    public GamePlayerManager() {
        this.gamePlayers = new ArrayList<>();
    }

    public void onLoad() {
        YamlConfiguration config = Main.getInstance().config.get();

        // Join all online players if join on server join is enabled
        if(!config.isSet("participate.join.server") || config.getBoolean("participate.join.server")) {
            for(Player player : Bukkit.getOnlinePlayers())
                Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(player.getUniqueId()));
        }
    }

    public void onUnload() {
        saveAll();
    }

    public void saveAll() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gamePlayer = this.getGamePlayer(player.getUniqueId());
            gamePlayer.getUserData().save();
        }
    }

    public List<GamePlayer> getGamePlayers() {
        List<GamePlayer> copy = new ArrayList<>(this.gamePlayers);
        return copy;
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        Optional<GamePlayer> result = this.getGamePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getUniqueId().equals(uuid))
                .findFirst();
        return result.orElse(null);
    }

    public boolean addGamePlayer(GamePlayer gamePlayer) {
        if(this.getGamePlayer(gamePlayer.getUniqueId()) != null)
            return false;
        this.gamePlayers.add(gamePlayer);
        return true;
    }
}