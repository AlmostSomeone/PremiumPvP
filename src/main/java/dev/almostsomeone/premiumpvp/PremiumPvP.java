package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.commands.CommandManager;
import dev.almostsomeone.premiumpvp.data.users.UserManager;
import dev.almostsomeone.premiumpvp.listeners.ListenerHandler;
import dev.almostsomeone.premiumpvp.placeholder.Placeholder;
import dev.almostsomeone.premiumpvp.storage.Storage;
import dev.almostsomeone.premiumpvp.world.VoidGenerator;
import dev.almostsomeone.premiumpvp.world.WorldManager;
import dev.almostsomeone.premiumpvp.world.WorldProfile;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PremiumPvP extends JavaPlugin {

    private Storage storage;
    private Configuration configuration;

    // Managers
    private UserManager userManager;
    private WorldManager worldManager;

    /**
     * This method will be used by bukkit when PremiumPvP is set as the world generator.
     *
     * @return A new instance of PremiumPvP's void generator.
     */
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
        // TODO Save all data
        storage.closePool();
    }

    private void onStartup() {
        // Let's initialize and load our configuration first
        configuration = new Configuration(this);

        // Initialize the storage and open the pool
        storage = new Storage(this, configuration);
        storage.openPool();

        // Initialize, register and set up the user manager
        userManager = new UserManager(this, configuration, storage);
        getServer().getPluginManager().registerEvents(userManager, this);
        userManager.loadUsers(storage);

        /*
         * TODO
         *  - Load Kits
         *  - Load Banking
         *  - Load Users?
         */
    }

    private void onStarted() {
        // Initialize the world manager and load the worlds
        worldManager = new WorldManager(this, configuration);
        worldManager.loadWorlds();

        // Prepare the Placeholders
        Placeholder.setup(this);

        // Register all commands
        new CommandManager(this, configuration);

        // Start listening to events
        new ListenerHandler(this);

        // Send metrics to bStats
        new Metrics(this, 14487);

        // Check for updates
        new Version(this, configuration);
    }

    /**
     * Obtain the configured PremiumPvP world profile for the given world name.
     *
     * @param worldName The targeted world's name, ignoring case differences.
     * @return The profile object for the given world. This might be unset until shortly after the server finished enabling.
     */
    public WorldProfile getWorldProfile(@Nonnull String worldName) {
        return worldManager.getWorldProfile(worldName);
    }

    /**
     * Access PremiumPvP's user manager for easy access to user data.
     *
     * @return The UserManager class. This might be unset during startup.
     */
    public UserManager getUserManager() {
        return userManager;
    }
}
