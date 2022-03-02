package dev.almostsomeone.premiumpvp.utilities;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class Config {

    private final JavaPlugin javaPlugin;

    private File file;
    private YamlConfiguration configFile;

    public Config(final JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
        this.loadConfig();
    }

    public void loadConfig() {
        this.javaPlugin.getLogger().log(Level.INFO, "Loading configuration...");
        file = new File(Main.getInstance().getDataFolder(), "config.yml");
        if(!file.exists()) {
            try {
                Main.getInstance().saveResource("config.yml", false);
                javaPlugin.getLogger().log(Level.INFO, "Successfully generated config.yml");
            } catch (Exception exception) {
                exception.printStackTrace();
                javaPlugin.getLogger().log(Level.WARNING, "Could not generate config.yml");
            }
        }

        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration get(){
        return configFile;
    }

    public void reload(){
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}