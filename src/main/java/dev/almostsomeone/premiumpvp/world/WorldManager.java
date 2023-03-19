package dev.almostsomeone.premiumpvp.world;

import dev.almostsomeone.premiumpvp.Configuration;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class WorldManager {

    private final Plugin plugin;
    private final Configuration configuration;
    private final List<WorldProfile> worldProfiles = new ArrayList<>();
    private WorldProfile globalProfile;
    private WorldProfile duelsProfile;

    public WorldManager(@Nonnull Plugin plugin, @Nonnull Configuration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    public void loadWorlds() {
        plugin.getLogger().log(Level.INFO, () -> "Loading worlds...");

        globalProfile = generateProfile("world-settings.global", false);
        duelsProfile = generateProfile("world-settings.duels", true);

        YamlConfiguration config = configuration.getSettings();

        if (config.isSet("world-settings.specifics")) {
            for (String profileName : Objects.requireNonNull(config.getConfigurationSection("world-settings.specifics")).getKeys(false)) {
                WorldProfile worldProfile = generateProfile("world-settings.specifics." + profileName, false);
                loadWorld(worldProfile);
                worldProfiles.add(worldProfile);
            }
        }

        plugin.getLogger().log(Level.INFO, () -> "Loaded all worlds");
    }

    public void loadWorld(WorldProfile worldProfile) {
        WorldCreator worldCreator = new WorldCreator(worldProfile.getName());
        worldCreator.type(worldProfile.getWorldType());
        worldCreator.generateStructures(worldProfile.getGenerateStructures());
        if (worldProfile.getVoid())
            worldCreator.generator(new VoidGenerator());
        worldCreator.createWorld();
        worldProfile.setWeather();
    }

    private WorldProfile generateProfile(String configPath, boolean isDuels) {
        YamlConfiguration config = configuration.getSettings();

        String[] pathParts = configPath.split("\\.");
        String profileName = pathParts[pathParts.length - 1];

        WorldProfile fallbackProfile = (isDuels ? duelsProfile : globalProfile);

        WorldType type = (fallbackProfile == null ? WorldType.NORMAL : fallbackProfile.getWorldType());
        if (config.isSet(configPath + ".type")) {
            try {
                type = WorldType.valueOf(config.getString(configPath + ".type"));
            } catch (IllegalArgumentException exception) {
                plugin.getLogger().log(Level.WARNING, "The world type for " + profileName + " is not valid. Defaulting to " + type.toString() + ".");
            }
        }
        boolean generateStructures = config.getBoolean(configPath + ".biome", (fallbackProfile == null || fallbackProfile.getGenerateStructures()));
        boolean isVoid = config.getBoolean(configPath + ".void", (fallbackProfile == null || fallbackProfile.getVoid()));
        boolean hunger = config.getBoolean(configPath + ".hunger", (fallbackProfile == null || fallbackProfile.getHunger()));
        boolean fallDamage = config.getBoolean(configPath + ".fall-damage", (fallbackProfile == null || fallbackProfile.getFallDamage()));
        String weatherLock = (fallbackProfile == null ? "NONE" : fallbackProfile.getWeatherLock());
        if (config.isSet(configPath + ".weather-lock")) {
            String temp = config.getString(configPath + ".weather-lock");
            if (temp == null) weatherLock = "NONE";
            else {
                try {
                    weatherLock = WeatherType.valueOf(temp).toString();
                } catch (IllegalArgumentException exception) {
                    plugin.getLogger().log(Level.WARNING, "The weather lock for " + profileName + " is not valid. Defaulting to " + weatherLock + ".");
                }
            }
        }
        String gameMode = (fallbackProfile == null ? "NONE" : fallbackProfile.getGameMode());
        if (config.isSet(configPath + ".gamemode")) {
            String temp = config.getString(configPath + ".gamemode");
            if (temp == null) gameMode = "NONE";
            else {
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
                fallDamage,
                weatherLock,
                gameMode
        );
    }

    public WorldProfile getWorldProfile(String name) {
        return this.worldProfiles.stream().filter(profile -> profile.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

