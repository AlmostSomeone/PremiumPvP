package dev.almostsomeone.premiumpvp.chat.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.chat.placeholder.integrated.PlaceholderPack;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Objects;

public class PlayerPlaceholders extends PlaceholderPack {

    @Override
    public @Nonnull String getIdentifier() {
        return "player";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        if(!offlinePlayer.isOnline())
            return null;
        return switch (param.toLowerCase(Locale.ROOT)) {
            case "name" -> offlinePlayer.getName();
            case "displayname" -> Objects.requireNonNull(offlinePlayer.getPlayer()).getDisplayName();
            case "health" -> String.valueOf(Math.round(Objects.requireNonNull(offlinePlayer.getPlayer()).getHealth()));
            default -> null;
        };
    }
}
