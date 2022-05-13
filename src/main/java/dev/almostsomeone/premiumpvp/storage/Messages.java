package dev.almostsomeone.premiumpvp.storage;

import dev.almostsomeone.premiumpvp.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Messages {

    private final Plugin plugin;

    private InfoFile file;
    private YamlConfiguration messagesFile;

    public Messages(final Plugin plugin){
        this.plugin = plugin;
        this.loadMessages();
    }

    public void loadMessages() {
        String fileName = Main.getInstance().getConfig().getString("language") + ".yml";
        this.file = new InfoFile(plugin, "messages", fileName);
        this.file.load();
        this.messagesFile = this.file.get();
    }

    public String getMessage(String path){
        if(this.messagesFile.isSet(path))
            return ChatColor.translateAlternateColorCodes('&', this.messagesFile.getString(path));
        return "&4Message not found. Please contact a server administrator.";
    }

    public void reload(){
        this.file.load();
    }
}