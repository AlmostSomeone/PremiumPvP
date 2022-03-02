package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.commands.KitPvPCMD;
import dev.almostsomeone.premiumpvp.commands.WorldCMD;
import dev.almostsomeone.premiumpvp.common.bukkit.world.VoidGenerator;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldManager;
import dev.almostsomeone.premiumpvp.common.nms.NMS;
import dev.almostsomeone.premiumpvp.utilities.*;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    // Get the configuration and messages
    public Config config;
    public Messages messages;

    public WorldManager worldManager;

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new VoidGenerator();
    }

    @Override
    public void onEnable() {
        this.onStartup();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, this::onStarted);
    }

    private void onStartup() {
        // Loading the configuration and messages
        this.config = new Config(this);
        this.messages = new Messages(this);

        // Loading bStats metrics
        this.getLogger().log(Level.INFO, "Loading bStats metrics...");
        Metrics metrics = new Metrics(this, 14487);
        String language = (this.config.get().isSet("language") ? this.config.get().getString("language") : "EN");
        metrics.addCustomChart(new SimplePie("language", () -> language));

        // Hooking into NMS
        new NMS(this);
    }

    private void onStarted() {
        // Load Worlds
        this.worldManager = new WorldManager(getInstance());

        // Initialize all commands
        new KitPvPCMD(getInstance());
        new WorldCMD();

        // Starting the updater
        new Updater(getInstance());

        // Inform the server administrators about possible issues with non-stable releases
        String version = this.getDescription().getVersion();
        if (version.contains("-")) {
            String suffix = version.split("-")[1].toLowerCase();
            this.getLogger().log(Level.WARNING, "You are running a " + suffix + " release. Consider using our latest stable release for public servers.");
        }
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }
}
