package dev.almostsomeone.premiumpvp.objects;

import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RefreshTimer extends BukkitRunnable {

    private Game game;
    private GamePlayerManager gamePlayerManager;

    public RefreshTimer(Game game) {
        this.game = game;
        this.gamePlayerManager = game.getGamePlayerManager();
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers())
            this.game.getBoardManager().showBoard(this.gamePlayerManager.getGamePlayer(player.getUniqueId()));
    }
}
