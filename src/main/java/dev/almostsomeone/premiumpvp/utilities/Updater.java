package dev.almostsomeone.premiumpvp.utilities;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater {

    private final JavaPlugin javaPlugin;
    private final String localPluginVersion;
    private String spigotPluginVersion;

    private static final int ID = 46196;
    private static final String error_message = "&cUpdater failed to check for updates!";
    private static final String update_message = "&fA new update for PremiumPvP is available at: https://www.spigotmc.org/resources/" + ID + "/updates";

    private static final Permission update_permission = new Permission("premiumpvp.update", PermissionDefault.TRUE);

    public Updater(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.localPluginVersion = javaPlugin.getDescription().getVersion();

        this.checkForUpdate();
    }

    public void checkForUpdate() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // The request is executed asynchronously as to not block the main thread.
                Bukkit.getScheduler().runTaskAsynchronously(javaPlugin, () -> {
                    // Check if the update notify is disabled
                    FileConfiguration config = Main.getInstance().getConfig();
                    if(config.isSet("update-notify") && !config.getBoolean("update-notify")) {
                        cancel(); // Cancel the runnable as checking for updates is disabled.
                        return;
                    }

                    // Check if the server is running a development release
                    if(localPluginVersion.endsWith("-dev")) {
                        cancel(); // Cancel the runnable as a development release has been detected.
                        return;
                    }

                    // Request the current version of your plugin on SpigotMC.
                    try {
                        final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + ID).openConnection();
                        connection.setRequestMethod("GET");
                        spigotPluginVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                    } catch (final IOException e) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', error_message));
                        e.printStackTrace();
                        cancel();
                        return;
                    }

                    // Check if the requested version is the same as the one in your plugin.yml.
                    if (localPluginVersion.equals(spigotPluginVersion)) return;

                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', update_message));

                    // Register the PlayerJoinEvent
                    Bukkit.getScheduler().runTask(javaPlugin, () -> Bukkit.getPluginManager().registerEvents(new Listener() {
                        @EventHandler(priority = EventPriority.MONITOR)
                        public void onPlayerJoin(final PlayerJoinEvent event) {
                            final Player player = event.getPlayer();
                            if (!player.hasPermission(update_permission)) return;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', update_message));
                        }
                    }, javaPlugin));

                    cancel(); // Cancel the runnable as an update has been found.
                });
            }
        }.runTaskTimer(javaPlugin, 0, 12_000);
    }
}