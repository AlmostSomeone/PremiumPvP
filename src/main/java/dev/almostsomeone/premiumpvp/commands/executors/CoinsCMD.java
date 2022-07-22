package dev.almostsomeone.premiumpvp.commands.executors;

import dev.almostsomeone.premiumpvp.commands.CommandBuilder;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;
import static dev.almostsomeone.premiumpvp.utilities.Chat.format;

public class CoinsCMD extends CommandBuilder {

    public CoinsCMD() {
        super("commands.coins");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(color(Settings.getMessage("global.only-players")));
            return true;
        }

        if (getPermission() != null && !player.hasPermission(getPermission())) {
            player.sendMessage(format(player, getPermissionMessage()));
            return true;
        }

        player.sendMessage(format(player, Settings.getMessage("commands.coins.balance")));
        return true;
    }
}
