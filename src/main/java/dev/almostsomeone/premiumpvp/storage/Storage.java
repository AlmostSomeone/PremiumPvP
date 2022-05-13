package dev.almostsomeone.premiumpvp.storage;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.storage.sql.MySQL;
import dev.almostsomeone.premiumpvp.storage.sql.SQL;
import dev.almostsomeone.premiumpvp.storage.sql.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public class Storage {

    private final Plugin plugin;
    private final SQL sql;

    public Storage(final Plugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = Main.getInstance().getConfig();

        String method = config.isSet("storage.method") ? config.getString("storage.method") : "sqlite";
        if(method == null) method = "sqlite";

        if (method.equalsIgnoreCase("mysql")) {
            // Retrieve the MySQL credentials
            String host = config.isSet("storage.host") ? config.getString("storage.host") : "localhost";
            String port = config.isSet("storage.port") ? config.getString("storage.port") : "3306";
            String database = config.isSet("storage.database") ? config.getString("storage.database") : "premiumpvp";
            String username = config.isSet("storage.username") ? config.getString("storage.username") : "root";
            String password = config.isSet("storage.password") ? config.getString("storage.password") : "";

            // Create the MySQL database connection
            this.sql = new MySQL(host, port, database, username, password);
        } else {
            // Create the SQLite database file
            File file = new File(this.plugin.getDataFolder(), "Database.db");
            if(!file.exists()) {
                try {
                    this.plugin.getLogger().log(Level.INFO, () -> "Creating local database...");
                    if(file.createNewFile())
                        this.plugin.getLogger().log(Level.INFO, () -> "Successfully generated local database");
                    else
                        this.plugin.getLogger().log(Level.WARNING, () -> "Could not generate local database");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            // Create the SQLite database connection
            this.sql = new SQLite(file);
        }

        // Set up the pool with the configured SQL
        plugin.getLogger().log(Level.INFO, () -> "Setting up connection pool...");
        this.sql.setupPool();

        // Auto-Save the data to minimize data loss on forced shutdown
        int autoSaveInterval = (config.isSet("performance.auto-save.interval") ? config.getInt("performance.auto-save.interval") : 300) * 20;
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            plugin.getLogger().log(Level.INFO, () -> "Auto-saving data...");
            Main.getInstance().getGame().getGamePlayerManager().saveAll();
            plugin.getLogger().log(Level.INFO, () -> "Finished auto-saving data");
        }, autoSaveInterval, autoSaveInterval);
    }

    public Connection getConnection() throws SQLException {
        return sql.getConnection();
    }

    public void closePool() {
        plugin.getLogger().log(Level.INFO, () -> "Closing the connection pool...");
        this.sql.closePool();
    }
}
