package dev.almostsomeone.premiumpvp.game;

import dev.almostsomeone.premiumpvp.world.WorldManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.scoreboard.BoardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class Game {

    private final WorldManager worldManager;
    private final BoardManager boardManager;
    private final GamePlayerManager gamePlayerManager;

    // Settings
    private Location spawnLocation;

    public Game(final Plugin plugin) {
        worldManager = new WorldManager(plugin);
        boardManager = new BoardManager(plugin, this);
        gamePlayerManager = new GamePlayerManager(this);
    }

    public void loadGame() {
        worldManager.loadWorlds();
        boardManager.loadBoard();
        gamePlayerManager.loadGamePlayers();
    }

    public void stop() {
        for(GamePlayer gamePlayer : gamePlayerManager.getGamePlayers())
            if(gamePlayer.getPlayer() != null) gamePlayer.getPlayer().teleport(getSpawnLocation());
        save();
    }

    public void save() {
        gamePlayerManager.saveAll();
    }

    public Location getSpawnLocation() {
        if(spawnLocation == null) spawnLocation = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
        return spawnLocation;
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
