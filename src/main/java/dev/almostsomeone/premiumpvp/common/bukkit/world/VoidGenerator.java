package dev.almostsomeone.premiumpvp.common.bukkit.world;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    @Override @Deprecated
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int cx, int cz, @NotNull BiomeGrid biomeGrid) {
        final ChunkData chunkData = createChunkData(world);

        Biome biome = Biome.PLAINS;
        for (int x = 0; x <= 15; x++) {
            for (int z = 0; z <= 15; z++)
                biomeGrid.setBiome(x, z, biome);
        }

        return chunkData;
    }

    @Override
    public boolean canSpawn(@NotNull World world, int x, int z) {
        return true;
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return Collections.emptyList();
    }
}