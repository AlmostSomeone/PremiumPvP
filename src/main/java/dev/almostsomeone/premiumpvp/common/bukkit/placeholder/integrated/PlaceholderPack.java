package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public abstract class PlaceholderPack {

    @NotNull
    public abstract String getIdentifier();

    public abstract String apply(OfflinePlayer offlinePlayer, String param);
}
