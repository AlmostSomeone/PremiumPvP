package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class PlayerPlaceholders extends PlaceholderPack {

    @Override
    public @NotNull String getIdentifier() {
        return "player";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        if(!offlinePlayer.isOnline())
            return null;
        return switch (param.toLowerCase(Locale.ROOT)) {
            case "name" -> offlinePlayer.getName();
            case "displayname" -> offlinePlayer.getPlayer().getDisplayName();
            case "health" -> String.valueOf(Math.round(offlinePlayer.getPlayer().getHealth()));
            default -> null;
        };
    }
}
