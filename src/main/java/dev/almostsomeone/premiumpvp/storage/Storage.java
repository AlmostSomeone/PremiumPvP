package dev.almostsomeone.premiumpvp.storage;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.storage.sql.MySQL;
import dev.almostsomeone.premiumpvp.storage.sql.SQL;
import dev.almostsomeone.premiumpvp.storage.sql.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public class Storage {

    private final Plugin plugin;
    private SQL sql;

    private YamlConfiguration config;

    public Storage(final Plugin plugin) {
        this.plugin = plugin;
        this.config = Main.getInstance().config.get();

        if(this.config.isSet("mysql.enabled") && this.config.getBoolean("mysql.enabled")) {
            String host = (config.isSet("mysql.host") ? config.getString("mysql.host") : "localhost");
            String port = (config.isSet("mysql.port") ? config.getString("mysql.port") : "3306");
            String database = (config.isSet("mysql.database") ? config.getString("mysql.database") : "kitpvp");
            String username = (config.isSet("mysql.username") ? config.getString("mysql.username") : "username");
            String password = (config.isSet("mysql.password") ? config.getString("mysql.password") : "password");

            this.sql = new MySQL(host, port, database, username, password);
        } else {
            File file = new File(this.plugin.getDataFolder(), "Database.db");
            if(!file.exists()) {
                try {
                    this.plugin.getLogger().log(Level.INFO, "Creating local database...");
                    file.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            this.sql = new SQLite(file);
        }

        // Set up the pool with the configured SQL
        plugin.getLogger().log(Level.INFO, "Setting up connection pool...");
        this.sql.setupPool();
    }

    public Connection getConnection() throws SQLException {
        return sql.getConnection();
    }

    public void closePool() {
        plugin.getLogger().log(Level.INFO, "Closing the connection pool...");
        this.sql.closePool();
    }
}
