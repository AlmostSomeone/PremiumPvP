package dev.almostsomeone.premiumpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;

import java.util.ArrayList;
import java.util.List;

public class PluginTopic {

    private static final List<Command> commands = new ArrayList<>();

    public static void generate() {
        List<HelpTopic> commandHelpTopics = new ArrayList<>();
        commands.forEach(c -> commandHelpTopics.add(new GenericCommandHelpTopic(c)));
        Bukkit.getHelpMap().addTopic(new IndexHelpTopic(
                "PremiumPvP",
                "All commands for PremiumPvP",
                null,
                commandHelpTopics,
                "Below a list of all PremiumPvP commands:"
        ));
    }

    public static void registerCommand(Command command) {
        if(!commands.contains(command))
            commands.add(command);
    }
}
