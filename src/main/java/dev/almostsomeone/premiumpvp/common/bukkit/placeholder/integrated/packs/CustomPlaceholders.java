package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class CustomPlaceholders extends PlaceholderPack {

    @Override
    public @NotNull String getIdentifier() {
        return "premiumpvp";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        Game game = Main.getInstance().getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        // Make sure the game player exists
        GamePlayer gamePlayer = gamePlayerManager.getGamePlayer(offlinePlayer.getUniqueId());
        if(gamePlayer == null)
            return null;

        // Make sure the user exists
        User user = gamePlayer.getUser();
        if(user == null)
            return null;

        return switch (param.toLowerCase(Locale.ROOT)) {
            // Player Leveling
            case "player_level" -> user.getLeveling().getLevel().toString();
            case "player_experience" -> user.getLeveling().getExperience().toString();

            // Player Economy
            case "player_coins" -> user.getEconomy().getCoins().toString();

            // Player Statistics
            case "player_kills" -> user.getStatistics().getKills().toString();
            case "player_assists" -> user.getStatistics().getAssists().toString();
            case "player_deaths" -> user.getStatistics().getDeaths().toString();
            case "player_killstreak" -> user.getStatistics().getKillstreak().toString();
            case "player_bestkillstreak" -> user.getStatistics().getBestKillstreak().toString();
            default -> null;
        };
    }
}
