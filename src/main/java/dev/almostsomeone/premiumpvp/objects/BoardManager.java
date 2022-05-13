package dev.almostsomeone.premiumpvp.objects;

import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class BoardManager {

    private final Plugin plugin;
    private final Game game;
    private final InfoFile scoreboardFile;

    private BukkitRunnable refreshTimer;

    private final Map<String, CustomBoard> boards;

    public BoardManager(final Plugin plugin, final Game game) {
        this.plugin = plugin;
        this.game = game;

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
        // Get the configuration
        YamlConfiguration config = scoreboardFile.get();

        // Clear the list of boards
        this.boards.clear();

        // Make sure the scoreboard is enabled
        if(!config.isSet("settings.enable") || !config.getBoolean("settings.enable")) return;

        // Load scoreboards
        for(String stateName : config.getConfigurationSection("scoreboards").getKeys(false)) {
            if(GamePlayerState.valueOf(stateName) == null) {
                plugin.getLogger().log(Level.WARNING, () -> "The GameState " + stateName.toUpperCase(Locale.ROOT) + " is not valid. Skipping scoreboard configuration.");
                continue;
            }
            GamePlayerState gamePlayerState = GamePlayerState.valueOf(stateName);
            this.boards.put(stateName, new CustomBoard(gamePlayerState));
        }

        // Get the refresh ticks
        int ticks = 0;
        if(config.isSet("settings.refresh"))
            ticks = config.getInt("settings.refresh");

        // Prepare the timer
        if(this.refreshTimer != null)
            this.refreshTimer.cancel();

        // Start the timer if the tick is higher than 0
        if(ticks <= 0) return;
        this.refreshTimer = new RefreshTimer(this.game);
        this.refreshTimer.runTaskTimerAsynchronously(plugin, 20, ticks);
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
