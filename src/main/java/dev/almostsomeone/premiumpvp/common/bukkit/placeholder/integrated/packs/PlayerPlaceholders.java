package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlayerPlaceholders extends PlaceholderPack {

    @Override
    public @NotNull String getIdentifier() {
        return "player";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        if(!offlinePlayer.isOnline())
            return null;
        switch (param.toLowerCase()) {
            case "name":
                return offlinePlayer.getName();
            case "displayname":
                return offlinePlayer.getPlayer().getDisplayName();
            case "health":
                return String.valueOf(Math.round(offlinePlayer.getPlayer().getHealth()));
        }
        return null;
    }
}
