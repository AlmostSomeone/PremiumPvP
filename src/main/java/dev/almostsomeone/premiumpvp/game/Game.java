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
    private WorldManager worldManager;
    private GamePlayerManager gamePlayerManager;
    private BoardManager boardManager;

    // Settings
    private Location spawnLocation;

    public Game(final Plugin plugin) {
        this.worldManager = new WorldManager(Main.getInstance());
        this.gamePlayerManager = new GamePlayerManager();
        this.boardManager = new BoardManager(plugin);
    }

    public void loadGame() {
        this.worldManager.loadWorlds();
        this.gamePlayerManager.loadGamePlayers();
        this.boardManager.loadBoard();
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
