package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

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

        switch (param.toLowerCase()){
            // Player Leveling
            case "player_level":
                return user.getLeveling().getLevel().toString();
            case "player_experience":
                return user.getLeveling().getExperience().toString();

            // Player Economy
            case "player_coins":
                return user.getEconomy().getCoins().toString();

            // Player Statistics
            case "player_kills":
                return user.getStatistics().getKills().toString();
            case "player_assists":
                return user.getStatistics().getAssists().toString();
            case "player_deaths":
                return user.getStatistics().getDeaths().toString();
            case "player_killstreak":
                return user.getStatistics().getKillstreak().toString();
            case "player_bestkillstreak":
                return user.getStatistics().getBestKillstreak().toString();
        }
        return null;
    }
}
