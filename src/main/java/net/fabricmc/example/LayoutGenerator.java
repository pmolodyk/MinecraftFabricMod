package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;

public class LayoutGenerator {
    String configFile;

    int [][] generate(MinecraftConfig mConfig) {
        int len = 20;
        int wid = 10;
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < wid; j++) {
                world.setBlockState(groundLevel.east(i).north(j), Blocks.COBBLESTONE.getDefaultState(), 3);
            }
        }
        return new int[len][wid];
    }
}
