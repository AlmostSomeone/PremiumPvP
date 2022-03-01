package dev.almostsomeone.premiumpvp.commands.kitpvp;

import dev.almostsomeone.premiumpvp.commands.CommandBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static dev.almostsomeone.premiumpvp.utilities.ChatUtil.color;

public class KitPvPCMD extends CommandBuilder {

    public KitPvPCMD() {
        super("main-command", "kitpvp", true);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(color(messages.getMessage("global.use-help").replaceAll("%command%", "/" + label)));
            return true;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> autoCompletes = new ArrayList<>();
        String[] subcommands = {
                "info",
                "help",
                "reload",
                "save"
        };

        if (args.length == 1) {
            for(String sub : subcommands)
                if(StringUtil.startsWithIgnoreCase(sub, args[0]))
                    autoCompletes.add(sub);
            return autoCompletes;
        }
        return null;
    }
}
