package dev.almostsomeone.premiumpvp.storage;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import dev.almostsomeone.premiumpvp.storage.sql.MySQL;
import dev.almostsomeone.premiumpvp.storage.sql.SQL;
import dev.almostsomeone.premiumpvp.storage.sql.SQLite;
import org.bukkit.Bukkit;
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

        // Set the SQL method
        String method = Settings.getString("storage.method", "sqlite");
        if (method.equalsIgnoreCase("mysql")) sql = setMySQL();
        else sql = setSQLite();

        openPool();

        // Auto-Save the data
        long interval = Settings.getInt("performance.auto-save.interval", 300) * 20L;
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> Main.getGame().save(), interval, interval);
    }

    public Connection getConnection() throws SQLException {
        return sql.getConnection();
    }

    public void openPool() {
        // Set up the pool with the configured SQL
        plugin.getLogger().log(Level.INFO, () -> "Setting up connection pool...");
        sql.setupPool();
    }

    public void closePool() {
        plugin.getLogger().log(Level.INFO, () -> "Closing the connection pool...");
        sql.closePool();
    }

    private SQL setMySQL() {
        String host = Settings.getString("storage.host", "localhost");
        String port = Settings.getString("storage.port", "3306");
        String database = Settings.getString("storage.database", "premiumpvp");
        String username = Settings.getString("storage.username", "root");
        String password = Settings.getString("storage.password", "");
        return new MySQL(host, port, database, username, password);
    }

    private SQL setSQLite() {
        File file = new File(plugin.getDataFolder(), "Database.db");
        if(!file.exists()) {
            try {
                plugin.getLogger().log(Level.INFO, () -> "Creating local database...");
                if(file.createNewFile())
                    plugin.getLogger().log(Level.INFO, () -> "Successfully generated local database");
                else
                    plugin.getLogger().log(Level.WARNING, () -> "Could not generate local database");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return new SQLite(file);
    }
}
