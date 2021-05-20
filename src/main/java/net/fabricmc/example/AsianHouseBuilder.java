package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.block.enums.StairShape;
import net.minecraft.block.enums.WallShape;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

public class AsianHouseBuilder extends HouseBuilder {
    private StructureWorldAccess world;

    void generateOuterRoof(BlockPos start, int n, int first) {
        // outer stairs
        for (int i = 1; i < n - 1; ++i) {
            world.setBlockState(start.east(i), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
            world.setBlockState(start.east(i).north(n - 1), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
        }
        for (int j = 1; j < n - 1; ++j) {
            world.setBlockState(start.north(j), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
            world.setBlockState(start.east(n - 1).north(j), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
        }
        // next-to-outer slabs
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
        // elevation
        for (int i = 4; i < n - 4; ++i) {
            world.setBlockState(start.east(i).north(4).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState(), 3);
            world.setBlockState(start.east(i).north(n - 5).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
        }
        for (int j = 4; j < n - 4; ++j) {
            world.setBlockState(start.east(4).north(j).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
            world.setBlockState(start.east(n - 5).north(j).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
        }
        // lanterns
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
        // elevation corners
        world.setBlockState(start.east(4).north(4).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        world.setBlockState(start.east(n - 5).north(4).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        world.setBlockState(start.east(4).north(n - 5).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        world.setBlockState(start.east(n - 5).north(n - 5).up(1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90).with(EnumProperty.of("shape", StairShape.class), StairShape.OUTER_LEFT), 3);
        // elevation top
        for (int i = 5; i < n - 5; ++i) {
            for (int j = 5; j < n - 5; ++j) {
                world.setBlockState(start.east(i).north(j).up(1), Blocks.NETHER_BRICKS.getDefaultState(), 3);
            }
        }
    }

    Pair<Integer, Integer> generateLayout(MinecraftConfig mConfig) {
        int len;
        int wid;
        world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
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
                world.setBlockState(groundLevel.east(i).north(wid - 7 - k).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_180), 3);
            }
        }
        for (int k = 0; k < 2; ++k) {
            for (int j = 12 + k; j < len - 12 - k; ++j) {
                world.setBlockState(groundLevel.east(6 + k).north(j).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), 3);
                world.setBlockState(groundLevel.east(len - 7 - k).north(j).up(2 + k), Blocks.NETHER_BRICK_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);
            }
        }
        return new Pair<>(len, wid);
    }

    private void generateLevel(BlockPos start, int a, int n) { // a -- len/wid, n -- level number
        int height = 6 - n;
        for (int k = 0; k < height; ++k) {
            world.setBlockState(start.up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("east", WallShape.class), WallShape.TALL)
                    .with(EnumProperty.of("north", WallShape.class), WallShape.TALL), 3);
            world.setBlockState(start.east(a - 1).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("west", WallShape.class), WallShape.TALL)
                    .with(EnumProperty.of("north", WallShape.class), WallShape.TALL), 3);
            world.setBlockState(start.north(a - 1).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("east", WallShape.class), WallShape.TALL)
                    .with(EnumProperty.of("south", WallShape.class), WallShape.TALL), 3);
            world.setBlockState(start.north(a - 1).east(a - 1).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("west", WallShape.class), WallShape.TALL)
                    .with(EnumProperty.of("south", WallShape.class), WallShape.TALL), 3);
        }
        int door_a = (int) (0.25 * a + 0.75);
        for (int i = 1; i < a - 1; ++i) {
            for (int j = 0; j < 2; ++j) {
                for (int k = 0; k < height; ++k) {
                    if (k == door_a && i >= door_a && i <= a - 1 - door_a) {
                        world.setBlockState(start.east(i).north(j * (a - 1)).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("east", WallShape.class), WallShape.TALL)
                                .with(EnumProperty.of("west", WallShape.class), WallShape.TALL), 3);
                    } else if (k >= door_a || i < door_a || i > a - 1 - door_a) {
                        world.setBlockState(start.east(i).north(j * (a - 1)).up(k), Blocks.END_STONE_BRICK_WALL.getDefaultState().with(EnumProperty.of("east", WallShape.class), WallShape.TALL)
                                .with(EnumProperty.of("west", WallShape.class), WallShape.TALL), 3);
                    }
                }
            }
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 1; j < a - 1; ++j) {
                for (int k = 0; k < height; ++k) {
                    if (k == door_a && j >= door_a && j <= a - 1 - door_a) {
                        world.setBlockState(start.east(i * (a - 1)).north(j).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("north", WallShape.class), WallShape.TALL)
                                .with(EnumProperty.of("south", WallShape.class), WallShape.TALL), 3);
                    } else if (k >= door_a || j < door_a || j > a - 1 - door_a) {
                        world.setBlockState(start.east(i * (a - 1)).north(j).up(k), Blocks.END_STONE_BRICK_WALL.getDefaultState().with(EnumProperty.of("north", WallShape.class), WallShape.TALL)
                                .with(EnumProperty.of("south", WallShape.class), WallShape.TALL), 3);
                    }
                }
            }
        }
        if (a == 5) {
            for (int k = 0; k < door_a; ++k) {
                for (int s = 0; s < 2; ++s) {
                    world.setBlockState(start.east(a / 2).north(s * (a - 1)).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("east", WallShape.class), WallShape.TALL)
                            .with(EnumProperty.of("west", WallShape.class), WallShape.TALL), 3);
                    world.setBlockState(start.east(s * (a - 1)).north(a / 2).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("north", WallShape.class), WallShape.TALL)
                            .with(EnumProperty.of("south", WallShape.class), WallShape.TALL), 3);
                }
            }
        } else if (a > 5) {
            for (int k = 0; k < door_a; ++k) {
                for (int j = 0; j < 2; ++j) {
                    world.setBlockState(start.east(door_a).north(j * (a - 1)).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("west", WallShape.class), WallShape.TALL), 3);
                    world.setBlockState(start.east(a - 1 - door_a).north(j * (a - 1)).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("east", WallShape.class), WallShape.TALL), 3);
                }
                for (int i = 0; i < 2; ++i) {
                    world.setBlockState(start.east(i * (a - 1)).north(door_a).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("south", WallShape.class), WallShape.TALL), 3);
                    world.setBlockState(start.east(i * (a - 1)).north(a - 1 - door_a).up(k), Blocks.RED_NETHER_BRICK_WALL.getDefaultState().with(EnumProperty.of("north", WallShape.class), WallShape.TALL), 3);
                }
            }
        }
    }

    @Override
    int generateWalls(MinecraftConfig mConfig, int len, int wid) {
        world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        int wallHeight = 5;
        generateLevel(groundLevel.east(8).north(8).up(4), 13, 0);
        generateLevel(groundLevel.east(10).north(10).up(12), 9, 1);
        generateLevel(groundLevel.east(12).north(12).up(19), 5, 2);
        wallHeight = 23;
        return wallHeight;
    }

    @Override
    boolean generateCeiling(MinecraftConfig mConfig, int len, int wid, int wallHeight) {
        int n = 9;
        int delta = 10;
        world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;

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
        return true;
    }
}