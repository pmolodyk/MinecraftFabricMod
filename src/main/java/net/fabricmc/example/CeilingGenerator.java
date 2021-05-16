package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.StairShape;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.abs;

public class CeilingGenerator {

    String type;

    public CeilingGenerator(String type) {
        this.type = type;
    }

    boolean generate(MinecraftConfig mConfig, int len, int wid, int wallHeight) {

        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;

        switch (type) {
            case "medieval":
                // stripped oak
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
                        world.setBlockState(groundLevel.east(i).north(5 + j * 2).up(7 - j), Blocks.OAK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
                    }
                }
                // chimney
                int fire_i = 5;
                int fire_j = 6;
                int fire_k = 8;
                world.setBlockState(groundLevel.east(fire_i).up(fire_k).north(fire_j), Blocks.CAMPFIRE.getDefaultState(), 3);
                for (int i = -1; i < 2; ++i) {
                    for (int j = -1; j < 2; ++j) {
                        for (int k = -fire_k; k < 2; ++k) {
                            if (Math.abs(i - j) != 1) {
                                continue;
                            }
                            world.setBlockState(groundLevel.east(fire_i + i).up(fire_k + k).north(fire_j + j), Blocks.COBBLESTONE.getDefaultState(), 3);
                        }
                    }
                }
                break;
            case "modern": {
                ArrayList<Block> glassList = new ArrayList<>();

                Random rand = new Random();

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
                break;
            }
            case "asian": {
                int n = 9;
                int delta = 10;

                // platform
                for (int i = delta + 1; i < len - delta - 1; ++i) {
                    for (int j = delta + 1; j < len - delta - 1; ++j) {
                        world.setBlockState(groundLevel.north(j).east(i).up(wallHeight), Blocks.NETHER_BRICK_SLAB.getDefaultState(), 3);
                    }
                }
                for (int i = delta + 2; i < len - 2 - delta; ++i) {
                    for (int j = delta + 2; j < wid - 2 - delta; ++j) {
                        world.setBlockState(groundLevel.east(i).north(j).up(wallHeight), Blocks.NETHER_BRICKS.getDefaultState(), 3);
                    }
                }

                // platform edges
                for (int i = 1; i < n - 1; ++i) {
                    world.setBlockState(groundLevel.east(delta + i).north(delta).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
                    world.setBlockState(groundLevel.east(delta + i).north(delta + n - 1).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
                }
                for (int j = 1; j < n - 1; ++j) {
                    world.setBlockState(groundLevel.east(delta).north(delta + j).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
                    world.setBlockState(groundLevel.east(delta + n - 1).north(delta + j).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
                }
                world.setBlockState(groundLevel.east(delta).north(delta).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.INNER_LEFT), 3);
                world.setBlockState(groundLevel.east(delta + n - 1).north(delta).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180).with(EnumProperty.of("shape", StairShape.class), StairShape.INNER_LEFT), 3);
                world.setBlockState(groundLevel.east(delta).north(delta + n - 1).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(EnumProperty.of("shape", StairShape.class), StairShape.INNER_LEFT), 3);
                world.setBlockState(groundLevel.east(delta + n - 1).north(delta + n - 1).up(wallHeight), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.INNER_LEFT), 3);

                // lanterns
                world.setBlockState(groundLevel.east(delta).north(delta).up(wallHeight - 1), Blocks.LANTERN.getDefaultState(), 3);
                world.setBlockState(groundLevel.east(delta + n - 1).north(delta).up(wallHeight - 1), Blocks.LANTERN.getDefaultState(), 3);
                world.setBlockState(groundLevel.east(delta).north(delta + n - 1).up(wallHeight - 1), Blocks.LANTERN.getDefaultState(), 3);
                world.setBlockState(groundLevel.east(delta + n - 1).north(delta + n - 1).up(wallHeight - 1), Blocks.LANTERN.getDefaultState(), 3);

                // hat
                world.setBlockState(groundLevel.east(len / 2 - 1).north(wid / 2).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
                world.setBlockState(groundLevel.east(len / 2 + 1).north(wid / 2).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
                world.setBlockState(groundLevel.east(len / 2).north(wid / 2 + 1).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
                world.setBlockState(groundLevel.east(len / 2).north(wid / 2 - 1).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState(), 3);

                world.setBlockState(groundLevel.east(len / 2 + 1).north(wid / 2 - 1).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
                world.setBlockState(groundLevel.east(len / 2 - 1).north(wid / 2 - 1).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
                world.setBlockState(groundLevel.east(len / 2 + 1).north(wid / 2 + 1).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
                world.setBlockState(groundLevel.east(len / 2 - 1).north(wid / 2 + 1).up(wallHeight + 1), Blocks.RED_NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);

                world.setBlockState(groundLevel.east(len / 2).north(wid / 2).up(wallHeight + 1), Blocks.RED_NETHER_BRICKS.getDefaultState(), 3);

                // spire
                for (int k = 2; k < 4; ++k) {
                    world.setBlockState(groundLevel.east(len / 2).north(wid / 2).up(wallHeight + k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState(), 3);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown ceiling type");
        }
        return true;
    }
}