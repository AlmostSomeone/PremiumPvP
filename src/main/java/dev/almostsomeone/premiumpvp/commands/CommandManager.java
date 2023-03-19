package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Configuration;
import dev.almostsomeone.premiumpvp.events.configuration.ConfigurationReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements Listener {

    private static final HashMap<String, CommandBuilder> commands = new HashMap<>();

    public CommandManager(@Nonnull Plugin plugin, @Nonnull Configuration configuration) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // Register all commands
        new KitPvPCMD(plugin, configuration);
        new WorldCMD(plugin, configuration);
        new CoinsCMD(plugin, configuration);

        // Generate topic
        generate();
    }

    static void addCommand(@Nonnull String name, @Nonnull CommandBuilder commandBuilder) {
        commands.put(name, commandBuilder);
    }

    private void generate() {
        List<HelpTopic> commandHelpTopics = new ArrayList<>();
        commands.values().forEach(c -> commandHelpTopics.add(new GenericCommandHelpTopic(c)));
        Bukkit.getHelpMap().addTopic(new IndexHelpTopic(
                "PremiumPvP",
                "All commands for PremiumPvP",
                null,
                commandHelpTopics,
                "Below a list of all PremiumPvP commands:"
        ));
    }

    @EventHandler
    public void onConfigurationReload(ConfigurationReloadEvent event) {
        commands.values().forEach(CommandBuilder::reload);
    }
}
