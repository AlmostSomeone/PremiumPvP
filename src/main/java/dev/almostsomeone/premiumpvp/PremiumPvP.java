package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.commands.PluginTopic;
import dev.almostsomeone.premiumpvp.commands.executors.CoinsCMD;
import dev.almostsomeone.premiumpvp.commands.executors.KitPvPCMD;
import dev.almostsomeone.premiumpvp.commands.executors.WorldCMD;
import dev.almostsomeone.premiumpvp.placeholder.Placeholder;
import dev.almostsomeone.premiumpvp.world.VoidGenerator;
import dev.almostsomeone.premiumpvp.data.user.User;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.listeners.ListenerHandler;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import dev.almostsomeone.premiumpvp.storage.Storage;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PremiumPvP extends JavaPlugin {

    private static Game game;
    private static Storage storage;

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id) {
        return new VoidGenerator();
    }

    @Override
    public void onEnable() {
        onStartup();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, this::onStarted);
    }

    @Override
    public void onDisable() {
        game.save();
        storage.closePool();
    }

    private void onStartup() {
        // Generate and load the settings and messages
        Settings.setup(this);

        // Register all commands
        new KitPvPCMD();
        new WorldCMD();
        new CoinsCMD();

        // Generate a topic in the servers HelpMap for improved usability.
        PluginTopic.generate();
    }

    private void onStarted() {
        new Metrics(this, 14487);

        // Initialize the storage
        storage = new Storage(this);

        // Create tables for the user
        // TODO Find a better way to automatically create tables
        new User(UUID.randomUUID()).createTables();

        // Prepare the Placeholders
        Placeholder.setup(this);

        // Set up the game instance
        game = new Game(this);
        game.loadGame();

        // Start listening to events
        new ListenerHandler(this);

        game.loadPlayers();

        // Check for updates
        new Version(this);
    }

    public static Game getGame() {
        return game;
    }

    public static Storage getStorage() {
        return storage;
    }
}
