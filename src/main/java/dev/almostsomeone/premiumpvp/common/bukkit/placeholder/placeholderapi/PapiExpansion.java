package dev.almostsomeone.premiumpvp.common.bukkit.placeholder.placeholderapi;

import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.packs.CustomPlaceholders;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.integrated.PlaceholderPack;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PapiExpansion extends PlaceholderExpansion {

    private final Plugin plugin;
    private final PlaceholderPack placeholderPack;

    public PapiExpansion(final Plugin plugin) {
        this.plugin = plugin;
        this.placeholderPack = new CustomPlaceholders();
    }

    @Override
    public @NotNull String getAuthor() {
        return "AlmostSomeone";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "PremiumPvP";
    }

    @Override
    public @NotNull String getVersion() {
        return "v" + this.plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        return this.placeholderPack.apply(player, params);
    }
}
