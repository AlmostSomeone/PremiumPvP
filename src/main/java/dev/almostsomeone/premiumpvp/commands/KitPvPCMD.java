package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.utilities.Config;
import dev.almostsomeone.premiumpvp.utilities.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.color;

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
        if(args.length == 0) {
            sender.sendMessage(color(this.messages.getMessage("commands.use-help").replaceAll("%command%", "/" + label)));
            return true;
        } else {
            switch(args[0].toLowerCase()) {
                case "info":
                    sender.sendMessage(" ");
                    sender.sendMessage("Welcome to PremiumPvP v" + this.plugin.getDescription().getVersion());
                    sender.sendMessage("Made by AlmostSomeone");
                    sender.sendMessage("https://github.com/AlmostSomeone/PremiumPvP");
                    sender.sendMessage(" ");
                    break;
                case "help":
                    sender.sendMessage(color(this.messages.getMessage("commands.helpmenu.header")
                            .replaceAll("%command%", "/" + label)));
                    this.subCommands.forEach((subcommand, description) ->
                            sender.sendMessage(color(this.messages.getMessage("commands.helpmenu.item")
                                    .replaceAll("%command%", "/" + label)
                                    .replaceAll("%subcommand%", subcommand.substring(0,1).toUpperCase() + subcommand.substring(1).toLowerCase())
                                    .replaceAll("%description%", description))));
                    sender.sendMessage(color(this.messages.getMessage("commands.helpmenu.footer")
                            .replaceAll("%command%", "/" + label)));
                    break;
                case "reload":
                    try {
                        this.config.reload();
                        this.messages.reload();
                        sender.sendMessage(color(this.messages.getMessage("commands.kitpvp.config.reload-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        sender.sendMessage(color(this.messages.getMessage("commands.kitpvp.config.reload-failed")));
                    }
                    break;
                case "save":
                    try {
                        //TODO Save data
                        sender.sendMessage(color(this.messages.getMessage("commands.kitpvp.data.save-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        sender.sendMessage(color(this.messages.getMessage("commands.kitpvp.data.save-failed")));
                    }
                    break;
                default:
                    sender.sendMessage(color(this.messages.getMessage("commands.use-help").replaceAll("%command%", "/" + label)));
                    break;
            }
        }
        return false;
    }
}
