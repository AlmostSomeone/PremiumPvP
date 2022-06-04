package dev.almostsomeone.premiumpvp.world;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    @Override @Deprecated
    public @Nonnull ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int cx, int cz, @Nonnull BiomeGrid biomeGrid) {
        final ChunkData chunkData = createChunkData(world);

        Biome biome = Biome.PLAINS;
        for (int x = 0; x <= 15; x++) {
            for (int z = 0; z <= 15; z++)
                biomeGrid.setBiome(x, z, biome);
        }

        return chunkData;
    }

    @Override
    public boolean canSpawn(@Nonnull World world, int x, int z) {
        return true;
    }

    @Override
    public @Nonnull List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
        return Collections.emptyList();
    }
}