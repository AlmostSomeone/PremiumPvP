package dev.almostsomeone.premiumpvp.objects;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Locale;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.format;

public class CustomBoard {

    private InfoFile scoreboardFile;
    private GamePlayerState gamePlayerState;

    private String title;
    private List<String> lines;

    public CustomBoard(final GamePlayerState gamePlayerState) {
        this.gamePlayerState = gamePlayerState;

        // Get the scoreboard file
        BoardManager boardManager = Main.getInstance().getGame().getBoardManager();
        this.scoreboardFile = boardManager.getScoreboardFile();

        // Get the yaml configuration
        YamlConfiguration config = this.scoreboardFile.get();

        // Make sure the scoreboard is enabled
        String stateName = this.gamePlayerState.name().toLowerCase();
        String configPrefix = "scoreboards." + stateName;
        if(!config.isSet(configPrefix) && !config.isSet(configPrefix + ".enabled") && config.getBoolean(configPrefix + ".enabled")) return;

        // Make sure the title and at least 1 line is configured
        if(!config.isSet(configPrefix + ".title") && !config.isSet(configPrefix + ".lines") && config.getStringList(configPrefix + ".lines").size() <= 0) return;

        // Load the information from the config
        this.load();
    }

    public void load() {
        // Get the yaml configuration
        YamlConfiguration config = this.scoreboardFile.get();

        // Get config prefix
        String stateName = this.gamePlayerState.name().toLowerCase();
        String configPrefix = "scoreboards." + stateName;

        // Load the information from the config
        this.title = config.getString(configPrefix + ".title");
        this.lines = config.getStringList(configPrefix + ".lines");
    }

    public void updateBoard(Player player) {
        // Replace scoreboard if the player has the main scoreboard active
        if (player.getScoreboard().equals(player.getServer().getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(player.getServer().getScoreboardManager().getNewScoreboard());

        // Get the scoreboard and objective
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective(player.getName()) == null ? board.registerNewObjective(player.getName(), "dummy") : board.getObjective(player.getName());

        // Set the title and lines
        objective.setDisplayName(format(player, this.title));
        int emptyCount = 0;
        for(int score = this.lines.size(); score > 0; score--) {
            String line = this.lines.get(this.lines.size() - score).toLowerCase();
            if(line.trim().equals("{empty}")) {
                line = "&" + emptyCount;
                emptyCount++;
            }
            replaceScore(objective, score, format(player, line));
        }

        // If the objective is not shown in the sidebar, set the objective's display slot to the sidebar.
        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR)
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    private String getEntryFromScore(Objective objective, int score) {
        if (objective == null || !hasScoreTaken(objective, score))
            return null;
        for (String string : objective.getScoreboard().getEntries()) {
            if (objective.getScore(string).getScore() == score)
                return objective.getScore(string).getEntry();
        }
        return null;
    }

    private boolean hasScoreTaken(Objective objective, int score) {
        for (String string : objective.getScoreboard().getEntries()) {
            if (objective.getScore(string).getScore() == score)
                return true;
        }
        return false;
    }

    private void replaceScore(Objective objective, int score, String name) {
        if (hasScoreTaken(objective, score)) {
            if (getEntryFromScore(objective, score).equalsIgnoreCase(name))
                return;
            if (!getEntryFromScore(objective, score).equalsIgnoreCase(name))
                objective.getScoreboard().resetScores(getEntryFromScore(objective, score));
        }
        objective.getScore(name).setScore(score);
    }

}
