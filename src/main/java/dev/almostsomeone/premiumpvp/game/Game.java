package dev.almostsomeone.premiumpvp.game;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.objects.BoardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class Game {

    // Managers
    private final WorldManager worldManager;
    private final BoardManager boardManager;
    private final GamePlayerManager gamePlayerManager;

    // Settings
    private Location spawnLocation;

    public Game(final Plugin plugin) {
        this.worldManager = new WorldManager(Main.getInstance());
        this.boardManager = new BoardManager(plugin, this);
        this.gamePlayerManager = new GamePlayerManager();
    }

    public void loadGame() {
        this.worldManager.loadWorlds();
        this.boardManager.loadBoard();
        this.gamePlayerManager.loadGamePlayers();
    }

    public Location getSpawnLocation() {
        if(this.spawnLocation == null)
            this.spawnLocation = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
        return this.spawnLocation;
    }

    public GamePlayerManager getGamePlayerManager() {
        return gamePlayerManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }
}
