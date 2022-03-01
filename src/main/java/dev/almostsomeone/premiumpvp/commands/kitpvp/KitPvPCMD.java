package dev.almostsomeone.premiumpvp.commands.kitpvp;

import dev.almostsomeone.premiumpvp.commands.CommandBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.color;

public class KitPvPCMD extends CommandBuilder {

    private final Plugin plugin;

    private final HashMap<String, String> subCommands = new HashMap<String, String>() {{
        put("info", "Get information about the plugin");
        put("help", "Get a list of sub-commands");
        put("reload", "Reload the configurations");
        put("save", "Force the plugin to save its data");
    }};

    public KitPvPCMD(final Plugin plugin) {
        super("main-command", "kitpvp", true);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(color(messages.getMessage("commands.use-help").replaceAll("%command%", "/" + label)));
            return true;
        } else {
            switch(args[0].toLowerCase()) {
                case "info":
                    sender.sendMessage(" ");
                    sender.sendMessage("Welcome to PremiumPvP v" + plugin.getDescription().getVersion());
                    sender.sendMessage("Made by AlmostSomeone");
                    sender.sendMessage("https://github.com/AlmostSomeone/PremiumPvP");
                    sender.sendMessage(" ");
                    break;
                case "help":
                    sender.sendMessage(color(messages.getMessage("commands.helpmenu.header")
                            .replaceAll("%command%", "/" + label)));
                    subCommands.forEach((subcommand, description) ->
                            sender.sendMessage(color(messages.getMessage("commands.helpmenu.item")
                                    .replaceAll("%command%", "/" + label)
                                    .replaceAll("%subcommand%", subcommand.substring(0,1).toUpperCase() + subcommand.substring(1).toLowerCase())
                                    .replaceAll("%description%", description))));
                    sender.sendMessage(color(messages.getMessage("commands.helpmenu.footer")
                            .replaceAll("%command%", "/" + label)));
                    break;
                case "reload":
                    try {
                        config.reload();
                        messages.reload();
                        sender.sendMessage(color(messages.getMessage("commands.kitpvp.config.reload-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        sender.sendMessage(color(messages.getMessage("commands.kitpvp.config.reload-failed")));
                    }
                    break;
                case "save":
                    try {
                        //TODO Save data
                        sender.sendMessage(color(messages.getMessage("commands.kitpvp.data.save-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        sender.sendMessage(color(messages.getMessage("commands.kitpvp.data.save-failed")));
                    }
                    break;
                default:
                    sender.sendMessage(color(messages.getMessage("commands.use-help").replaceAll("%command%", "/" + label)));
                    break;
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> autoCompletes = new ArrayList<>();
        String[] subcommands = subCommands.keySet().toArray(new String[0]);

        if (args.length == 1) {
            for (String sub : subcommands)
                if (StringUtil.startsWithIgnoreCase(sub, args[0]))
                    autoCompletes.add(sub);
            return autoCompletes;
        }
        return null;
    }
}
