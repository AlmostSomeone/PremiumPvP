package dev.almostsomeone.premiumpvp.world;

import dev.almostsomeone.premiumpvp.configuration.Settings;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class WorldManager {

    private final Plugin plugin;
    private WorldProfile globalProfile;
    private WorldProfile duelsProfile;

    private final List<WorldProfile> worldProfiles;

    public WorldManager(final Plugin plugin) {
        this.plugin = plugin;
        worldProfiles = new ArrayList<>();
    }

    public void loadWorlds() {
        plugin.getLogger().log(Level.INFO, () -> "Loading worlds...");

        globalProfile = generateProfile("world-settings.global", false);
        duelsProfile = generateProfile("world-settings.duels", true);

        YamlConfiguration config = Settings.getConfig();
        
        if(config.isSet("world-settings.specifics")) {
            for(String profileName : Objects.requireNonNull(config.getConfigurationSection("world-settings.specifics")).getKeys(false)) {
                WorldProfile worldProfile = generateProfile("world-settings.specifics." + profileName, false);
                loadWorld(worldProfile);
                worldProfiles.add(worldProfile);
            }
        }

        plugin.getLogger().log(Level.INFO, () -> "Loaded all worlds");
    }

    public void loadWorld(WorldProfile worldProfile) {
        WorldCreator worldCreator = new WorldCreator(worldProfile.getName());
        worldCreator.type(worldProfile.getType());
        worldCreator.generateStructures(worldProfile.getGenerateStructures());
        if(worldProfile.getVoid())
            worldCreator.generator(new VoidGenerator());
        worldCreator.createWorld();
        worldProfile.setWeather();
    }

    private WorldProfile generateProfile(String configPath, boolean isDuels) {
        YamlConfiguration config = Settings.getConfig();
        
        String[] pathParts = configPath.split("\\.");
        String profileName = pathParts[pathParts.length-1];

        WorldProfile fallbackProfile = (isDuels ? duelsProfile : globalProfile);

        WorldType type = (fallbackProfile == null ? WorldType.NORMAL : fallbackProfile.getType());
        if(config.isSet(configPath + ".type")) {
            try {
                type = WorldType.valueOf(config.getString(configPath + ".type"));
            } catch (IllegalArgumentException exception) {
                plugin.getLogger().log(Level.WARNING, "The world type for " + profileName + " is not valid. Defaulting to " + type.toString() + ".");
            }
        }
        boolean generateStructures = (fallbackProfile == null || fallbackProfile.getGenerateStructures());
        if(config.isSet(configPath + ".generate-structures"))
            generateStructures = config.getBoolean(configPath + ".biome");
        boolean isVoid = (fallbackProfile == null || fallbackProfile.getVoid());
        if(config.isSet(configPath + ".void"))
            isVoid = config.getBoolean(configPath + ".void");
        boolean hunger = (fallbackProfile == null || fallbackProfile.getHunger());
        if(config.isSet(configPath + ".hunger"))
            hunger = config.getBoolean(configPath + ".hunger");
        boolean fallDamae = (fallbackProfile == null || fallbackProfile.getFallDamage());
        if(config.isSet(configPath + ".fall-damage"))
            fallDamae = config.getBoolean(configPath + ".fall-damage");
        String weatherLock = (fallbackProfile == null ? "NONE" : fallbackProfile.getWeatherLock());
        if(config.isSet(configPath + ".weather-lock")) {
            String temp = config.getString(configPath + ".weather-lock");
            if(temp == null) temp = "NONE";
            if(temp.equalsIgnoreCase("NONE")) {
                weatherLock = "NONE";
            } else {
                try {
                    weatherLock = WeatherType.valueOf(temp).toString();
                } catch (IllegalArgumentException exception) {
                    plugin.getLogger().log(Level.WARNING, "The weather lock for " + profileName + " is not valid. Defaulting to " + weatherLock + ".");
                }
            }
        }
        String gameMode = (fallbackProfile == null ? "NONE" : fallbackProfile.getGameMode());
        if(config.isSet(configPath + ".gamemode")) {
            String temp = config.getString(configPath + ".gamemode");
            if(temp == null) temp = "NONE";
            if(temp.equalsIgnoreCase("NONE")) {
                gameMode = "NONE";
            } else {
                try {
                    gameMode = GameMode.valueOf(temp).toString();
                } catch (IllegalArgumentException exception) {
                    plugin.getLogger().log(Level.WARNING, "The gamemode for " + profileName + " is not valid. Defaulting to " + gameMode + ".");
                }
            }
        }

        return new WorldProfile(
                profileName,
                type,
                generateStructures,
                isVoid,
                hunger,
                fallDamae,
                weatherLock,
                gameMode
        );
    }

    public WorldProfile getWorldProfile(String name) {
        return this.worldProfiles.stream().filter(profile -> profile.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

