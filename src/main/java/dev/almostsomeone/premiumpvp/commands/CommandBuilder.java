package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.storage.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CommandBuilder extends Command {

    private final FileConfiguration config = Main.getInstance().getConfig();
    private final Messages messages = Main.getInstance().getMessages();

    public HashMap<String, String> subCommands;

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        return false;
    }

    protected CommandBuilder(String configPath) {
        super(Main.getInstance().getConfig().getString(configPath + ".name"));

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

    protected CommandBuilder(String configPath, String name, boolean forceEnabled, boolean allowPermissions) {
        super(name);

        this.setPermissionMessage(this.messages.getMessage("global.no-permissions"));
        if(this.config.isSet(configPath + ".description"))
            this.setDescription(this.config.getString(configPath + ".description"));
        if(allowPermissions && this.config.isSet(configPath + ".permission.enabled") && this.config.getBoolean(configPath + ".permission.enabled") && this.config.isSet(configPath + ".permission.name"))
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

    @Override @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        String lastWord = args[args.length - 1];

        List<String> autoCompletes = new ArrayList<>();
        if (args.length == 1 && subCommands != null) {
            for (String sub : this.subCommands.keySet().toArray(new String[0]))
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
