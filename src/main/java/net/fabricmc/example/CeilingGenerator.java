package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.json.JSONObject;

import static java.lang.Math.abs;

public class CeilingGenerator {

    String type;

    public CeilingGenerator(String type) {
        this.type = type;
    }

    boolean generate(MinecraftConfig mConfig, int[][] layout, int wallHeight) {
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;

        if (type == "medieval") {
            // stripped oak (whatever)
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 5; ++j) {
                     world.setBlockState(groundLevel.east(i * 18).north(j * 2).up(-abs(j - 2) + 7), Blocks.OAK_PLANKS.getDefaultState(), 3);
                }
            }
            // slabs
            for (int i = 0; i < 19; ++i) {
                for (int j = 0; j < 5; ++j) {
                    world.setBlockState(groundLevel.east(i).north(j * 2).up(-abs(j - 2) + 8), Blocks.OAK_SLAB.getDefaultState(), 3);
                }
            }
            // stairs facing south
            for (int i = 0; i < 19; ++i) {
                for (int j = 0; j < 3; ++j) {
                    world.setBlockState(groundLevel.east(i).north(-1 + j * 2).up(5 + j), Blocks.OAK_STAIRS.getDefaultState(), 3);
                }
            }
            // stairs facing north
            for (int i = 0; i < 19; ++i) {
                for (int j = 0; j < 3; ++j) {
                    world.setBlockState(groundLevel.east(i).north(5 + j * 2).up(7 - j), Blocks.OAK_STAIRS.rotate(Blocks.OAK_STAIRS.getDefaultState(), BlockRotation.CLOCKWISE_180), 3);
                }
            }
        } else if (type == "modern") {

        } else {
            throw new IllegalArgumentException("Unknown ceiling type");
        }
        return true;
    }
}