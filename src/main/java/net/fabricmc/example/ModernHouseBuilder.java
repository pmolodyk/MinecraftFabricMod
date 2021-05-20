package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ModernHouseBuilder extends HouseBuilder{
    private int door_j = 0;

    private boolean isAroundDoor(int j, int k) {
        return Math.abs(j - door_j) <= 1 && k <= 3;
    }
    private boolean isDoor(int j, int k) {
        return j == door_j && (k == 2 || k == 1);
    }

    @Override
    Pair<Integer, Integer> generateLayout(MinecraftConfig mConfig) {
        int len;
        int wid;
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        len = ThreadLocalRandom.current().nextInt(9, 14 + 1);
        wid = ThreadLocalRandom.current().nextInt(9, 14 + 1);

        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < wid; ++j) {
                world.setBlockState(groundLevel.north(j).east(i), Blocks.WHITE_CONCRETE.getDefaultState(), 3);
            }
        }
        return new Pair<>(len, wid);
    }

    @Override
    int generateWalls(MinecraftConfig mConfig, int len, int wid) {
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
        int wallHeight = 5;
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
        return wallHeight;
    }

    @Override
    boolean generateCeiling(MinecraftConfig mConfig, int len, int wid, int wallHeight) {
        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;
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
        return true;
    }
}