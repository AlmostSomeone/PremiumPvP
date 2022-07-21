package dev.almostsomeone.premiumpvp.listeners.player;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Game game = Main.getGame();
        GamePlayer gamePlayer = game.getGamePlayerManager().getGamePlayer(player.getUniqueId());
        if(gamePlayer.getGamePlayerState().equals(GamePlayerState.LOBBY))
            if(!game.getLobby().isAllowMove()) event.setCancelled(true);
    }
}
