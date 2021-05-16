package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.block.enums.WallShape;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class ExternalWallGenerator {

    String type;
    int door_j = 0;
    private StructureWorldAccess world;

    public ExternalWallGenerator(String type) {
        this.type = type;
    }

    private boolean isAroundDoor(int j, int k) {
        return Math.abs(j - door_j) <= 1 && k <= 3;
    }
    private boolean isDoor(int j, int k) {
        return j == door_j && (k == 2 || k == 1);
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

    int generate(MinecraftConfig mConfig, int len, int wid) {

        world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        int wallHeight = 5;

        switch (type) {
            case "medieval":
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
                break;
            case "modern":
                ArrayList<Block> concreteList = new ArrayList<>();

                ArrayList<Block> glassList = new ArrayList<>();

                Random rand = new Random();

                wallHeight = ThreadLocalRandom.current().nextInt(2, 5 + 1);
                int n = (int) Math.ceil(Math.sqrt(2 * len + 0.25) - 0.5);

                concreteList.add(Blocks.WHITE_CONCRETE);
                if (len % 2 == 0) { // cold color scheme
                    concreteList.add(Blocks.BLUE_CONCRETE);
                    concreteList.add(Blocks.CYAN_CONCRETE);
                    concreteList.add(Blocks.GREEN_CONCRETE);
                    concreteList.add(Blocks.LIME_CONCRETE);
                    concreteList.add(Blocks.LIGHT_BLUE_CONCRETE);

                    glassList.add(Blocks.WHITE_STAINED_GLASS_PANE);
                    glassList.add(Blocks.BLUE_STAINED_GLASS_PANE);
                    glassList.add(Blocks.CYAN_STAINED_GLASS_PANE);
                    glassList.add(Blocks.GREEN_STAINED_GLASS_PANE);
                    glassList.add(Blocks.LIME_STAINED_GLASS_PANE);
                    glassList.add(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
                } else { // warm color scheme
                    concreteList.add(Blocks.YELLOW_CONCRETE);
                    concreteList.add(Blocks.BROWN_CONCRETE);
                    concreteList.add(Blocks.ORANGE_CONCRETE);
                    concreteList.add(Blocks.RED_CONCRETE);

                    glassList.add(Blocks.WHITE_STAINED_GLASS_PANE);
                    glassList.add(Blocks.YELLOW_STAINED_GLASS_PANE);
                    glassList.add(Blocks.BROWN_STAINED_GLASS_PANE);
                    glassList.add(Blocks.ORANGE_STAINED_GLASS_PANE);
                    glassList.add(Blocks.RED_STAINED_GLASS_PANE);
                }

                // side walls
                int i_pos = 1;
                for (int s = 1; s < n; ++s) {
                    int lim = i_pos + s + 1;
                    for (int i = i_pos; i < Math.min(lim, len - 1); ++i, ++i_pos) {
                        int color = rand.nextInt(concreteList.size());
                        for (int k = 1; k < wallHeight + s; ++k) {
                            for (int j = 0; j < 2; ++j) {
                                if (k == 1 || k == wallHeight + s - 1) {
                                    world.setBlockState(groundLevel.east(i).north(j * (wid - 1)).up(k), concreteList.get(color).getDefaultState(), 3);
                                } else {
                                    world.setBlockState(groundLevel.east(i).north(j * (wid - 1)).up(k), glassList.get(color).getDefaultState().with(BooleanProperty.of("west"), true)
                                            .with(BooleanProperty.of("east"), true), 3);
                                }
                            }
                        }
                    }
                }

                // back wall
                for (int j = 0; j < wid; ++j) {
                    int color = rand.nextInt(concreteList.size());
                    for (int k = 1; k < wallHeight; ++k) {
                        if (k == 1 || k == wallHeight - 1 || j == 0 || j == wid - 1) {
                            world.setBlockState(groundLevel.north(j).up(k), concreteList.get(color).getDefaultState(), 3);
                        } else {
                            world.setBlockState(groundLevel.north(j).up(k), glassList.get(color).getDefaultState().with(BooleanProperty.of("north"), true)
                                    .with(BooleanProperty.of("south"), true), 3);
                        }
                    }
                }

                // door
                door_j = rand.nextInt(wid - 2) + 1;
                world.setBlockState(groundLevel.north(door_j).east(len - 1).up(1), Blocks.BIRCH_DOOR.getDefaultState().with(EnumProperty.of("half", DoubleBlockHalf.class), DoubleBlockHalf.LOWER).rotate(BlockRotation.CLOCKWISE_90), 3);
                world.setBlockState(groundLevel.north(door_j).east(len - 1).up(2), Blocks.BIRCH_DOOR.getDefaultState().with(EnumProperty.of("half", DoubleBlockHalf.class), DoubleBlockHalf.UPPER).rotate(BlockRotation.CLOCKWISE_90), 3);
                world.setBlockState(groundLevel.north(door_j).east(len - 1), Blocks.QUARTZ_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);

                // front wall
                for (int j = 0; j < wid; ++j) {
                    int color = rand.nextInt(concreteList.size());
                    for (int k = 1; k < wallHeight + n - 1; ++k) {
                        if (!isDoor(j, k)) {
                            if (isAroundDoor(j, k) || j == 0 || j == wid - 1 || k == 1 || k == wallHeight + n - 2) {
                                world.setBlockState(groundLevel.north(j).up(k).east(len - 1), concreteList.get(color).getDefaultState(), 3);

                            } else {
                                world.setBlockState(groundLevel.north(j).up(k).east(len - 1), glassList.get(color).getDefaultState().with(BooleanProperty.of("north"), true)
                                        .with(BooleanProperty.of("south"), true), 3);
                            }
                        }
                    }
                }
                break;
            case "asian":
                generateLevel(groundLevel.east(8).north(8).up(4), 13, 0);
                generateLevel(groundLevel.east(10).north(10).up(12), 9, 1);
                generateLevel(groundLevel.east(12).north(12).up(19), 5, 2);
                wallHeight = 23;
                break;
            default:
                throw new IllegalArgumentException("Illegal walls type");
        }
        return wallHeight;
    }
}