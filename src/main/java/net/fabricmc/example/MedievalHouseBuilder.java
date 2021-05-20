package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;

import static java.lang.Math.abs;

public class MedievalHouseBuilder extends HouseBuilder {

    @Override
    Pair<Integer, Integer> generateLayout(MinecraftConfig mConfig) {
        int len = 19;
        int wid = 9;
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
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
        world.setBlockState(groundLevel.north(4), Blocks.OAK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
        return new Pair<>(len, wid);
    }

    @Override
    int generateWalls(MinecraftConfig mConfig, int len, int wid) {
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        int wallHeight = 5;
        // clay for the front and the back wall
        for (int i = 0; i < 2; ++i) {
            for (int j = 1; j < wid - 1; ++j) {
                for (int k = 1; k < wallHeight + 2; ++k) {
                    world.setBlockState(groundLevel.north(j).east(i * (len - 1)).up(k), Blocks.WHITE_TERRACOTTA.getDefaultState(), 3);
                }
            }
        }
        // clay for the side walls
        for (int i = 1; i < len - 1; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 1; k < wallHeight - 1; ++k) {
                    world.setBlockState(groundLevel.east(i).north(j * (wid - 1)).up(k), Blocks.WHITE_TERRACOTTA.getDefaultState(), 3);
                }
            }
        }
        // vertical oak logs
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 1; k < wallHeight; ++k) {
                    world.setBlockState(groundLevel.east(i * 6).north(j * (wid - 1)).up(k), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                }
            }
        }
        // west-east oak logs
        for (int i = 1; i < len - 1; ++i) {
            for (int j = 0; j < 2; ++j) {
                world.setBlockState(groundLevel.east(i).north(j * (wid - 1)).up(wallHeight - 1), Blocks.STRIPPED_OAK_WOOD.getDefaultState().with(EnumProperty.of("axis", Direction.Axis.class), Direction.Axis.X), 3);
            }
        }
        // south-north oak logs
        for (int i = 0; i < 2; ++i) {
            for (int j = 1; j < wid - 1; ++j) {
                world.setBlockState(groundLevel.east(i * (len - 1)).north(j).up(wallHeight - 1), Blocks.STRIPPED_OAK_WOOD.getDefaultState().with(EnumProperty.of("axis", Direction.Axis.class), Direction.Axis.Z), 3);
            }
        }
        // side windows, south
        for (int i = 0; i < 3; ++i) {
            world.setBlockState(groundLevel.east(3 + i * 6).up(2), Blocks.OAK_TRAPDOOR.getDefaultState().with(BooleanProperty.of("open"), true), 3);
        }
        // side windows, north
        for (int i = 0; i < 3; ++i) {
            world.setBlockState(groundLevel.east(3 + i * 6).north(wid - 1).up(2), Blocks.OAK_TRAPDOOR.getDefaultState().with(BooleanProperty.of("open"), true).rotate(BlockRotation.CLOCKWISE_180), 3);
        }
        // side windows, west
        for (int j = 0; j < 2; ++j) {
            world.setBlockState(groundLevel.north(2 + j * 4).up(2), Blocks.OAK_TRAPDOOR.getDefaultState().with(BooleanProperty.of("open"), true).rotate(BlockRotation.CLOCKWISE_90), 3);
        }
        // door
        world.setBlockState(groundLevel.north(4).up(1), Blocks.AIR.getDefaultState(), 3);
        world.setBlockState(groundLevel.north(4).up(2), Blocks.AIR.getDefaultState(), 3);
        world.setBlockState(groundLevel.east(1).north(4).up(1), Blocks.OAK_DOOR.getDefaultState().with(EnumProperty.of("half", DoubleBlockHalf.class), DoubleBlockHalf.LOWER).rotate(BlockRotation.CLOCKWISE_90), 3);
        world.setBlockState(groundLevel.east(1).north(4).up(2), Blocks.OAK_DOOR.getDefaultState().with(EnumProperty.of("half", DoubleBlockHalf.class), DoubleBlockHalf.UPPER).rotate(BlockRotation.CLOCKWISE_90), 3);
        return wallHeight;
    }

    @Override
    boolean generateCeiling(MinecraftConfig mConfig, int len, int wid, int wallHeight) {
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
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
        return true;
    }
}