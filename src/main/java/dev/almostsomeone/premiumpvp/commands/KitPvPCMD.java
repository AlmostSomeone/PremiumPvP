package dev.almostsomeone.premiumpvp.commands;

import dev.almostsomeone.premiumpvp.Configuration;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import dev.almostsomeone.premiumpvp.events.configuration.ConfigurationReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;
import static dev.almostsomeone.premiumpvp.utilities.Chat.format;

class KitPvPCMD extends CommandBuilder {

    private boolean joinCommand = false;
    private boolean leaveCommand = false;

    KitPvPCMD(@Nonnull Plugin plugin, @Nonnull Configuration configuration) {
        super(plugin, configuration, "commands.main", "kitpvp", true, false);
    }

    @Override
    protected void reload() {
        super.reload();

        subCommands.put("", new HashMap<>() {{
            put("help", "Get help with this command.");
            put("info", "Get information about the plugin");
            put("reload", "Reload the configurations");
            put("save", "Force the plugin to save its data");
        }});

        if (configuration.getSettings().getBoolean("participate.join.command", false)) {
            subCommands.get("").put("join", "Join the KitPvP");
            joinCommand = true;
        } else {
            joinCommand = false;
        }
        if (configuration.getSettings().getBoolean("participate.leave.command", false)) {
            subCommands.get("").put("leave", "Leave the KitPvP");
            leaveCommand = true;
        } else {
            leaveCommand = false;
        }
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(color(configuration.getMessages().get("global.only-players")));
            return true;
        }
        /*Game game = PremiumPvP.getGame();
        GamePlayer gamePlayer = game.getGamePlayerManager().getGamePlayer(player.getUniqueId());
*/
        if (args.length == 0) {
            player.sendMessage(format(player, configuration.getMessages().get("commands.help.use-help").replaceAll("\\{command}", "/" + label)));
            return true;
        } else {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "info" -> {
                    if (args.length == 2) {
                        sendHelp(sender, label, args);
                        break;
                    }
                    player.sendMessage(" ");
                    player.sendMessage("Welcome to PremiumPvP");
                    player.sendMessage("Made by AlmostSomeone");
                    player.sendMessage("https://github.com/AlmostSomeone/PremiumPvP");
                    player.sendMessage(" ");
                }
                case "help" -> sendHelp(sender, label, args);
                case "reload" -> {
                    try {
                        Settings.load();
                        configuration.load();
                        Bukkit.getPluginManager().callEvent(new ConfigurationReloadEvent());
                        //PremiumPvP.getGame().getBoardManager().reloadBoard();
                        player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.config.reload-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.config.reload-failed")));
                    }
                }
                case "save" -> {
                    try {
                        //game.getGamePlayerManager().saveAll();
                        player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.data.save-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.data.save-failed")));
                    }
                }
                case "join" -> {
                    if (!joinCommand) break;
                    /*if (!gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) {
                        player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.already-joined")));
                        break;
                    }
                    Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(game, player.getUniqueId()));*/
                    player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.join")));
                }
                case "leave" -> {
                    if (!leaveCommand) break;
                    /*if (gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) {
                        player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.not-joined")));
                        break;
                    }
                    Bukkit.getPluginManager().callEvent(new GamePlayerLeaveEvent(player.getUniqueId()));*/
                    player.sendMessage(format(player, configuration.getMessages().get("commands.kitpvp.leave")));
                }
                default ->
                        player.sendMessage(format(player, configuration.getMessages().get("commands.help.use-help").replaceAll("\\{command}", "/" + label)));
            }
        }
        return false;
    }
}