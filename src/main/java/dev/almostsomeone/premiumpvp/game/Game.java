package dev.almostsomeone.premiumpvp.game;

import dev.almostsomeone.premiumpvp.world.WorldManager;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerManager;
import dev.almostsomeone.premiumpvp.scoreboard.BoardManager;
import org.bukkit.plugin.Plugin;

public class Game {

    private final WorldManager worldManager;
    private final BoardManager boardManager;
    private final GamePlayerManager gamePlayerManager;

    private final Lobby lobby;

    public Game(final Plugin plugin) {
        worldManager = new WorldManager(plugin);
        boardManager = new BoardManager(plugin, this);
        gamePlayerManager = new GamePlayerManager(this);

        lobby = new Lobby(this);
    }

    public void loadGame() {
        worldManager.loadWorlds();
        boardManager.loadBoard();
    }

    public void loadPlayers() {
        gamePlayerManager.loadGamePlayers();
    }

    public void save() {
        gamePlayerManager.saveAll();
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

    public Lobby getLobby() {
        return lobby;
    }
}
