package dev.almostsomeone.premiumpvp.game;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Game {

    // Managers
    private WorldManager worldManager;
    private GamePlayerManager gamePlayerManager;

    // Settings
    private Location spawnLocation;

    public Game() {
        this.worldManager = new WorldManager(Main.getInstance());
        this.gamePlayerManager = new GamePlayerManager();
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
}
