package dev.almostsomeone.premiumpvp.objects;

import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class BoardManager {

    private final Plugin plugin;
    private final InfoFile scoreboardFile;

    private Map<String, CustomBoard> boards;

    public BoardManager(final Plugin plugin) {
        this.plugin = plugin;

        // Get and load the scoreboard file
        this.scoreboardFile = new InfoFile(plugin, "", "scoreboard.yml");
        this.scoreboardFile.load();

        this.boards = new HashMap<>();
    }

    public void reloadBoard() {
        this.scoreboardFile.load();

        this.loadBoard();
    }

    public void loadBoard() {
        this.boards.clear();

        // Get the configuration
        YamlConfiguration config = scoreboardFile.get();

        // Make sure the scoreboard is enabled
        if(!config.isSet("settings.enable") || !config.getBoolean("settings.enable")) return;

        // Load scoreboards
        for(String stateName : config.getConfigurationSection("scoreboards").getKeys(false)) {
            stateName = stateName.toUpperCase();
            if(GamePlayerState.valueOf(stateName) == null) {
                plugin.getLogger().log(Level.WARNING, "The GameState " + stateName + " is not valid. Skipping scoreboard configuration.");
                continue;
            }
            GamePlayerState gamePlayerState = GamePlayerState.valueOf(stateName);
            this.boards.put(stateName, new CustomBoard(gamePlayerState));
        }
    }

    public void showBoard(GamePlayer gamePlayer) {
        // Get the configuration
        YamlConfiguration config = this.scoreboardFile.get();

        // Make sure the scoreboard is enabled
        if(!config.isSet("settings.enable") || !config.getBoolean("settings.enable")) return;

        // Show the scoreboard
        CustomBoard targetBoard = this.boards.get(gamePlayer.getGamePlayerState().name());
        targetBoard.updateBoard(gamePlayer.getPlayer());
    }

    public InfoFile getScoreboardFile() {
        return this.scoreboardFile;
    }
}
