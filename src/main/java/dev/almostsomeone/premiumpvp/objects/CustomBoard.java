package dev.almostsomeone.premiumpvp.objects;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static dev.almostsomeone.premiumpvp.chat.Chat.format;

public class CustomBoard {

    private final InfoFile scoreboardFile;
    private final GamePlayerState gamePlayerState;

    private String title;
    private List<String> lines;

    public CustomBoard(final GamePlayerState gamePlayerState) {
        this.gamePlayerState = gamePlayerState;

        // Get the scoreboard file
        BoardManager boardManager = Main.getGame().getBoardManager();
        scoreboardFile = boardManager.getScoreboardFile();

        // Get the yaml configuration
        YamlConfiguration config = scoreboardFile.get();

        // Make sure the scoreboard is enabled
        String stateName = gamePlayerState.name().toLowerCase(Locale.ROOT);
        String configPrefix = "scoreboards." + stateName;
        if(!config.isSet(configPrefix) && !config.isSet(configPrefix + ".enabled") && config.getBoolean(configPrefix + ".enabled")) return;

        // Make sure the title and at least 1 line is configured
        if(!config.isSet(configPrefix + ".title") && !config.isSet(configPrefix + ".lines") && config.getStringList(configPrefix + ".lines").size() <= 0) return;

        // Load the information from the config
        load();
    }

    public void load() {
        // Get the yaml configuration
        YamlConfiguration config = scoreboardFile.get();

        // Get config prefix
        String stateName = gamePlayerState.name().toLowerCase(Locale.ROOT);
        String configPrefix = "scoreboards." + stateName;

        // Load the information from the config
        title = config.getString(configPrefix + ".title");
        lines = config.getStringList(configPrefix + ".lines");
    }

    public void updateBoard(Player player) {
        // Replace scoreboard if the player has the main scoreboard active
        if (player.getServer().getScoreboardManager() != null &&
                player.getScoreboard().equals(player.getServer().getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(player.getServer().getScoreboardManager().getNewScoreboard());

        // Get the scoreboard and objective
        Scoreboard board = player.getScoreboard();
        Objective objective = board.getObjective(player.getName()) == null ? board.registerNewObjective(player.getName(), "dummy", format(player, title)) : board.getObjective(player.getName());
        if(objective == null) return;

        // Set the title and line
        int emptyCount = 0;
        for(int score = lines.size(); score > 0; score--) {
            String line = lines.get(lines.size() - score);
            if(line.toLowerCase(Locale.ROOT).trim().equals("{empty}")) {
                line = "&" + emptyCount;
                emptyCount++;
            }
            replaceScore(objective, score-1, format(player, line));
        }
        if(objective.getScoreboard() != null && objective.getScoreboard().getEntries().size() > lines.size()) {
            for(int score = objective.getScoreboard().getEntries().size(); score > lines.size()-2; score--)
                replaceScore(objective, score, "");
        }

        // If the objective is not shown in the sidebar, set the objective's display slot to the sidebar.
        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR)
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    private String getEntryFromScore(Objective objective, int score) {
        if (objective == null || !hasScoreTaken(objective, score)) return null;
        for (String string : Objects.requireNonNull(objective.getScoreboard()).getEntries())
            if (objective.getScore(string).getScore() == score) return objective.getScore(string).getEntry();
        return null;
    }

    private boolean hasScoreTaken(Objective objective, int score) {
        for (String string : Objects.requireNonNull(objective.getScoreboard()).getEntries())
            if (objective.getScore(string).getScore() == score) return true;
        return false;
    }

    private void replaceScore(Objective objective, int score, String name) {
        if (hasScoreTaken(objective, score)) {
            if (getEntryFromScore(objective, score).equalsIgnoreCase(name)) return;
            if (!getEntryFromScore(objective, score).equalsIgnoreCase(name))
                Objects.requireNonNull(objective.getScoreboard()).resetScores(getEntryFromScore(objective, score));
        }
        objective.getScore(name).setScore(score);
    }

}
