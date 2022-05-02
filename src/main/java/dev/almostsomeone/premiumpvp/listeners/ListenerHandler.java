package dev.almostsomeone.premiumpvp.listeners;

import dev.almostsomeone.premiumpvp.listeners.gameplayer.GamePlayerJoinListener;
import dev.almostsomeone.premiumpvp.listeners.gameplayer.GamePlayerLeaveListener;
import dev.almostsomeone.premiumpvp.listeners.player.FoodLevelChangeListener;
import dev.almostsomeone.premiumpvp.listeners.player.PlayerChangedWorldListener;
import dev.almostsomeone.premiumpvp.listeners.player.PlayerJoinListener;
import dev.almostsomeone.premiumpvp.listeners.world.WeatherChangeListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ListenerHandler {

    public ListenerHandler(final Plugin plugin) {
        List<Listener> listeners = Arrays.asList(
                new PlayerJoinListener(),
                new PlayerChangedWorldListener(),
                new FoodLevelChangeListener(),
                new WeatherChangeListener(),
                new GamePlayerJoinListener(),
                new GamePlayerLeaveListener()
        );
        for(Listener listener : listeners)
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
