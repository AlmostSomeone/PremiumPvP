package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;

import static dev.almostsomeone.premiumpvp.chat.Chat.color;

public class Version {

    private final Plugin plugin;

    public Version(Plugin plugin) {
        this.plugin = plugin;

        // Inform the server administrators about possible issues with non-stable releases
        if (getVersion().contains("-")) {
            String suffix = getVersion().split("-")[1].toLowerCase(Locale.ROOT);
            plugin.getLogger().log(Level.WARNING, () -> "You are running a " + suffix + " release. Consider using our latest stable release for public servers.");
        }

        checkForUpdate();
    }

    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    private void checkForUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // The request is executed asynchronously as to not block the main thread.
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    // Check if the update notify is disabled
                    if(!Settings.getBoolean("update-notify", true)) {
                        cancel(); // Cancel the runnable as checking for updates is disabled.
                        return;
                    }

                    // Cancel the runnable as a development release has been detected.
                    if(getVersion().endsWith("-dev")) {
                        return;
                    }

                    // Request the current version of your plugin on SpigotMC.
                    String newestVersion;
                    try {
                        final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=46196").openConnection();
                        connection.setRequestMethod("GET");
                        newestVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                    } catch (final IOException exception) {
                        plugin.getLogger().log(Level.WARNING, () -> "Failed to check for updates.");
                        exception.printStackTrace();
                        cancel();
                        return;
                    }

                    // Check if the requested version is the same as the one from this plugin
                    if (getVersion().equals(newestVersion)) return;

                    plugin.getLogger().log(Level.INFO, () -> "A new version of PremiumPvP is available: " + newestVersion + ". You can download it here: https://www.spigotmc.org/resources/46196/updates");

                    // Register the PlayerJoinEvent
                    Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().registerEvents(new Listener() {
                        @EventHandler(priority = EventPriority.MONITOR)
                        public void onPlayerJoin(final PlayerJoinEvent event) {
                            final Player player = event.getPlayer();
                            if (!player.hasPermission("premiumpvp.update")) return;
                            player.sendMessage(color("&cA new version of PremiumPvP is available: &4" + newestVersion + "&c. You can download it here: &4&nhttps://www.spigotmc.org/resources/46196/updates"));
                        }
                    }, plugin));

                    cancel(); // Cancel the runnable as an update has been found.
                });
            }
        }.runTaskTimer(plugin, 0, 12_000);
    }
}
