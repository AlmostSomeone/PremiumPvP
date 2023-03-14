package dev.almostsomeone.premiumpvp.commands.executors;

import dev.almostsomeone.premiumpvp.PremiumPvP;
import dev.almostsomeone.premiumpvp.commands.CommandBuilder;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerJoinEvent;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerLeaveEvent;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;

import static dev.almostsomeone.premiumpvp.utilities.Chat.color;
import static dev.almostsomeone.premiumpvp.utilities.Chat.format;

public class KitPvPCMD extends CommandBuilder {

    private boolean joinCommand = false;
    private boolean leaveCommand = false;

    public KitPvPCMD() {
        super("commands.main", "kitpvp", true, false);
        YamlConfiguration config = Settings.getConfig();

        subCommands.put("", new HashMap<>() {{
            put("help", "Get help with this command.");
            put("info", "Get information about the plugin");
            put("reload", "Reload the configurations");
            put("save", "Force the plugin to save its data");
        }});

        if(config.getBoolean("participate.join.command", false)) {
            subCommands.get("").put("join", "Join the KitPvP");
            joinCommand = true;
        }
        if(config.getBoolean("participate.leave.command", false)) {
            subCommands.get("").put("leave", "Leave the KitPvP");
            leaveCommand = true;
        }
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(color(Settings.getMessage("global.only-players")));
            return true;
        }
        Game game = PremiumPvP.getGame();
        GamePlayer gamePlayer = game.getGamePlayerManager().getGamePlayer(player.getUniqueId());

        if(args.length == 0) {
            player.sendMessage(format(player, Settings.getMessage("commands.help.use-help").replaceAll("\\{command}", "/" + label)));
            return true;
        } else {
            switch(args[0].toLowerCase(Locale.ROOT)) {
                case "info":
                    if(args.length == 2) {
                        sendHelp(sender, label, args);
                        break;
                    }
                    player.sendMessage(" ");
                    player.sendMessage("Welcome to PremiumPvP");
                    player.sendMessage("Made by AlmostSomeone");
                    player.sendMessage("https://github.com/AlmostSomeone/PremiumPvP");
                    player.sendMessage(" ");
                    break;
                case "help":
                    sendHelp(sender, label, args);
                    break;
                case "reload":
                    try {
                        Settings.load();
                        PremiumPvP.getGame().getBoardManager().reloadBoard();
                        player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.config.reload-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.config.reload-failed")));
                    }
                    break;
                case "save":
                    try {
                        game.getGamePlayerManager().saveAll();
                        player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.data.save-success")));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.data.save-failed")));
                    }
                    break;
                case "join":
                    if(!joinCommand) break;
                    if(!gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) {
                        player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.already-joined")));
                        break;
                    }
                    Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(game, player.getUniqueId()));
                    player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.join")));
                    break;
                case "leave":
                    if(!leaveCommand) break;
                    if(gamePlayer.getGamePlayerState().equals(GamePlayerState.NONE)) {
                        player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.not-joined")));
                        break;
                    }
                    Bukkit.getPluginManager().callEvent(new GamePlayerLeaveEvent(player.getUniqueId()));
                    player.sendMessage(format(player, Settings.getMessage("commands.kitpvp.leave")));
                    break;
                default:
                    player.sendMessage(format(player, Settings.getMessage("commands.help.use-help").replaceAll("\\{command}", "/" + label)));
                    break;
            }
        }
        return false;
    }
}