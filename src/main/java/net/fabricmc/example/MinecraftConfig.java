package net.fabricmc.example;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;

public class MinecraftConfig {
    StructureWorldAccess world;
    ChunkGenerator generator;
    Random random;
    BlockPos pos;
    DefaultFeatureConfig config;

    MinecraftConfig(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos,
                    DefaultFeatureConfig config) {
        this.world = world;
        this.generator = generator;
        this.random = random;
        this.pos = pos;
        this.config = config;
    }
}
