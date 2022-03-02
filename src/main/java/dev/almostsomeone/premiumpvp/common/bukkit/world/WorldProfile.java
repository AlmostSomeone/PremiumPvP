package dev.almostsomeone.premiumpvp.common.bukkit.world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public class WorldProfile {

    private String name;

    private WorldType type;
    private Boolean generateStructures;

    private Boolean isVoid;
    private Boolean hunger;

    private String weatherLock;
    private String gameMode;

    public WorldProfile(String name, WorldType type, Boolean generateStructures, Boolean isVoid, Boolean hunger, String weatherLock, String gameMode) {
        this.name = name;
        this.type = type;
        this.generateStructures = generateStructures;
        this.isVoid = isVoid;
        this.hunger = hunger;
        this.weatherLock = weatherLock;
        this.gameMode = gameMode;
    }

    public void setWeather() {
        if(this.weatherLock.equalsIgnoreCase("NONE"))
            return;

        World world = Bukkit.getWorld(this.name);
        if (this.weatherLock.equalsIgnoreCase("clear")) {
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    public void applyProfile(Player player) {
        if(!this.gameMode.equalsIgnoreCase("none"))
            player.setGameMode(GameMode.valueOf(this.gameMode));

        if(!this.hunger)
            player.setFoodLevel(20);
    }

    public String getName() {
        return this.name;
    }

    public WorldType getType() {
        return this.type;
    }

    public Boolean getGenerateStructures() {
        return this.generateStructures;
    }

    public Boolean getVoid() {
        return this.isVoid;
    }

    public Boolean getHunger() {
        return hunger;
    }

    public String getWeatherLock() {
        return weatherLock;
    }

    public String getGameMode() {
        return gameMode;
    }
}
