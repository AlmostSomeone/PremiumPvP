package dev.almostsomeone.premiumpvp.world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public record WorldProfile(String name, WorldType type, boolean generateStructures,
                           boolean isVoid, boolean hunger, boolean fallDamage, String weatherLock,
                           String gameMode) {

    public void setWeather() {
        if (weatherLock.equalsIgnoreCase("NONE"))
            return;

        World world = Bukkit.getWorld(name);
        if (world == null) return;

        if (weatherLock.equalsIgnoreCase("clear")) {
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    public void applyProfile(Player player) {
        if (!gameMode.equalsIgnoreCase("none"))
            player.setGameMode(GameMode.valueOf(gameMode));

        if (!hunger)
            player.setFoodLevel(20);
    }

    public String getName() {
        return name;
    }

    public WorldType getType() {
        return type;
    }

    public boolean getGenerateStructures() {
        return generateStructures;
    }

    public boolean getVoid() {
        return isVoid;
    }

    public boolean getHunger() {
        return hunger;
    }

    public boolean getFallDamage() {
        return fallDamage;
    }

    public String getWeatherLock() {
        return weatherLock;
    }

    public String getGameMode() {
        return gameMode;
    }
}
