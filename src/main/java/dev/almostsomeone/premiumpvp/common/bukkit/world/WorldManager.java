package dev.almostsomeone.premiumpvp.common.bukkit.world;

import dev.almostsomeone.premiumpvp.Main;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class WorldManager {

    private final Plugin plugin;

    private final YamlConfiguration config = Main.getInstance().config.get();

    private WorldProfile globalProfile;
    private WorldProfile duelsProfile;

    public List<WorldProfile> worldProfiles;

    public WorldManager(final Plugin plugin) {
        this.plugin = plugin;

        this.worldProfiles = new ArrayList<>();
        this.loadWorlds();
    }

    public void loadWorlds() {
        this.globalProfile = this.generateProfile("world-settings.global", false);
        this.duelsProfile = this.generateProfile("world-settings.duels", false);

        if(this.config.isSet("world-settings.specifics")) {
            for(String profileName : this.config.getConfigurationSection("world-settings.specifics").getKeys(false)) {
                WorldProfile worldProfile = this.generateProfile("world-settings.specifics." + profileName, false);

                loadWorld(worldProfile);
                this.worldProfiles.add(worldProfile);
            }
        }
    }

    public void loadWorld(WorldProfile worldProfile) {
        WorldCreator worldCreator = new WorldCreator(worldProfile.name);
        worldCreator.type(worldProfile.type);
        worldCreator.generateStructures(worldProfile.generateStructures);
        if(worldProfile.isVoid)
            worldCreator.generator(new VoidGenerator());
        worldCreator.createWorld();
    }

    private WorldProfile generateProfile(String configPath, Boolean isDuels) {
        String[] pathParts = configPath.split("\\.");
        String profileName = pathParts[pathParts.length-1];

        WorldProfile fallbackProfile = (isDuels ? this.duelsProfile : this.globalProfile);

        WorldType type = (fallbackProfile == null ? WorldType.NORMAL : fallbackProfile.type);
        if(this.config.isSet(configPath + ".type")) {
            try {
                type = WorldType.valueOf(this.config.getString(configPath + ".type"));
            } catch (IllegalArgumentException exception) {
                this.plugin.getLogger().log(Level.WARNING, "The world type for " + profileName + " is not valid. Defaulting to " + type.toString() + ".");
            }
        }
        boolean generateStructures = (fallbackProfile == null || fallbackProfile.generateStructures);
        if(this.config.isSet(configPath + ".generate-structures"))
            generateStructures = this.config.getBoolean(configPath + ".biome");
        boolean isVoid = (fallbackProfile == null || fallbackProfile.isVoid);
        if(this.config.isSet(configPath + ".void"))
            isVoid = this.config.getBoolean(configPath + ".void");
        boolean hunger = (fallbackProfile == null || fallbackProfile.hunger);
        if(this.config.isSet(configPath + ".hunger"))
            hunger = this.config.getBoolean(configPath + ".hunger");
        String weatherLock = (fallbackProfile == null ? "NONE" : fallbackProfile.weatherLock);
        if(this.config.isSet(configPath + ".weather-lock")) {
            String temp = this.config.getString(configPath + ".weather-lock");
            if(temp.equalsIgnoreCase("NONE")) {
                weatherLock = "NONE";
            } else {
                try {
                    weatherLock = WeatherType.valueOf(temp).toString();
                } catch (IllegalArgumentException exception) {
                    this.plugin.getLogger().log(Level.WARNING, "The weather lock for " + profileName + " is not valid. Defaulting to " + weatherLock + ".");
                }
            }
        }
        String gameMode = (fallbackProfile == null ? "NONE" : fallbackProfile.gameMode);
        if(this.config.isSet(configPath + ".gamemode")) {
            String temp = this.config.getString(configPath + ".gamemode");
            if(temp.equalsIgnoreCase("NONE")) {
                gameMode = "NONE";
            } else {
                try {
                    gameMode = GameMode.valueOf(temp).toString();
                } catch (IllegalArgumentException exception) {
                    this.plugin.getLogger().log(Level.WARNING, "The gamemode for " + profileName + " is not valid. Defaulting to " + gameMode + ".");
                }
            }
        }

        WorldProfile worldProfile = new WorldProfile(
                profileName,
                type,
                generateStructures,
                isVoid,
                hunger,
                weatherLock,
                gameMode
        );

        return worldProfile;
    }

    public WorldProfile getWorldProfile(String name) {
        return this.worldProfiles.stream().filter(profile -> profile.name.equalsIgnoreCase(name)).findFirst().get();
    }
}
