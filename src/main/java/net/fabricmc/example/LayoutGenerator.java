package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LayoutGenerator {

    private static String type = null;

    public LayoutGenerator(String type) {
        this.type = type;
    }

    int[][] generate(MinecraftConfig mConfig) {
        int len = 0;
        int wid = 0;
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;

        if (type == "medieval") {
            len = 19;
            wid = 9;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < wid; j++) {
                    world.setBlockState(groundLevel.east(i).north(j), Blocks.COBBLESTONE.getDefaultState(), 3);
                }
            }
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j) {
                    world.setBlockState(groundLevel.east(i * (len - 1)).north(j * (wid - 1)), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                }
            }
            world.setBlockState(groundLevel.north(4), Blocks.OAK_STAIRS.rotate(Blocks.OAK_STAIRS.getDefaultState(), BlockRotation.CLOCKWISE_90), 3);
        } else if (type == "modern") {
            len = ThreadLocalRandom.current().nextInt(5, 15 + 1);
            wid = ThreadLocalRandom.current().nextInt(5, 15 + 1);

            ArrayList<Block> blockList = new ArrayList<>();
            blockList.add(Blocks.WHITE_CONCRETE);

            Random rand = new Random();

            for (int i = 0; i < len; ++i) {
                for (int j = 0; j < wid; ++j) {
                    world.setBlockState(groundLevel.north(j).east(i), blockList.get(rand.nextInt(blockList.size())).getDefaultState(), 3);
                }
            }
        } else {
            throw new IllegalArgumentException("Unknown type of houses: " + type);
        }
        return new int[len][wid];
    }
}