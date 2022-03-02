package dev.almostsomeone.premiumpvp.utilities;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String format(Player player, String message) {
        return color(Main.getInstance().placeholder.setPlaceholders(player, message));
    }
}
