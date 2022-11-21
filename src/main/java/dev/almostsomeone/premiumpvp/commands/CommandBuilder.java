package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Main;
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
import java.util.*;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;

public abstract class CommandBuilder extends Command {

    public HashMap<String, HashMap<String, String>> subCommands = new HashMap<>();

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

        subCommands.put("", new HashMap<>());

        if(!config.isSet(configPath + ".enabled") || config.getBoolean(configPath + ".enabled")) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

                Command command = commandMap.getCommand(getName());
                if(command != null && command.isRegistered())
                    return;

                commandMap.register(Main.getPlugin(Main.class).getDescription().getName(), this);
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

        subCommands.put("", new HashMap<>());

        if(forceEnabled || !config.isSet(configPath + ".enabled") || config.getBoolean(configPath + ".enabled")) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

                Command command = commandMap.getCommand(name);
                if(command != null && command.isRegistered())
                    return;

                commandMap.register(Main.getPlugin(Main.class).getDescription().getName(), this);
                PluginTopic.registerCommand(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args, Location location) throws IllegalArgumentException {
        final List<String> autoCompletes = new ArrayList<>();
        String lastWord = args[args.length-1];

        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(args).forEach(string -> stringBuilder.append(string).append(" "));
        String arguments = stringBuilder.toString().trim();

        if(subCommands.containsKey(arguments) && subCommands.get(arguments).size() > 0)
            subCommands.get(arguments).keySet().stream().filter(argument -> StringUtil.startsWithIgnoreCase(argument, lastWord)).forEach(autoCompletes::add);
        else {
            Player senderPlayer = sender instanceof Player ? (Player) sender : null;
            if(senderPlayer == null) return new ArrayList<>();
            for(Player player : sender.getServer().getOnlinePlayers().stream().filter(senderPlayer::canSee).filter(player -> StringUtil.startsWithIgnoreCase(player.getName(), lastWord)).toList())
                autoCompletes.add(player.getName());
        }

        autoCompletes.sort(String.CASE_INSENSITIVE_ORDER);
        return autoCompletes;
    }

    public void sendHelp(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args) {
        StringBuilder gArg = new StringBuilder();
        for(int i = 0; i < args.length-1; i++) gArg.append(args[i]).append(" ");
        String arg = gArg.toString().trim();
        String command = "/" + alias + (arg.equals("") ? "" : " " + arg);

        // Send header
        sender.sendMessage(color(Settings.getMessage("commands.help.header").replaceAll("\\{command}", command)));

        // Send error message if there are no subcommands
        if(!this.subCommands.containsKey(arg) || this.subCommands.get(arg).size() < 2) {
            sender.sendMessage(color(Settings.getMessage("commands.help.no-help")));
            return;
        }

        // Send sub commands
        this.subCommands.get(arg).forEach((subcommand, description) ->
                sender.sendMessage(color(Settings.getMessage("commands.help.item")
                        .replaceAll("\\{command}", command)
                        .replaceAll("\\{subcommand}", subcommand)
                        .replaceAll("\\{description}", description))));

        // Send footer
        sender.sendMessage(color(Settings.getMessage("commands.help.footer").replaceAll("\\{command}", command)));
    }
}