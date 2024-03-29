package dev.almostsomeone.premiumpvp.listeners;

import dev.almostsomeone.premiumpvp.listeners.entity.*;
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

                // Player
                new PlayerJoinListener(),
                new PlayerChangedWorldListener(),
                new FoodLevelChangeListener(),
                new FallDamageListener(),
                new PlayerDropItemListener(),
                new InventoryClickListener(),

                // World
                new WeatherChangeListener()
        );
        for(Listener listener : listeners)
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
