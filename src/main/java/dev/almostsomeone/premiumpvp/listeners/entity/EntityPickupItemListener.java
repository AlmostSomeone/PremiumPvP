package dev.almostsomeone.premiumpvp.listeners.entity;

import dev.almostsomeone.premiumpvp.PremiumPvP;
import dev.almostsomeone.premiumpvp.game.Game;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayer;
import dev.almostsomeone.premiumpvp.game.gameplayer.GamePlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;

        Game game = PremiumPvP.getGame();
        GamePlayer gamePlayer = game.getGamePlayerManager().getGamePlayer(player.getUniqueId());

        if(gamePlayer.getGamePlayerState() == GamePlayerState.LOBBY)
            if(!game.getLobby().isAllowPickup()) event.setCancelled(true);
    }
}
