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
        int type = ((int)((Math.random() * (4 - 1)) + 1));
        if (type == 1) {
            houseBuilder = new MedievalHouseBuilder();
        } else if (type == 2) {
            houseBuilder = new ModernHouseBuilder();
        } else {
            houseBuilder = new AsianHouseBuilder();
        }
        return houseBuilder.build(minecraftConfig);
    }
}