package dev.almostsomeone.premiumpvp.common.bukkit.world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public record WorldProfile(String name, WorldType type, boolean generateStructures,
                           boolean isVoid, boolean hunger, String weatherLock,
                           String gameMode) {

    public void setWeather() {
        if (this.weatherLock.equalsIgnoreCase("NONE"))
            return;

        World world = Bukkit.getWorld(this.name);
        if (world == null) return;

        if (this.weatherLock.equalsIgnoreCase("clear")) {
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    public void applyProfile(Player player) {
        if (!this.gameMode.equalsIgnoreCase("none"))
            player.setGameMode(GameMode.valueOf(this.gameMode));

        if (!this.hunger)
            player.setFoodLevel(20);
    }

    public String getName() {
        return this.name;
    }

    public WorldType getType() {
        return this.type;
    }

    public boolean getGenerateStructures() {
        return this.generateStructures;
    }

    public boolean getVoid() {
        return this.isVoid;
    }

    public boolean getHunger() {
        return hunger;
    }

    public String getWeatherLock() {
        return weatherLock;
    }

    public String getGameMode() {
        return gameMode;
    }
}
