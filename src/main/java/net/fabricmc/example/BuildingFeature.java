package net.fabricmc.example;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class BuildingFeature extends Feature<DefaultFeatureConfig> {

    private HouseBuilder houseBuilder;

    public BuildingFeature(Codec<DefaultFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random,
        BlockPos pos,
        DefaultFeatureConfig config) {
        MinecraftConfig minecraftConfig = new MinecraftConfig(world, generator, random,
            world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos), config);
        if (((int)((Math.random() * (3 - 1)) + 1)) == 1) {
            houseBuilder = new MedievalHouseBuilder();
        } else {
            houseBuilder = new ModernHouseBuilder();
        }
        return houseBuilder.build(minecraftConfig);
    }
}