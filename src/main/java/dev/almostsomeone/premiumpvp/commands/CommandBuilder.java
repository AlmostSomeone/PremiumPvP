package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.utilities.Messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CommandBuilder extends Command {

    private final YamlConfiguration config = Main.getInstance().config.get();
    private final Messages messages = Main.getInstance().messages;

    public HashMap<String, String> subCommands;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        return false;
    }

    protected CommandBuilder(String configPath) {
        super(Main.getInstance().config.get().getString(configPath + ".name"));

        this.setUsage("/" + this.getName());
        this.setPermissionMessage(this.messages.getMessage("global.no-permissions"));
        if(this.config.isSet(configPath + ".description"))
            this.setDescription(this.config.getString(configPath + ".description"));
        if(this.config.isSet(configPath + ".permission.enabled") && this.config.getBoolean(configPath + ".permission.enabled") && this.config.isSet(configPath + ".permission.name"))
            this.setPermission(this.config.getString(configPath + ".permission.name"));
        if(this.config.isSet(configPath + ".aliases"))
            this.setAliases(this.config.getStringList(configPath + ".aliases"));

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

        this.setUsage("/" + this.getName());
        this.setPermissionMessage(this.messages.getMessage("global.no-permissions"));
        if(this.config.isSet(configPath + ".description"))
            this.setDescription(this.config.getString(configPath + ".description"));
        if(this.config.isSet(configPath + ".permission.enabled") && this.config.getBoolean(configPath + ".permission.enabled") && this.config.isSet(configPath + ".permission.name"))
            this.setPermission(this.config.getString(configPath + ".permission.name"));
        if(this.config.isSet(configPath + ".aliases"))
            this.setAliases(this.config.getStringList(configPath + ".aliases"));

        if(forceEnabled || !this.config.isSet(configPath + ".enabled") || this.config.getBoolean(configPath + ".enabled")) {
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

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> autoCompletes = new ArrayList<>();
        if (args.length == 1) {
            for (String sub : this.subCommands.keySet().toArray(new String[0]))
                if (StringUtil.startsWithIgnoreCase(sub, args[0]))
                    autoCompletes.add(sub);
            return autoCompletes;
        }
        return null;
    }
}
