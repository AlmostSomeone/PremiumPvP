package dev.almostsomeone.premiumpvp.common.bukkit.placeholder;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class IntegratedPlaceholders {

    public String setPlaceholders(Player player, String string) {
        if(string == null)
            return null;
        if(player == null)
            return string;
        Server server = Bukkit.getServer();
        return string
                // Player
                .replaceAll("%player_name%", player.getName())
                .replaceAll("%player_displayname%", player.getDisplayName())
                .replaceAll("%player_health%", String.valueOf((int)Math.round(player.getHealth())))

                // Server
                .replaceAll("%server_name%", server.getName())
                .replaceAll("%server_online%", String.valueOf(server.getOnlinePlayers().size()))
                .replaceAll("%server_max_players%", String.valueOf(server.getMaxPlayers()))
                ;
    }
}
