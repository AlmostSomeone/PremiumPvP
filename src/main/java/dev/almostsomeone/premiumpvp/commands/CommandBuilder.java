package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.*;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;

abstract class CommandBuilder extends Command {

    private final Plugin plugin;
    protected final Configuration configuration;

    private final String configPath;
    private final boolean forceEnabled, allowPermissions;
    protected HashMap<String, HashMap<String, String>> subCommands = new HashMap<>();

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, String[] args) {
        return false;
    }

    protected CommandBuilder(@Nonnull Plugin plugin, @Nonnull Configuration configuration, String configPath) {
        super(Objects.requireNonNull(configuration.getSettings().getString(configPath + ".name")));
        this.plugin = plugin;
        this.configuration = configuration;
        this.configPath = configPath;
        this.forceEnabled = false;
        this.allowPermissions = true;

        setProperties();
        register();
    }

    protected CommandBuilder(@Nonnull Plugin plugin, @Nonnull Configuration configuration, String configPath, String name, boolean forceEnabled, boolean allowPermissions) {
        super(name);
        this.plugin = plugin;
        this.configuration = configuration;
        this.configPath = configPath;
        this.forceEnabled = forceEnabled;
        this.allowPermissions = allowPermissions;

        setProperties();
        register();
    }

    private void setProperties() {
        YamlConfiguration config = configuration.getSettings();
        if(!forceEnabled && !config.getBoolean(configPath + ".enabled", true)) return;
        setPermissionMessage(configuration.getMessages().get("global.no-permissions"));
        if(config.isSet(configPath + ".description"))
            setDescription(Objects.requireNonNull(config.getString(configPath + ".description")));
        if(allowPermissions && config.isSet(configPath + ".permission.enabled") && config.getBoolean(configPath + ".permission.enabled") && config.isSet(configPath + ".permission.name"))
            setPermission(config.getString(configPath + ".permission.name"));
        if(config.isSet(configPath + ".aliases"))
            setAliases(config.getStringList(configPath + ".aliases"));
    }

    protected void reload() {
    }

    private void register() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            Command command = commandMap.getCommand(getName());
            if(command != null && command.isRegistered())
                return;

            commandMap.register(plugin.getDescription().getName(), this);
            CommandManager.addCommand(getName(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args, Location location) throws IllegalArgumentException {
        // We will be saving all possibilities here
        final List<String> autoCompletes = new ArrayList<>();

        // The last word typed
        String lastWord = args[args.length-1];

        // Get the arguments (without the last word)
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < args.length-1; i++) builder.append(args[i]).append(" ");

        // Make variables for easy access
        String arguments = builder.toString().trim();
        String totalArguments = arguments + " " + lastWord;

        // If the argument has subcommands
        if(subCommands.containsKey(totalArguments))
            subCommands.get(totalArguments).keySet().stream()
                    .filter(a -> StringUtil.startsWithIgnoreCase(a, lastWord))
                    .forEach(autoCompletes::add);

        // If the second to last argument has subcommands but the last doesn't
        else if(subCommands.containsKey(arguments) && !subCommands.containsKey(totalArguments))
                subCommands.get(arguments).keySet().stream()
                        .filter(a -> StringUtil.startsWithIgnoreCase(a, lastWord))
                        .forEach(autoCompletes::add);

        // Else, show the player list like default
        else {
            Player senderPlayer = sender instanceof Player ? (Player) sender : null;
            if(senderPlayer == null) return new ArrayList<>();
            sender.getServer().getOnlinePlayers().stream()
                    .filter(senderPlayer::canSee)
                    .filter(player -> StringUtil.startsWithIgnoreCase(player.getName(), lastWord))
                    .forEach(player -> autoCompletes.add(player.getName()));
        }

        // Sort the list and return it
        autoCompletes.sort(String.CASE_INSENSITIVE_ORDER);
        return autoCompletes;
    }

    protected void sendHelp(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args) {
        StringBuilder gArg = new StringBuilder();
        for(int i = 0; i < args.length-1; i++) gArg.append(args[i]).append(" ");
        String arg = gArg.toString().trim();
        String command = "/" + alias + (arg.equals("") ? "" : " " + arg);

        // Send header
        sender.sendMessage(color(configuration.getMessages().get("commands.help.header").replaceAll("\\{command}", command)));

        // Send error message if there are no subcommands
        if(!this.subCommands.containsKey(arg) || this.subCommands.get(arg).size() < 2) {
            sender.sendMessage(color(configuration.getMessages().get("commands.help.no-help")));
            return;
        }

        // Send sub commands
        this.subCommands.get(arg).forEach((subcommand, description) ->
                sender.sendMessage(color(configuration.getMessages().get("commands.help.item")
                        .replaceAll("\\{command}", command)
                        .replaceAll("\\{subcommand}", subcommand)
                        .replaceAll("\\{description}", description))));

        // Send footer
        sender.sendMessage(color(configuration.getMessages().get("commands.help.footer").replaceAll("\\{command}", command)));
    }
}