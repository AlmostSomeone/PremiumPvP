package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Configuration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;
import static dev.almostsomeone.premiumpvp.utilities.Chat.format;

class CoinsCMD extends CommandBuilder {

    CoinsCMD(@Nonnull Plugin plugin, @Nonnull Configuration configuration) {
        super(plugin, configuration, "commands.coins");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(color(configuration.getMessages().get("global.only-players")));
            return true;
        }

        if (getPermission() != null && !player.hasPermission(getPermission())) {
            player.sendMessage(format(player, getPermissionMessage()));
            return true;
        }

        player.sendMessage(format(player, configuration.getMessages().get("commands.coins.balance")));
        return true;
    }
}