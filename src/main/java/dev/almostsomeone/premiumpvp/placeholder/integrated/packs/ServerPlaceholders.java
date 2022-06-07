package dev.almostsomeone.premiumpvp.placeholder.integrated.packs;

import dev.almostsomeone.premiumpvp.placeholder.integrated.PlaceholderPack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import javax.annotation.Nonnull;
import java.util.Locale;

public class ServerPlaceholders extends PlaceholderPack {

    @Override
    public @Nonnull String getIdentifier() {
        return "server";
    }

    @Override
    public String apply(OfflinePlayer offlinePlayer, String param) {
        Server server = Bukkit.getServer();

        return switch (param.toLowerCase(Locale.ROOT)) {
            case "name" -> server.getName();
            case "online" -> String.valueOf(server.getOnlinePlayers().size());
            case "max_players" -> String.valueOf(server.getMaxPlayers());
            default -> null;
        };
    }
}
