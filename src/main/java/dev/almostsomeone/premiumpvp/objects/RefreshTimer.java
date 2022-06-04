package dev.almostsomeone.premiumpvp.objects;

import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RefreshTimer extends BukkitRunnable {

    private final Game game;
    private final GamePlayerManager gamePlayerManager;

    public RefreshTimer(Game game) {
        this.game = game;
        gamePlayerManager = game.getGamePlayerManager();
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers())
            game.getBoardManager().showBoard(gamePlayerManager.getGamePlayer(player.getUniqueId()));
    }
}
