package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;
import static dev.almostsomeone.premiumpvp.utilities.Chat.format;

class WorldCMD extends CommandBuilder {

    WorldCMD(@Nonnull Plugin plugin, @Nonnull Configuration configuration) {
        super(plugin, configuration, "commands.world");

        subCommands.put("", new HashMap<>());
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(color(configuration.getMessages().get("global.only-players")));
            return true;
        }

        if (getPermission() != null && !player.hasPermission(getPermission())) {
            player.sendMessage(format(player, getPermissionMessage()));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            player.sendMessage(format(player, configuration.getMessages().get("commands.world.usage")));
            return true;
        }

        if (args.length == 1) {
            try {
                teleport(player, Objects.requireNonNull(Bukkit.getWorld(args[0])));
            } catch (NullPointerException exception) {
                player.sendMessage(format(player, configuration.getMessages().get("commands.world.no-world")));
                return true;
            }
        } else {
            Player target;
            try {
                target = Bukkit.getPlayerExact(args[1]);
            } catch (Exception exception) {
                player.sendMessage(format(player, configuration.getMessages().get("global.target-not-online")));
                return true;
            }
            if (target != null) {
                try {
                    teleport(target, Objects.requireNonNull(Bukkit.getWorld(args[0])));
                } catch (NullPointerException exception) {
                    player.sendMessage(format(player, configuration.getMessages().get("commands.world.no-world")));
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args, Location location) throws IllegalArgumentException {
        Bukkit.getWorlds().forEach(world -> subCommands.get("").put(world.getName(), ""));
        return super.tabComplete(sender, alias, args, location);
    }

    private void teleport(Player player, World world) throws NullPointerException {
        player.teleport(world.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(format(player, configuration.getMessages().get("commands.world.teleported")));
    }
}