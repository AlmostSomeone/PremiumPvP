package dev.almostsomeone.premiumpvp.utilities;

import dev.almostsomeone.premiumpvp.placeholder.Placeholder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String format(Player player, String message) {
        return color(Placeholder.setPlaceholders(player, message));
    }
}
