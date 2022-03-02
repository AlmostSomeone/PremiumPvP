package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.color;
import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.format;

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
        if(!(sender instanceof Player)) {
            sender.sendMessage(color(messages.getMessage("global.only-players")));
            return true;
        }
        Player player = (Player) sender;

        if(this.getPermission() != null && !player.hasPermission(this.getPermission())) {
            player.sendMessage(color(this.getPermissionMessage()));
            return true;
        }
        if(args.length < 1 || args.length > 2) {
            player.sendMessage(format(player, messages.getMessage("commands.world.usage")));
            return true;
        } else {
            if(args.length == 1) {
                try {
                    teleport(player, Bukkit.getWorld(args[0]));
                } catch (NullPointerException exception) {
                    player.sendMessage(format(player, messages.getMessage("commands.world.no-world")));
                    return true;
                }
            } else {
                Player target;
                try {
                    target = Bukkit.getPlayerExact(args[1]);
                } catch (Exception exception) {
                    player.sendMessage(format(player, messages.getMessage("global.target-not-online")));
                    return true;
                }
                if(target != null) {
                    try {
                        teleport(target, Bukkit.getWorld(args[0]));
                    } catch (NullPointerException exception) {
                        player.sendMessage(format(player, messages.getMessage("commands.world.no-world")));
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private void teleport(Player player, World world) throws NullPointerException {
        player.teleport(world.getHighestBlockAt(world.getSpawnLocation()).getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(format(player, messages.getMessage("commands.world.teleported")));
    }
}
