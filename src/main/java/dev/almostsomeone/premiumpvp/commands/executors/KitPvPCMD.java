package dev.almostsomeone.premiumpvp.commands.executors;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.commands.CommandBuilder;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerJoinEvent;
import dev.almostsomeone.premiumpvp.events.gameplayer.GamePlayerLeaveEvent;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Locale;

import static dev.almostsomeone.premiumpvp.chat.Chat.color;
import static dev.almostsomeone.premiumpvp.chat.Chat.format;

public class KitPvPCMD extends CommandBuilder {

    private boolean joinCommand = false;
    private boolean leaveCommand = false;

    public KitPvPCMD() {
        super("commands.main", "kitpvp", true, false);

        subCommands = new HashMap<>() {{
            put("info", "Get information about the plugin");
            put("help", "Get a list of sub-commands");
            put("reload", "Reload the configurations");
            put("save", "Force the plugin to save its data");
        }};

        if(Settings.getBoolean("participate.join.command", false)) {
            subCommands.put("join", "Join the KitPvP");
            joinCommand = true;
        }
        if(Settings.getBoolean("participate.leave.command", false)) {
            subCommands.put("leave", "Leave the KitPvP");
            leaveCommand = true;
        }
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(color(Settings.getMessage("global.only-players")));
            return true;
        }
        Game game = Main.getGame();
        GamePlayer gamePlayer = game.getGamePlayerManager().getGamePlayer(player.getUniqueId());

        if(args.length == 0) {
            player.sendMessage(format(player, Settings.getMessage("commands.help.use-help").replaceAll("\\{command}", "/" + label)));
            return true;
        } else {
            switch(args[0].toLowerCase(Locale.ROOT)) {
                case "info":
                    player.sendMessage(" ");
                    player.sendMessage("Welcome to PremiumPvP");
                    player.sendMessage("Made by AlmostSomeone");
                    player.sendMessage("https://github.com/AlmostSomeone/PremiumPvP");
                    player.sendMessage(" ");
                    break;
                case "help":
                    sender.sendMessage(format(player, Settings.getMessage("commands.help.header")
                            .replaceAll("\\{command}", "/" + label)));
                    this.subCommands.forEach((subcommand, description) ->
                            player.sendMessage(format(player, Settings.getMessage("commands.help.item")
                                    .replaceAll("\\{command}", "/" + label)
                                    .replaceAll("\\{subcommand}", subcommand.substring(0,1).toUpperCase(Locale.ROOT) + subcommand.substring(1).toLowerCase(Locale.ROOT))
                                    .replaceAll("\\{description}", description))));
                    player.sendMessage(format(player, Settings.getMessage("commands.help.footer")
                            .replaceAll("\\{command}", "/" + label)));
                    break;
                case "reload":
                    try {
                        Settings.load();
                        Main.getGame().getBoardManager().reloadBoard();
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
