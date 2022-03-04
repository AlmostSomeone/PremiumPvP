package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import dev.almostsomeone.premiumpvp.data.objects.UserData;
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

        // Make sure the user data exists
        UserData userData = gamePlayer.getUserData();
        if(userData == null)
            return null;

        switch (param.toLowerCase()){
            case "player_level":
                return userData.getUserLeveling().getLevel().toString();
            case "player_experience":
                return userData.getUserLeveling().getExperience().toString();
        }
        return null;
    }
}
