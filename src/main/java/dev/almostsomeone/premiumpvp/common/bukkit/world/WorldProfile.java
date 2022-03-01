package dev.almostsomeone.premiumpvp.common.bukkit.world;

import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;

public class WorldProfile {

    public String name;

    public World.Environment environment;
    public WorldType type;
    public Biome biome;
    public Boolean generateStructures;

    public Boolean isVoid;
    public Boolean hunger;

    public String weatherLock;
    public String gameMode;

    public WorldProfile(String name, World.Environment environment, WorldType type, Biome biome, Boolean generateStructures, Boolean isVoid, Boolean hunger, String weatherLock, String gameMode) {
        this.name = name;
        this.environment = environment;
        this.type = type; this.biome = biome;
        this.generateStructures = generateStructures;
        this.isVoid = isVoid;
        this.hunger = hunger;
        this.weatherLock = weatherLock;
        this.gameMode = gameMode;
    }
}
