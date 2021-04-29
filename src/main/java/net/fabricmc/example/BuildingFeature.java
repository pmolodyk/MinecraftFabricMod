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
    houseBuilder = new ModernHouseBuilder(
        "{\"wall_block_type\": \"whiteConcreate\",\"ceiling_block_type\": \"whiteConcreate\",\"layout_type\": \"rectangle\",}");
  }

  @Override
  public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random,
      BlockPos pos,
      DefaultFeatureConfig config) {
    MinecraftConfig minecraftConfig = new MinecraftConfig(world, generator, random,
        world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos), config);
    return houseBuilder.build(minecraftConfig);
//        BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
//        Direction offset = Direction.NORTH;
//
//        for (int y = 1; y <= 15; y++) {
//            offset = offset.rotateYClockwise();
//            world.setBlockState(topPos.up(y).offset(offset), Blocks.STONE.getDefaultState(), 3);
//        }
//
//        return true;
  }
}