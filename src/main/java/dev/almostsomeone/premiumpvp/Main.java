package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.commands.kitpvp.KitPvPCMD;
import dev.almostsomeone.premiumpvp.utilities.*;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    // Get the configuration and messages
    public Config config;
    public Messages messages;

    @Override
    public void onEnable() {
        // Initialize the configuration and messages
        config = new Config(this);
        messages = new Messages(this);

        // Load the configuration and messages
        config.loadConfig();
        messages.loadMessages();

        // Initialize bStats
        Metrics metrics = new Metrics(this, 14487);
        metrics.addCustomChart(new SimplePie("language", () ->
                Main.getInstance().config.get().getString("language")
        ));

        // Initialize the updater
        new Updater(this).checkForUpdate();

        // Initialize all commands
        new KitPvPCMD();

        // Inform the server administrators about possible issues with non-stable releases
        String version = getDescription().getVersion();
        if(version.contains("-")) {
            String suffix = version.split("-")[1].toLowerCase();
            getLogger().log(Level.WARNING, "You are running a " + suffix + " release. Consider using our latest stable release for public servers.");
        }
    }

    public static Main getInstance(){
        return Main.getPlugin(Main.class);
    }
}
