package dev.almostsomeone.premiumpvp.world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class WorldProfile {

    private final String name;
    private final WorldType worldType;
    private final boolean generateStructures, isVoid;
    private boolean hunger, fallDamage;
    private String weatherLock, gameMode;

    /**
     * The profile of a world, configurable in PremiumPvP's settings file.
     *
     * @param name               The name of the world this profile applies to
     * @param worldType          The type of world, for example Overworld
     * @param generateStructures If the world has generated structures
     * @param isVoid             If the world should be a void world
     * @param hunger             If players their hunger drain
     * @param fallDamage         If players can get fall damage
     * @param weatherLock        To which weather the world should be locked, use "none" to disable.
     * @param gameMode           Which game mode players in this world should be set to, use "none" to disable.
     */
    public WorldProfile(@Nonnull String name, @Nonnull WorldType worldType,
                        Boolean generateStructures, Boolean isVoid,
                        Boolean hunger, Boolean fallDamage,
                        String weatherLock, String gameMode) {
        this.name = name;
        this.worldType = worldType;
        this.generateStructures = generateStructures == null || generateStructures;
        this.isVoid = isVoid != null && isVoid;
        this.hunger = hunger == null || hunger;
        this.fallDamage = fallDamage != null && fallDamage;
        this.weatherLock = weatherLock == null ? "none" : weatherLock;
        this.gameMode = gameMode == null ? "none" : gameMode;
    }

    /**
     * Set the weather of the world to the configured lock.
     */
    public void setWeather() {
        if (weatherLock.equalsIgnoreCase("none"))
            return;

        World world = Bukkit.getWorld(name);
        if (world == null) return;

        if (weatherLock.equalsIgnoreCase("clear")) {
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    /**
     * Apply the world's game mode and hunger to the player. This will feed the player.
     *
     * @param player The player to apply these settings to.
     */
    public void applyProfile(Player player) {
        if (!gameMode.equalsIgnoreCase("none"))
            player.setGameMode(GameMode.valueOf(gameMode));

        if (!hunger)
            player.setFoodLevel(20);
    }

    /**
     * Get the name of the profile / world.
     *
     * @return The name in the original capitalisation and format.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the world type of the world.
     *
     * @return The world type of the world.
     */
    public WorldType getWorldType() {
        return worldType;
    }

    /**
     * Check if the world has structure generation enabled or disabled.
     *
     * @return The result as a boolean.
     */
    public boolean getGenerateStructures() {
        return generateStructures;
    }

    /**
     * Check if the world is a void world.
     *
     * @return The result as a boolean.
     */
    public boolean getVoid() {
        return isVoid;
    }

    /**
     * Check if the world has hunger enabled.
     *
     * @return The result as a boolean.
     */
    public boolean getHunger() {
        return hunger;
    }

    /**
     * Configure if the hunger system should be enabled.
     *
     * @param hunger The boolean state.
     */
    public void setHunger(boolean hunger) {
        this.hunger = hunger;
    }

    /**
     * Check if the world has fall damage enabled.
     *
     * @return The result as a boolean.
     */
    public boolean getFallDamage() {
        return fallDamage;
    }

    /**
     * Configure if fall damage should be enabled.
     *
     * @param fallDamage The boolean state.
     */
    public void setFallDamage(boolean fallDamage) {
        this.fallDamage = fallDamage;
    }

    /**
     * Check if the world has a set weather lock.
     *
     * @return The current weather lock configuration, "none" if not active.
     */
    public String getWeatherLock() {
        return weatherLock;
    }

    /**
     * Configure the weather lock.
     *
     * @param weatherLock The weather state or "none".
     */
    public void setWeatherLock(String weatherLock) {
        this.weatherLock = weatherLock;
    }

    /**
     * Check if the world has a set game mode.
     *
     * @return The current game mode configuration, "none" if not active.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Configure the game mode.
     *
     * @param gameMode The game mode or "none".
     */
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}