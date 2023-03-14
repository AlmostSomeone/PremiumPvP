package dev.almostsomeone.premiumpvp.configuration;

import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.plugin.Plugin;

public class Messages extends InfoFile {

    public Messages(final Plugin plugin, String language) {
        super(plugin, "messages",language + ".yml" );
    }

    public String get(String path){
        if(get().isSet(path) && get().getString(path) != null) return get().getString(path);
        return "&4Message not found. Please contact a server administrator.";
    }
}