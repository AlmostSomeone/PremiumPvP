package dev.almostsomeone.premiumpvp.scoreboard;

import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class BoardManager {

    private final Plugin plugin;
    private final InfoFile scoreboardFile;
    private final Map<String, CustomBoard> boards;
    private BukkitRunnable refreshTimer;

    public BoardManager(final Plugin plugin) {
        this.plugin = plugin;

        scoreboardFile = new InfoFile(plugin, "", "scoreboard.yml");
        boards = new HashMap<>();
    }

    public void reloadBoard() {
        scoreboardFile.load();
        loadBoard();
    }

    public void loadBoard() {
        // Get the configuration
        YamlConfiguration config = scoreboardFile.get();

        // Clear the list of boards
        boards.clear();

        // Make sure the scoreboard is enabled
        if (!config.getBoolean("settings.enable", true)) return;

        // Load scoreboards
        for (String stateName : Objects.requireNonNull(config.getConfigurationSection("scoreboards")).getKeys(false)) {
            try {
                /*GamePlayerState gamePlayerState = GamePlayerState.valueOf(stateName.toUpperCase(Locale.ROOT));
                boards.put(stateName.toUpperCase(Locale.ROOT), new CustomBoard(gamePlayerState));*/
            } catch (IllegalArgumentException exception) {
                plugin.getLogger().log(Level.WARNING, () -> "The GameState " + stateName.toUpperCase(Locale.ROOT) + " is not valid. Skipping scoreboard configuration.");
            }
        }

        // Get the refresh ticks
        int ticks = config.getInt("settings.refresh", 20);

        // Prepare the timer
        if (refreshTimer != null)
            refreshTimer.cancel();

        // Start the timer if the tick is higher than 0
        if (ticks <= 0) return;
        //refreshTimer = new RefreshTimer(game);
        refreshTimer.runTaskTimerAsynchronously(plugin, 20, ticks);
    }

    public void showBoard(Player player) {
        // Get the configuration
        YamlConfiguration config = scoreboardFile.get();

        // Make sure the scoreboard is enabled
        if (!config.getBoolean("settings.enable", true)) {
        }

        // Show the scoreboard
        /*CustomBoard targetBoard = boards.get(gamePlayer.getGamePlayerState().name());
        if(targetBoard == null || !targetBoard.isEnabled()) {
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            if(scoreboardManager == null) return;
            gamePlayer.getPlayer().setScoreboard(scoreboardManager.getMainScoreboard());
            return;
        }
        targetBoard.updateBoard(gamePlayer.getPlayer());*/
    }

    public InfoFile getScoreboardFile() {
        return scoreboardFile;
    }
}
