package dev.almostsomeone.premiumpvp.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        /*Player player = event.getPlayer();
        Game game = PremiumPvP.getGame();
        GamePlayer gamePlayer = game.getGamePlayerManager().getGamePlayer(player.getUniqueId());

        if(gamePlayer.getGamePlayerState().equals(GamePlayerState.LOBBY))
            if(!game.getLobby().isAllowDrop()) event.setCancelled(true);*/
    }
}
