package dev.almostsomeone.premiumpvp.common.bukkit.world;

import org.bukkit.WorldType;

public class WorldProfile {

    public String name;

    public WorldType type;
    public Boolean generateStructures;

    public Boolean isVoid;
    public Boolean hunger;

    public String weatherLock;
    public String gameMode;

    public WorldProfile(String name, WorldType type, Boolean generateStructures, Boolean isVoid, Boolean hunger, String weatherLock, String gameMode) {
        this.name = name;
        this.type = type;
        this.generateStructures = generateStructures;
        this.isVoid = isVoid;
        this.hunger = hunger;
        this.weatherLock = weatherLock;
        this.gameMode = gameMode;
    }
}
