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
        // Loading the configuration and messages
        this.getLogger().log(Level.INFO, "Loading configuration...");
        this.config = new Config(this);
        this.messages = new Messages(this);

        // Loading bStats metrics
        this.getLogger().log(Level.INFO, "Loading bStats metrics...");
        Metrics metrics = new Metrics(this, 14487);
        String language = (this.config.get().isSet("language") ? this.config.get().getString("language") : "EN");
        metrics.addCustomChart(new SimplePie("language", () -> language));

        // Starting the updater
        new Updater(this);

        // Initialize all commands
        new KitPvPCMD(this);

        // Inform the server administrators about possible issues with non-stable releases
        String version = this.getDescription().getVersion();
        if(version.contains("-")) {
            String suffix = version.split("-")[1].toLowerCase();
            this.getLogger().log(Level.WARNING, "You are running a " + suffix + " release. Consider using our latest stable release for public servers.");
        }
    }

    public static Main getInstance(){
        return Main.getPlugin(Main.class);
    }
}
