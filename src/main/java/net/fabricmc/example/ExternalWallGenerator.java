package net.fabricmc.example;
//package org.bukkit;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Random;


public class ExternalWallGenerator {

    String type;
    private final int wallHeight = 5;

    public ExternalWallGenerator(String type) {
        this.type = type;
    }

    int generate(MinecraftConfig mConfig, int[][] layout) {

        StructureWorldAccess world = mConfig.world;
        BlockPos groundLevel = mConfig.pos;

        if (type == "medieval") {
            // clay for the front and the back wall
            for (int i = 0; i < 2; ++i) {
                for (int j = 1; j < 8; ++j) {
                    for (int k = 1; k < 7; ++k) {
                        world.setBlockState(groundLevel.north(j).east(i * 18).up(k), Blocks.WHITE_TERRACOTTA.getDefaultState(), 3);
                    }
                }
            }
            // clay for the side walls
            for (int i = 1; i < 18; ++i) {
                for (int j = 0; j < 2; ++j) {
                    for (int k = 1; k < 4; ++k) {
                        world.setBlockState(groundLevel.east(i).north(j * 8).up(k), Blocks.WHITE_TERRACOTTA.getDefaultState(), 3);
                    }
                }
            }
            // vertical oak logs
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 2; ++j) {
                    for (int k = 1; k < 5; ++k) {
                        world.setBlockState(groundLevel.east(i * 6).north(j * 8).up(k), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                    }
                }
            }
            // west-east oak logs
            for (int i = 1; i < 18; ++i) {
                for (int j = 0; j < 2; ++j) {
                    world.setBlockState(groundLevel.east(i).north(j * 8).up(4), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                }
            }
            // south-north oak logs
            for (int i = 0; i < 2; ++i) {
                for (int j = 1; j < 8; ++j) {
                    world.setBlockState(groundLevel.east(i * 18).north(j).up(4), Blocks.STRIPPED_OAK_WOOD.getDefaultState(), 3);
                }
            }
            // side windows, south
            for (int i = 0; i < 3; ++i) {
                world.setBlockState(groundLevel.east(3 + i * 6).up(2), Blocks.OAK_TRAPDOOR.getDefaultState(), 3);
            }
            // side windows, north
            for (int i = 0; i < 3; ++i) {
                world.setBlockState(groundLevel.east(3 + i * 6).north(8).up(2), Blocks.OAK_TRAPDOOR.rotate(Blocks.OAK_TRAPDOOR.getDefaultState(), BlockRotation.CLOCKWISE_180), 3);
            }
            // side windows, west
            for (int j = 0; j < 2; ++j) {
                world.setBlockState(groundLevel.north(2 + j * 4).up(2), Blocks.OAK_TRAPDOOR.rotate(Blocks.OAK_TRAPDOOR.getDefaultState(), BlockRotation.CLOCKWISE_90), 3);
            }
            // door
            world.setBlockState(groundLevel.north(4).up(1), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(groundLevel.north(4).up(2), Blocks.AIR.getDefaultState(), 3);
            world.setBlockState(groundLevel.east(1).north(4).up(1), Blocks.OAK_DOOR.rotate(Blocks.OAK_DOOR.getDefaultState(), BlockRotation.CLOCKWISE_90), 3);
        } else if (type == "modern") {
            ArrayList<Block> blockList = new ArrayList<>();
            blockList.add(Blocks.WHITE_CONCRETE);
            blockList.add(Blocks.YELLOW_CONCRETE);

            Random rand = new Random();

//            Material.
        } else {
            throw new IllegalArgumentException("Illegal walls type");
        }
        return wallHeight;
    }
}