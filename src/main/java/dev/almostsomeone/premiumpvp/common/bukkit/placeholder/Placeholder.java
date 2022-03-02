package dev.almostsomeone.premiumpvp.common.bukkit.placeholder;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Placeholder {

    private String method;
    private IntegratedPlaceholders integratedPlaceholders;

    public Placeholder(final Plugin plugin) {
        Plugin placeholderAPI = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if(placeholderAPI != null) {
            this.method = "PlaceholderAPI";
            plugin.getLogger().log(Level.INFO, "Hooking into PlaceholderAPI v" + placeholderAPI.getDescription().getVersion());
        } else {
            method = "PremiumPvP";
            plugin.getLogger().log(Level.INFO, "No PlaceholderAPI found, hooking into PremiumPvP's integrated placeholders.");
            integratedPlaceholders = new IntegratedPlaceholders();
        }
    }

    public String setPlaceholders(Player player, String string) {
        if(method == null)
            return integratedPlaceholders.setPlaceholders(player, string);;

        switch(method) {
            case "PlaceholderAPI":
                return PlaceholderAPI.setPlaceholders(player, string);
            default:
                return integratedPlaceholders.setPlaceholders(player, string);
        }
    }
}
