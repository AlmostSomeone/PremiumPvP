package dev.almostsomeone.premiumpvp.chat.placeholder.integrated;

import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;

public abstract class PlaceholderPack {

    @Nonnull
    public abstract String getIdentifier();

    public abstract String apply(OfflinePlayer offlinePlayer, String param);
}
