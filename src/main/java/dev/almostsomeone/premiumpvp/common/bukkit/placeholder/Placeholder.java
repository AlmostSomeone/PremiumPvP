package dev.almostsomeone.premiumpvp.common.bukkit.placeholder;

import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.IntegratedPlaceholders;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.placeholderapi.PapiExpansion;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Level;

public class Placeholder {

    private final String method;

    private IntegratedPlaceholders integratedPlaceholders;

    public Placeholder(final Plugin plugin) {
        Plugin placeholderAPI = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if(placeholderAPI != null) {
            this.method = "PlaceholderAPI";

            plugin.getLogger().log(Level.INFO, () -> "Hooking into PlaceholderAPI v" + placeholderAPI.getDescription().getVersion());
            new PapiExpansion(plugin).register();
        } else {
            this.method = "PremiumPvP";

            plugin.getLogger().log(Level.INFO, () -> "No PlaceholderAPI found, hooking into PremiumPvP's integrated placeholders.");
            this.integratedPlaceholders = new IntegratedPlaceholders();
        }
    }

    public String setPlaceholders(OfflinePlayer player, String string) {
        if(this.method == null)
            return this.integratedPlaceholders.setPlaceholders(player, string);

        if ("PlaceholderAPI".equals(this.method))
            return PlaceholderAPI.setPlaceholders(player, string);
        return this.integratedPlaceholders.setPlaceholders(player, string);
    }

    public List<PlaceholderPack> getRegisteredPacks(){
        if(this.integratedPlaceholders == null)
            return null;
        return this.integratedPlaceholders.getRegisteredPacks();
    }
}
