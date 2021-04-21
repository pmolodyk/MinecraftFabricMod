package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;

public class CeilingGenerator {
    String configFile;

    boolean generate(MinecraftConfig mConfig, int[][] layout, int wallHeight) {
        int len = layout.length;
        if (len == 0) {
            throw new IllegalStateException();
        }
        int wid = layout[0].length;
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < wid; j++) {
                world.setBlockState(groundLevel.east(i).north(j).up(wallHeight), Blocks.COBBLESTONE.getDefaultState(), 3);
            }
        }
        return true;
    }
}
