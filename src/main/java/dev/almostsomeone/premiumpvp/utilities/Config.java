package dev.almostsomeone.premiumpvp.utilities;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Config {

    private final JavaPlugin javaPlugin;

    private File file;
    private YamlConfiguration configFile;

    public Config(final JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
    }

    public void loadConfig() {
        file = new File(Main.getInstance().getDataFolder(), "config.yml");
        if(!file.exists()) {
            try {
                Main.getInstance().saveResource("config.yml", false);
            } catch (Exception exception) {
                exception.printStackTrace();
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