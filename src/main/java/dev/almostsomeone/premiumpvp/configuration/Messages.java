package dev.almostsomeone.premiumpvp.configuration;

import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.plugin.Plugin;

public class Messages {

    private final InfoFile file;

    public Messages(final Plugin plugin, String language) {
        file = new InfoFile(plugin, "messages", language + ".yml");
    }

    public void load() {
        file.load();
    }

    public String getMessage(String path){
        if(file.get().isSet(path) && file.get().getString(path) != null) return file.get().getString(path);
        return "&4Message not found. Please contact a server administrator.";
    }
}