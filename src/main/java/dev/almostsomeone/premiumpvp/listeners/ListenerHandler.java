package dev.almostsomeone.premiumpvp.listeners;

import dev.almostsomeone.premiumpvp.PremiumPvP;
import dev.almostsomeone.premiumpvp.listeners.entity.EntityPickupItemListener;
import dev.almostsomeone.premiumpvp.listeners.player.*;
import dev.almostsomeone.premiumpvp.listeners.world.WeatherChangeListener;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ListenerHandler {

    public ListenerHandler(@Nonnull PremiumPvP instance) {
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
                new WeatherChangeListener(instance)
        );
        for (Listener listener : listeners)
            instance.getServer().getPluginManager().registerEvents(listener, instance);
    }
}
