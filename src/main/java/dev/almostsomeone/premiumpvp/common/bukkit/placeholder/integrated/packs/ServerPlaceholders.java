package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ServerPlaceholders extends PlaceholderPack {

    @Override
    public @NotNull String getIdentifier() {
        return "server";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        Server server = Bukkit.getServer();

        switch (param.toLowerCase(Locale.ROOT)) {
            case "name":
                return server.getName();
            case "online":
                return String.valueOf(server.getOnlinePlayers().size());
            case "max_players":
                return String.valueOf(server.getMaxPlayers());
        }
        return null;
    }
}
