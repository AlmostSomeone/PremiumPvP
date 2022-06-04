package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class CommandBuilder extends Command {

    public HashMap<String, String> subCommands;

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, String[] args) {
        return false;
    }

    protected CommandBuilder(String configPath) {
        super(Objects.requireNonNull(Settings.getConfig().getString(configPath + ".name")));

        YamlConfiguration config = Settings.getConfig();
        setPermissionMessage(Settings.getMessage("global.no-permissions"));
        if(config.isSet(configPath + ".description"))
            setDescription(Objects.requireNonNull(config.getString(configPath + ".description")));
        if(config.isSet(configPath + ".permission.enabled") && config.getBoolean(configPath + ".permission.enabled") && config.isSet(configPath + ".permission.name"))
            setPermission(config.getString(configPath + ".permission.name"));
        if(config.isSet(configPath + ".aliases"))
            setAliases(config.getStringList(configPath + ".aliases"));

        if(!config.isSet(configPath + ".enabled") || config.getBoolean(configPath + ".enabled")) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

                Command command = commandMap.getCommand(getName());
                if(command != null && command.isRegistered())
                    return;

                commandMap.register(getName(), this);
                PluginTopic.registerCommand(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected CommandBuilder(String configPath, String name, boolean forceEnabled, boolean allowPermissions) {
        super(name);

        YamlConfiguration config = Settings.getConfig();
        this.setPermissionMessage(Settings.getMessage("global.no-permissions"));
        if(config.isSet(configPath + ".description"))
            setDescription(Objects.requireNonNull(config.getString(configPath + ".description")));
        if(allowPermissions && config.isSet(configPath + ".permission.enabled") && config.getBoolean(configPath + ".permission.enabled") && config.isSet(configPath + ".permission.name"))
            setPermission(config.getString(configPath + ".permission.name"));
        if(config.isSet(configPath + ".aliases"))
            setAliases(config.getStringList(configPath + ".aliases"));

        if(forceEnabled || !config.isSet(configPath + ".enabled") || config.getBoolean(configPath + ".enabled")) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

                Command command = commandMap.getCommand(name);
                if(command != null && command.isRegistered())
                    return;

                commandMap.register(name, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args, Location location) throws IllegalArgumentException {
        String lastWord = args[args.length - 1];

        List<String> autoCompletes = new ArrayList<>();
        if (args.length == 1 && subCommands != null) {
            for (String sub : subCommands.keySet().toArray(new String[0]))
                if (StringUtil.startsWithIgnoreCase(sub, args[0]))
                    autoCompletes.add(sub);
        } else {
            Player senderPlayer = sender instanceof Player ? (Player) sender : null;
            for(Player player : sender.getServer().getOnlinePlayers()) {
                String name = player.getName();
                if((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord))
                    autoCompletes.add(name);
            }
        }
        autoCompletes.sort(String.CASE_INSENSITIVE_ORDER);
        return autoCompletes;
    }


}
