package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.state.property.EnumProperty;
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
    private StructureWorldAccess world;
    private BlockPos groundLevel;

    public LayoutGenerator(String type) {
        LayoutGenerator.type = type;
    }

    void generateOuterRoof(BlockPos start, int n, int first) {
        for (int i = 1; i < n - 1; ++i) {
            world.setBlockState(start.east(i), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
            world.setBlockState(start.east(i).north(n - 1), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
        }
        for (int j = 1; j < n - 1; ++j) {
            world.setBlockState(start.north(j), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
            world.setBlockState(start.east(n - 1).north(j), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
        }
        for (int i = 1; i < n - 1; ++i) {
            for (int j = 1; j < n - 1; ++j) {
                world.setBlockState(start.east(i).north(j), Blocks.NETHER_BRICK_SLAB.getDefaultState(), 3);
            }
        }
        for (int i = 3; i < n - 3; ++i) {
            for (int j = 3; j < n - 3; ++j) {
                world.setBlockState(start.east(i).north(j), Blocks.NETHER_BRICKS.getDefaultState(), 3);
            }
        }
        for (int i = 4; i < n - 4; ++i) {
            world.setBlockState(start.east(i).north(4).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
        }
        for (int i = 4; i < n - 4; ++i) {
            world.setBlockState(start.east(i).north(n - 5).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
        }
        for (int j = 4; j < n - 4; ++j) {
            world.setBlockState(start.east(4).north(j).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
        }
        for (int j = 4; j < n - 4; ++j) {
            world.setBlockState(start.east(n - 5).north(j).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                world.setBlockState(start.east(i * (n - 1)).north(j * (n - 1)), Blocks.NETHER_BRICKS.getDefaultState(), 3);
                if (first == 0) {
                    world.setBlockState(start.east(i * (n - 1)).north(j * (n - 1)).up(1), Blocks.LANTERN.getDefaultState(), 3);
                } else {
                    for (int k = -4 + first; k < 0; ++k) {
                        world.setBlockState(start.east(i * (n - 1)).north(j * (n - 1)).up(k), Blocks.LANTERN.getDefaultState(), 3);
                    }
                }
            }
        }
        world.setBlockState(start.east(4).north(4).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        world.setBlockState(start.east(n - 5).north(4).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        world.setBlockState(start.east(4).north(n - 5).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        world.setBlockState(start.east(n - 5).north(n - 5).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        for (int i = 5; i < n - 5; ++i) {
            for (int j = 5; j < n - 5; ++j) {
                world.setBlockState(start.east(i).north(j).up(1), Blocks.NETHER_BRICKS.getDefaultState(), 3);
            }
        }
    }

    int[][] generate(MinecraftConfig mConfig) {
        int len = 0;
        int wid = 0;
        world = mConfig.world;
        groundLevel = mConfig.pos;

        if (type.equals("medieval")) {
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
        } else if (type.equals("modern")) {
            len = ThreadLocalRandom.current().nextInt(9, 14 + 1);
            wid = ThreadLocalRandom.current().nextInt(9, 14 + 1);

            ArrayList<Block> blockList = new ArrayList<>();
            blockList.add(Blocks.WHITE_CONCRETE);

            Random rand = new Random();

            for (int i = 0; i < len; ++i) {
                for (int j = 0; j < wid; ++j) {
                    world.setBlockState(groundLevel.north(j).east(i), blockList.get(rand.nextInt(blockList.size())).getDefaultState(), 3);
                }
            }
        } else if (type.equals("asian")) {
            len = 29;
            wid = 29;
            generateOuterRoof(groundLevel, 29, 0);
            generateOuterRoof(groundLevel.east(5).north(5).up(10), 19, 1);
            generateOuterRoof(groundLevel.east(7).north(7).up(17), 15, 2);
            for (int k = 0; k < 2; ++k) {
                for (int i = 6 + k; i < len - 6 - k; ++i) {
                    for (int j = 6 + k; j < wid - 6 - k; ++j) {
                        world.setBlockState(groundLevel.east(i).north(j).up(2 + k), Blocks.NETHER_BRICKS.getDefaultState(), 3);
                    }
                }
            }
            for (int k = 0; k < 2; ++k) {
                for (int i = 12 + k; i < len - 12 - k; ++i) {
                    world.setBlockState(groundLevel.east(i).north(6 + k).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
                }
            }
            for (int k = 0; k < 2; ++k) {
                for (int j = 12 + k; j < len - 12 - k; ++j) {
                    world.setBlockState(groundLevel.east(6 + k).north(j).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
                }
            }
            for (int k = 0; k < 2; ++k) {
                for (int i = 12 + k; i < len - 12 - k; ++i) {
                    world.setBlockState(groundLevel.east(i).north(wid - 7 - k).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
                }
            }
            for (int k = 0; k < 2; ++k) {
                for (int j = 12 + k; j < len - 12 - k; ++j) {
                    world.setBlockState(groundLevel.east(len - 7 - k).north(j).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
                }
            }
        } else {
            throw new IllegalArgumentException("Unknown type of houses: " + type);
        }
        return new int[len][wid];
    }
}