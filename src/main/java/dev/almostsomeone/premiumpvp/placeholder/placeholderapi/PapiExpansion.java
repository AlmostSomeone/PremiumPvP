package dev.almostsomeone.premiumpvp.placeholder.placeholderapi;

import dev.almostsomeone.premiumpvp.placeholder.integrated.packs.CustomPlaceholders;
import dev.almostsomeone.premiumpvp.placeholder.integrated.PlaceholderPack;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class PapiExpansion extends PlaceholderExpansion {

    private final Plugin plugin;
    private final PlaceholderPack placeholderPack;

    public PapiExpansion(final Plugin plugin) {
        this.plugin = plugin;
        placeholderPack = new CustomPlaceholders();
    }

    @Override
    public @Nonnull String getAuthor() {
        return "AlmostSomeone";
    }

    @Override
    public @Nonnull String getIdentifier() {
        return "PremiumPvP";
    }

    @Override
    public @Nonnull String getVersion() {
        return "v" + this.plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, @Nonnull String params) {
        return placeholderPack.apply(player, params);
    }
}
