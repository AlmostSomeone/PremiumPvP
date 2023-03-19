package dev.almostsomeone.premiumpvp.listeners.world;

import dev.almostsomeone.premiumpvp.PremiumPvP;
import dev.almostsomeone.premiumpvp.world.WorldProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import javax.annotation.Nonnull;

public class WeatherChangeListener implements Listener {

    private final PremiumPvP instance;

    public WeatherChangeListener(@Nonnull PremiumPvP instance) {
        this.instance = instance;
    }

    @EventHandler
    private void weatherChange(WeatherChangeEvent event) {
        WorldProfile profile = instance.getWorldProfile(event.getWorld().getName());
        if(profile != null && !profile.getWeatherLock().equals("none")) {
            event.setCancelled(true);
        }
    }
}
