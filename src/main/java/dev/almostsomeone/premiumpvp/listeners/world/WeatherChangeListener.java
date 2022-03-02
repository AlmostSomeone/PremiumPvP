package dev.almostsomeone.premiumpvp.listeners.world;

import dev.almostsomeone.premiumpvp.Main;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldManager;
import dev.almostsomeone.premiumpvp.common.bukkit.world.WorldProfile;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void weatherChange(WeatherChangeEvent event) {
        World world = event.getWorld();

        // Get world information
        WorldManager worldManager = Main.getInstance().getGame().getWorldManager();
        WorldProfile worldProfile = worldManager.getWorldProfile(world.getName());

        // Cancel the event if the world has a profile, and the profile has its weather locked.
        if(worldProfile != null && !worldProfile.getWeatherLock().equalsIgnoreCase("none"))
            event.setCancelled(true);
    }
}
