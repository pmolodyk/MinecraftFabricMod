package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;

public class ExternalWallGenerator {
    String configFile;
    private final int wallHeight = 5;
    int generate(MinecraftConfig mConfig, int[][] layout) {
        int len = layout.length;
        if (len == 0) {
            throw new IllegalStateException();
        }
        int wid = layout[0].length;
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        for (int i = 0; i < len; i++) {
            for (int k = 0; k < wallHeight; k++) {
                world.setBlockState(groundLevel.east(i).up(k), Blocks.COBBLESTONE.getDefaultState(), 3);
                world.setBlockState(groundLevel.east(i).north(wid - 1).up(k), Blocks.COBBLESTONE.getDefaultState(), 3);
            }
        }
        for (int i = 0; i < wid; i++) {
            for (int k = 0; k < wallHeight; k++) {
                world.setBlockState(groundLevel.north(i).up(k), Blocks.COBBLESTONE.getDefaultState(), 3);
                world.setBlockState(groundLevel.north(i).east(len - 1).up(k), Blocks.COBBLESTONE.getDefaultState(), 3);
            }
        }
        return wallHeight;
    }
}
