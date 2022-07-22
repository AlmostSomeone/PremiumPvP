package dev.almostsomeone.premiumpvp.listeners;

import dev.almostsomeone.premiumpvp.listeners.entity.*;
import dev.almostsomeone.premiumpvp.listeners.gameplayer.*;
import dev.almostsomeone.premiumpvp.listeners.player.*;
import dev.almostsomeone.premiumpvp.listeners.world.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ListenerHandler {

    public ListenerHandler(final Plugin plugin) {
        List<Listener> listeners = Arrays.asList(
                // Entity
                new EntityPickupItemListener(),

                // GamePlayer
                new GamePlayerLeaveListener(),

                // Player
                new PlayerJoinListener(),
                new PlayerChangedWorldListener(),
                new FoodLevelChangeListener(),
                new FallDamageListener(),
                new PlayerDropItemListener(),
                new InventoryClickListener(),

                // World
                new WeatherChangeListener(),
                new GamePlayerLeaveListener()
        );
        for(Listener listener : listeners)
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
