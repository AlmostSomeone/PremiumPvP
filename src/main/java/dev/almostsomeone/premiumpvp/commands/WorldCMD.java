package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.color;

public class WorldCMD extends CommandBuilder {

    private final Messages messages = Main.getInstance().messages;

    public WorldCMD() {
        super("world-command");

        this.subCommands = new HashMap<>();
        for(World world : Bukkit.getWorlds())
            this.subCommands.put(world.getName(), "");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
            sender.sendMessage(color(this.getPermissionMessage()));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(color(messages.getMessage("global.only-players")));
            return true;
        }
        if(args.length < 1 || args.length > 2) {
            sender.sendMessage(color(messages.getMessage("commands.world.usage")));
            return true;
        } else {
            if(args.length == 1) {
                try {
                    teleport(((Player) sender), Bukkit.getWorld(args[0]));
                } catch (NullPointerException exception) {
                    sender.sendMessage(color(messages.getMessage("commands.world.no-world").replaceAll("%world%", args[0])));
                    return true;
                }
            } else {
                Player target;
                try {
                    target = Bukkit.getPlayerExact(args[1]);
                } catch (Exception exception) {
                    sender.sendMessage(color(messages.getMessage("global.target-not-online")));
                    return true;
                }
                if(target != null) {
                    try {
                        teleport(target, Bukkit.getWorld(args[0]));
                    } catch (NullPointerException exception) {
                        sender.sendMessage(color(messages.getMessage("commands.world.no-world").replaceAll("%world%", args[0])));
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private void teleport(Player player, World world) throws NullPointerException {
        player.teleport(world.getHighestBlockAt(world.getSpawnLocation()).getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(color(messages.getMessage("commands.world.teleported").replaceAll("%world%", world.getName())));
    }
}
