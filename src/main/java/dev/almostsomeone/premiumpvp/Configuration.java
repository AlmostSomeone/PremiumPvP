package dev.almostsomeone.premiumpvp;

import dev.almostsomeone.premiumpvp.configuration.Messages;
import dev.almostsomeone.premiumpvp.storage.InfoFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class Configuration {

    private final InfoFile settings;
    private final Messages messages;

    Configuration(@Nonnull Plugin plugin) {
        settings = new InfoFile(plugin, "", "settings.yml");
        messages = new Messages(plugin, settings.get().getString("language", "EN"));
    }

    public void load() {
        settings.load();
        messages.load();
    }

    public YamlConfiguration getSettings() {
        return settings.get();
    }

    public Messages getMessages() {
        return messages;
    }
}
