package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.utilities.Config;
import dev.almostsomeone.premiumpvp.utilities.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.color;
import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.format;

public class KitPvPCMD extends CommandBuilder {

    private final Plugin plugin;

    private final Config config = Main.getInstance().config;
    private final Messages messages = Main.getInstance().messages;

    public KitPvPCMD(final Plugin plugin) {
        super("main-command", "kitpvp", true);
        this.plugin = plugin;

        this.subCommands = new HashMap<String, String>() {{
            put("info", "Get information about the plugin");
            put("help", "Get a list of sub-commands");
            put("reload", "Reload the configurations");
            put("save", "Force the plugin to save its data");
        }};
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(color(messages.getMessage("global.only-players")));
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(format(player, this.messages.getMessage("commands.help.use-help").replaceAll("\\{command\\}", "/" + label)));
            return true;
        } else {
            switch(args[0].toLowerCase()) {
                case "info":
                    player.sendMessage(" ");
                    player.sendMessage("Welcome to PremiumPvP v" + this.plugin.getDescription().getVersion());
                    player.sendMessage("Made by AlmostSomeone");
                    player.sendMessage("https://github.com/AlmostSomeone/PremiumPvP");
                    player.sendMessage(" ");
                    break;
                case "help":
                    sender.sendMessage(format(player, this.messages.getMessage("commands.help.header")
                            .replaceAll("\\{command\\}", "/" + label)));
                    this.subCommands.forEach((subcommand, description) ->
                            player.sendMessage(format(player, this.messages.getMessage("commands.help.item")
                                    .replaceAll("\\{command\\}", "/" + label)
                                    .replaceAll("\\{subcommand\\}", subcommand.substring(0,1).toUpperCase() + subcommand.substring(1).toLowerCase())
                                    .replaceAll("\\{description\\}", description))));
                    player.sendMessage(format(player, this.messages.getMessage("commands.help.footer")
                            .replaceAll("\\{command\\}", "/" + label)));
                    break;
                case "reload":
                    try {
                        this.config.reload();
                        this.messages.reload();
                        player.sendMessage(format(player, this.messages.getMessage("commands.kitpvp.config.reload-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        player.sendMessage(format(player, this.messages.getMessage("commands.kitpvp.config.reload-failed")));
                    }
                    break;
                case "save":
                    try {
                        //TODO Save data
                        player.sendMessage(format(player, this.messages.getMessage("commands.kitpvp.data.save-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        player.sendMessage(format(player, this.messages.getMessage("commands.kitpvp.data.save-failed")));
                    }
                    break;
                default:
                    player.sendMessage(format(player, this.messages.getMessage("commands.help.use-help").replaceAll("\\{command\\}", "/" + label)));
                    break;
            }
        }
        return false;
    }
}
