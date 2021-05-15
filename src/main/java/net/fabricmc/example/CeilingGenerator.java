package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
            ArrayList<Block> glassList = new ArrayList<>();

            Random rand = new Random();

            int len = layout.length;
            int wid = layout[0].length;
            int n = (int) Math.ceil(Math.sqrt(2 * len + 0.25) - 0.5);

            if (len % 2 == 0) { // cold color scheme
                glassList.add(Blocks.WHITE_STAINED_GLASS);
                glassList.add(Blocks.BLUE_STAINED_GLASS);
                glassList.add(Blocks.CYAN_STAINED_GLASS);
                glassList.add(Blocks.GREEN_STAINED_GLASS);
                glassList.add(Blocks.LIME_STAINED_GLASS);
                glassList.add(Blocks.LIGHT_BLUE_STAINED_GLASS);
            } else {
                glassList.add(Blocks.WHITE_STAINED_GLASS);
                glassList.add(Blocks.YELLOW_STAINED_GLASS);
                glassList.add(Blocks.BROWN_STAINED_GLASS);
                glassList.add(Blocks.ORANGE_STAINED_GLASS);
                glassList.add(Blocks.RED_STAINED_GLASS);
            }

            int i_pos = 0;
            for (int s = 0; s < n; ++s) {
                int color = rand.nextInt(glassList.size());
                int lim = i_pos + s + 1;
                for (int i = i_pos; i < lim; ++i, ++i_pos) {
                    for (int j = 0; j < wid; ++j) {
                        world.setBlockState(groundLevel.north(j).east(i).up(wallHeight + s), glassList.get(color).getDefaultState(), 3);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Unknown ceiling type");
        }
        return true;
    }
}