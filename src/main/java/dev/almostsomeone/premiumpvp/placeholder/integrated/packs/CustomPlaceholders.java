package dev.almostsomeone.premiumpvp.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.placeholder.integrated.PlaceholderPack;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;

public class CustomPlaceholders extends PlaceholderPack {

    @Override
    public @Nonnull String getIdentifier() {
        return "premiumpvp";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        /*Game game = PremiumPvP.getGame();
        GamePlayerManager gamePlayerManager = game.getGamePlayerManager();

        // Make sure the game player exists
        GamePlayer gamePlayer = gamePlayerManager.getGamePlayer(offlinePlayer.getUniqueId());
        if(gamePlayer == null) return null;

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
            case "player_damagedealt" -> user.getStatistics().getDamageDealt().toString();
            case "player_damagereceived" -> user.getStatistics().getDamageReceived().toString();
            case "player_arrowshit" -> user.getStatistics().getArrowsHit().toString();
            case "player_arrowsshot" -> user.getStatistics().getArrowsShot().toString();
            case "player_bountiesclaimed" -> user.getStatistics().getBountiesClaimed().toString();
            default -> null;
        };*/
        return null; //remove
    }
}
