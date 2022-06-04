package dev.almostsomeone.premiumpvp.configuration;

import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {

    private static InfoFile infoFile;

    private static Messages messages;

    public static void setup(final Plugin plugin) {
        infoFile = new InfoFile(plugin, "", "settings.yml");
        messages = new Messages(plugin, getString("language", "EN"));
    }

    public static void load() {
        infoFile.load();
        messages.load();
    }

    public static String getString(String path, String fallback) {
        return infoFile.get().getString(path, fallback);
    }

    public static boolean getBoolean(String path, boolean fallback) {
        return infoFile.get().getBoolean(path, fallback);
    }

    public static int getInt(String path, int fallback) {
        return infoFile.get().getInt(path, fallback);
    }

    public static YamlConfiguration getConfig() {
        return infoFile.get();
    }

    public static String getMessage(String path) {
        return messages.getMessage(path);
    }
}
