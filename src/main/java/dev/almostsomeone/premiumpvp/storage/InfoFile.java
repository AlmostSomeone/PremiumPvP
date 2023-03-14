package dev.almostsomeone.premiumpvp.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class InfoFile {

    private final Plugin plugin;
    private final String path;
    private final String name;

    private YamlConfiguration yamlConfiguration;

    public InfoFile(final Plugin plugin, final String path, final String name) {
        this.plugin = plugin;
        this.path = path;
        this.name = name;

        load();
    }

    public String getName() {
        return name;
    }

    public void load() {
        plugin.getLogger().log(Level.INFO, () -> "Loading file " + name + "...");
        File dir = new File(plugin.getDataFolder() + File.separator);
        if(path != null) {
            dir = new File(plugin.getDataFolder() + File.separator + path + File.separator);
            if(!dir.exists()){
                try {
                    if(dir.mkdir()) plugin.getLogger().log(Level.INFO, () -> path + " directory created.");
                    else plugin.getLogger().log(Level.WARNING, () -> "Something went wrong creating directory " + path);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        File file = new File(dir, name);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        if (!file.exists()) {
            try {
                inputStream = plugin.getResource(name);
                outputStream = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];
                while ((read = inputStream != null ? inputStream.read(bytes) : 0) != -1)
                    outputStream.write(bytes, 0, read);
                plugin.getLogger().log(Level.INFO, () -> "Successfully generated " + file);
            } catch (IOException e) {
                e.printStackTrace();
                plugin.getLogger().log(Level.WARNING, () -> "Could not generate " + file);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration get() {
        return yamlConfiguration;
    }
}
