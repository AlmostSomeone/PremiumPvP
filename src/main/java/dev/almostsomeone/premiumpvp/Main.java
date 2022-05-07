package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.commands.KitPvPCMD;
import dev.almostsomeone.premiumpvp.commands.WorldCMD;
import dev.almostsomeone.premiumpvp.common.bukkit.placeholder.Placeholder;
import dev.almostsomeone.premiumpvp.common.bukkit.world.VoidGenerator;
import dev.almostsomeone.premiumpvp.common.nms.NMS;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.listeners.ListenerHandler;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import dev.almostsomeone.premiumpvp.storage.Messages;
import dev.almostsomeone.premiumpvp.storage.Storage;
import dev.almostsomeone.premiumpvp.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    // Get the configuration and messages
    public InfoFile config;
    public Messages messages;

    // Instances
    private Game game;
    private Placeholder placeholder;
    private Storage storage;

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id) {
        return new VoidGenerator();
    }

    @Override
    public void onEnable() {
        this.onStartup();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, this::onStarted);
    }

    @Override
    public void onDisable() {
        this.game.getGamePlayerManager().saveGamePlayers();
        this.storage.closePool();
    }

    private void onStartup() {
        // Loading the configuration
        this.config = new InfoFile(this, "", "config.yml");
        this.config.load();

        // Loading messages
        this.messages = new Messages(this);

        // Hooking into NMS
        new NMS(this);
    }

    private void onStarted() {
        // Load the storage
        this.storage = new Storage(this);

        // Create tables for the user
        // TODO Find a better way to automatically create tables
        new User(UUID.randomUUID()).createTables();

        // Preparing placeholders
        this.placeholder = new Placeholder(this);

        // Initialize game
        this.game = new Game(this);
        this.game.loadGame();

        // Register all listeners
        new ListenerHandler(this);

        // Load all game players
        this.game.getGamePlayerManager().loadGamePlayers();

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

    public Game getGame() {
        return this.game;
    }

    public Placeholder getPlaceholder() {
        return this.placeholder;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }
}
