package dev.almostsomeone.premiumpvp.chat.placeholder;

import dev.almostsomeone.premiumpvp.chat.placeholder.integrated.IntegratedPlaceholders;
import dev.almostsomeone.premiumpvp.chat.placeholder.integrated.PlaceholderPack;
import dev.almostsomeone.premiumpvp.chat.placeholder.placeholderapi.PapiExpansion;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Level;

public class Placeholder {

    private static boolean external = false;
    private static IntegratedPlaceholders integratedPlaceholders;

    public static void setup(final Plugin plugin) {
        Plugin placeholderAPI = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if(placeholderAPI != null) {
            external = true;
            plugin.getLogger().log(Level.INFO, () -> "Hooking into PlaceholderAPI v" + placeholderAPI.getDescription().getVersion());
            new PapiExpansion(plugin).register();
        } else {
            external = false;
            plugin.getLogger().log(Level.INFO, () -> "No PlaceholderAPI found, hooking into PremiumPvP's integrated placeholders.");
            integratedPlaceholders = new IntegratedPlaceholders();
        }
    }

    public static String setPlaceholders(OfflinePlayer player, String string) {
        if(external) return PlaceholderAPI.setPlaceholders(player, string);
        else return integratedPlaceholders.setPlaceholders(player, string);
    }

    public static List<PlaceholderPack> getRegisteredPacks(){
        if(integratedPlaceholders == null) return null;
        return integratedPlaceholders.getRegisteredPacks();
    }
}
