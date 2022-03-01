package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.utilities.Config;
import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.Field;

public abstract class CommandBuilder extends Command {

    private Main main = Main.getInstance();

    public Config config = main.config;
    public Messages messages = main.messages;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return false;
    }

    protected CommandBuilder(String configPath) {
        super(Main.getInstance().config.get().getString(configPath + ".name"));

        YamlConfiguration config = this.config.get();

        this.setUsage("/" + this.getName());
        this.setPermissionMessage(main.messages.getMessage("global.no-permissions"));
        if(config.isSet(configPath + ".description"))
            this.setDescription(config.getString(configPath + ".description"));
        if(config.isSet(configPath + ".permission.enabled") && config.getBoolean(configPath + ".permission.enabled") && config.isSet(configPath + ".permission.name"))
            this.setPermission(config.getString(configPath + ".permission.name"));
        if(config.isSet(configPath + ".aliases"))
            this.setAliases(config.getStringList(configPath + ".aliases"));

        if(!config.isSet(configPath + ".enabled") || config.getBoolean(configPath + ".enabled")) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

                commandMap.register(this.getName(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected CommandBuilder(String configPath, String name, Boolean forceEnabled) {
        super(name);

        YamlConfiguration config = main.config.get();

        this.setUsage("/" + this.getName());
        this.setPermissionMessage(main.messages.getMessage("global.no-permissions"));
        if(config.isSet(configPath + ".description"))
            this.setDescription(config.getString(configPath + ".description"));
        if(config.isSet(configPath + ".permission.enabled") && config.getBoolean(configPath + ".permission.enabled") && config.isSet(configPath + ".permission.name"))
            this.setPermission(config.getString(configPath + ".permission.name"));
        if(config.isSet(configPath + ".aliases"))
            this.setAliases(config.getStringList(configPath + ".aliases"));

        if(forceEnabled || !config.isSet(configPath + ".enabled") || config.getBoolean(configPath + ".enabled")) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

                commandMap.register(this.getName(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
