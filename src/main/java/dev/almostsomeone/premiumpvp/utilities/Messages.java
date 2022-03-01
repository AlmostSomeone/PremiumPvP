package dev.almostsomeone.premiumpvp.utilities;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;

public class Messages {

    private final JavaPlugin javaPlugin;

    private File file;
    private YamlConfiguration messagesFile;

    public Messages(final JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
    }

    public void loadMessages() {
        File dir = new File(Main.getInstance().getDataFolder() + File.separator + "languages" + File.separator);
        if(!dir.exists()){
            try {
                if(dir.mkdir())
                    Bukkit.getLogger().info("[PremiumPvP] Languages directory created.");
                else
                    Bukkit.getLogger().warning("[PremiumPvP] Something went wrong creating a directory for the languages.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        String fileName = Main.getInstance().config.get().getString("language") + ".yml";
        file = new File(dir, fileName);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        if (!file.exists()) {
            try {
                // read this file into InputStream
                inputStream = Main.getInstance().getResource(fileName);

                // write the inputStream to a FileOutputStream
                outputStream = new FileOutputStream(file);

                int read;
                byte[] bytes = new byte[1024];

                while ((read = inputStream != null ? inputStream.read(bytes) : 0) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getServer()
                        .getLogger()
                        .warning("[PremiumPvP] Could not create " + fileName + "!");
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
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(String path){
        if(messagesFile.isSet(path)) {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesFile.getString(path)));
        }
        return "&4Message not found. Please contact a server administrator.";
    }

    public void reload(){
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }
}