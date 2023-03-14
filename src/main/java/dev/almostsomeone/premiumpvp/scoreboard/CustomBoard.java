package dev.almostsomeone.premiumpvp.scoreboard;

import dev.almostsomeone.premiumpvp.PremiumPvP;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static dev.almostsomeone.premiumpvp.utilities.Chat.format;

public class CustomBoard {

    private final InfoFile scoreboardFile;
    private final GamePlayerState gamePlayerState;

    private String title;
    private List<String> lines;

    public CustomBoard(final GamePlayerState gamePlayerState) {
        this.gamePlayerState = gamePlayerState;

        // Get the scoreboard file
        BoardManager boardManager = PremiumPvP.getGame().getBoardManager();
        scoreboardFile = boardManager.getScoreboardFile();

        // Get the yaml configuration
        YamlConfiguration config = scoreboardFile.get();

        // Make sure the scoreboard is enabled
        String stateName = gamePlayerState.name().toLowerCase(Locale.ROOT);
        String configPrefix = "scoreboards." + stateName;
        if(!config.getBoolean(configPrefix + ".enabled", true)) return;

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
        // Make sure the ScoreboardManager is initialized
        if (player.getServer().getScoreboardManager() == null) return;

        // Make a personal scoreboard for the player if the player still uses the main scoreboard
        if (player.getScoreboard().equals(player.getServer().getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(player.getServer().getScoreboardManager().getNewScoreboard());

        // Get the scoreboard
        Scoreboard board = player.getScoreboard();

        // Get the sidebar objective
        Objective objective = board.getObjective("ppvp_sidebar");
        if (objective == null) objective = board.registerNewObjective("ppvp_sidebar", "dummy", format(player, title), RenderType.HEARTS);

        int emptyCount = 0;
        // Go through all lines in the array
        for(int score = lines.size(); score > 0; score--) {
            String line = lines.get(lines.size() - score);

            // Deal with {empty} lines
            if(line.toLowerCase(Locale.ROOT).trim().equals("{empty}")) {
                line = "&" + emptyCount;
                emptyCount++;
            }

            // Replace the entry with the targeted score
            replaceScore(objective, score-1, format(player, line));
        }

        // If the scoreboard is initialized and the scoreboard has more entries than the lines array, remove the extra entries
        if(objective.getScoreboard() != null && objective.getScoreboard().getEntries().size() > lines.size()) {
            for(int score = objective.getScoreboard().getEntries().size(); score > lines.size()-2; score--)
                replaceScore(objective, score, "");
        }

        // If the objective is not shown in the sidebar, set the objective's display slot to the sidebar.
        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR)
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public boolean isEnabled() {
        return lines != null && lines.size() > 0;
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
            Objects.requireNonNull(objective.getScoreboard()).resetScores(getEntryFromScore(objective, score));
        }
        objective.getScore(name).setScore(score);
    }

}
