package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class ExternalWallGenerator {

    String type;
    int len = 0;
    int wid = 0;
    int door_j = 0;

    public ExternalWallGenerator(String type) {
        this.type = type;
    }

    private boolean isAroundDoor(int j, int k) {
        return Math.abs(j - door_j) <= 1 && k <= 3;
    }
    private boolean isDoor(int j, int k) {
        return j == door_j && (k == 2 || k == 1);
    }

    int generate(MinecraftConfig mConfig, int[][] layout) {

        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        int wallHeight = 5;
        int len = layout.length;
        int wid = layout[0].length;

        if (type == "medieval") {
            // clay for the front and the back wall
            for (int i = 0; i < 2; ++i) {
                for (int j = 1; j < wid - 1; ++j) {
                    for (int k = 1; k < wallHeight + 2; ++k) {
                        world.setBlockState(groundLevel.north(j).east(i * 18).up(k), Blocks.WHITE_TERRACOTTA.getDefaultState(), 3);
                    }
                }
            }
            // clay for the side walls
            for (int i = 1; i < len - 1; ++i) {
                for (int j = 0; j < 2; ++j) {
                    for (int k = 1; k < wallHeight - 1; ++k) {
                        world.setBlockState(groundLevel.east(i).north(j * 8).up(k), Blocks.WHITE_TERRACOTTA.getDefaultState(), 3);
                    }
                }
            }
            // vertical oak logs
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 2; ++j) {
                    for (int k = 1; k < wallHeight; ++k) {
                        world.setBlockState(groundLevel.east(i * 6).north(j * 8).up(k), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                    }
                }
            }
            // west-east oak logs
            for (int i = 1; i < len - 1; ++i) {
                for (int j = 0; j < 2; ++j) {
                    world.setBlockState(groundLevel.east(i).north(j * 8).up(4), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                }
            }
            // south-north oak logs
            for (int i = 0; i < 2; ++i) {
                for (int j = 1; j < wid - 1; ++j) {
                    world.setBlockState(groundLevel.east(i * 18).north(j).up(4), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                }
            }
            // side windows, south
            for (int i = 0; i < 3; ++i) {
                world.setBlockState(groundLevel.east(3 + i * 6).up(2), Blocks.OAK_TRAPDOOR.getDefaultState().with(BooleanProperty.of("open"), true), 3);
            }
            // side windows, north
            for (int i = 0; i < 3; ++i) {
                world.setBlockState(groundLevel.east(3 + i * 6).north(8).up(2), Blocks.OAK_TRAPDOOR.getDefaultState().with(BooleanProperty.of("open"), true).rotate(BlockRotation.CLOCKWISE_180), 3);
            }
            // side windows, west
            for (int j = 0; j < 2; ++j) {
                world.setBlockState(groundLevel.north(2 + j * 4).up(2), Blocks.OAK_TRAPDOOR.getDefaultState().with(BooleanProperty.of("open"), true).rotate(BlockRotation.CLOCKWISE_90), 3);
            }
            // door
            world.setBlockState(groundLevel.north(4).up(1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(groundLevel.north(4).up(2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(groundLevel.east(1).north(4).up(1), Blocks.OAK_DOOR.rotate(Blocks.OAK_DOOR.getDefaultState(), BlockRotation.CLOCKWISE_90), 3);
        } else if (type == "modern") {
            ArrayList<Block> concreteList = new ArrayList<>();
            concreteList.add(Blocks.WHITE_CONCRETE);
            concreteList.add(Blocks.BLACK_CONCRETE);
            concreteList.add(Blocks.BLUE_CONCRETE);
            concreteList.add(Blocks.CYAN_CONCRETE);
            concreteList.add(Blocks.GREEN_CONCRETE);
            concreteList.add(Blocks.LIME_CONCRETE);
            concreteList.add(Blocks.LIGHT_BLUE_CONCRETE);

            ArrayList<Block> glassList = new ArrayList<>();
            glassList.add(Blocks.WHITE_STAINED_GLASS_PANE);
            glassList.add(Blocks.BLACK_STAINED_GLASS_PANE);
            glassList.add(Blocks.BLUE_STAINED_GLASS_PANE);
            glassList.add(Blocks.CYAN_STAINED_GLASS_PANE);
            glassList.add(Blocks.GREEN_STAINED_GLASS_PANE);
            glassList.add(Blocks.LIME_STAINED_GLASS_PANE);
            glassList.add(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);

            Random rand = new Random();

            len = layout.length;
            wid = layout[0].length;
            wallHeight = ThreadLocalRandom.current().nextInt(2, 5 + 1);
            int n = (int) Math.ceil(Math.sqrt(2 * len + 0.25) - 0.5);

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
                                world.setBlockState(groundLevel.east(i).north(j * (wid - 1)).up(k), glassList.get(color).getDefaultState(), 3);
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
                        world.setBlockState(groundLevel.north(j).up(k), glassList.get(color).getDefaultState(), 3);
                    }
                }
            }

            // door
            door_j = rand.nextInt(wid - 2) + 1;
            world.setBlockState(groundLevel.north(door_j).east(len - 1).up(1), Blocks.WARPED_DOOR.getDefaultState(), 3);
            world.setBlockState(groundLevel.north(door_j).east(len - 1), Blocks.QUARTZ_STAIRS.getDefaultState().rotate(BlockRotation.COUNTERCLOCKWISE_90), 3);

            // front wall
            for (int j = 0; j < wid; ++j) {
                int color = rand.nextInt(concreteList.size());
                for (int k = 1; k < wallHeight + n - 1; ++k) {
                    if (isDoor(j, k)) {
                        continue;
                    } else if (isAroundDoor(j, k) || j == 0 || j == wid - 1 || k == 1 || k == wallHeight + n - 2) {
                        world.setBlockState(groundLevel.north(j).up(k).east(len - 1), concreteList.get(color).getDefaultState(), 3);

                    } else {
                        world.setBlockState(groundLevel.north(j).up(k).east(len - 1), glassList.get(color).getDefaultState(), 3);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Illegal walls type");
        }
        return wallHeight;
    }
}