package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ExternalWallGenerator {

  String configFile;
  private final int wallHeight = 5;

  public ExternalWallGenerator(String configFile) {
    this.configFile = configFile;
  }

  int generate(MinecraftConfig mConfig, int[][] layout) {
    int len = layout.length;
    if (len == 0) {
      throw new IllegalStateException();
    }
    JSONObject obj = new JSONObject(configFile);
    String wallBlockType = obj.getString("wall_block_type");
    int wid = layout[0].length;
    StructureWorldAccess world = mConfig.world;
    BlockPos groundLevel = mConfig.pos;
    for (int i = 0; i < len; i++) {
      for (int k = 0; k < wallHeight; k++) {
        if (wallBlockType.equals("cobblestone")) {
          world.setBlockState(groundLevel.east(i).up(k),
              Blocks.COBBLESTONE.getDefaultState(), 3);
          world.setBlockState(groundLevel.east(i).north(wid - 1).up(k),
              Blocks.COBBLESTONE.getDefaultState(), 3);
        } else if (wallBlockType.equals("whiteConcreate")) {
          world.setBlockState(groundLevel.east(i).up(k),
              Blocks.WHITE_CONCRETE.getDefaultState(), 3);
          world.setBlockState(groundLevel.east(i).north(wid - 1).up(k),
              Blocks.WHITE_CONCRETE.getDefaultState(), 3);
        } else {
          throw new IllegalStateException("Illegal type of blocks for walls");
        }
      }
    }
    for (int i = 0; i < wid; i++) {
      for (int k = 0; k < wallHeight; k++) {
        if (wallBlockType.equals("cobblestone")) {
          world.setBlockState(groundLevel.north(i).up(k), Blocks.COBBLESTONE.getDefaultState(), 3);
          world.setBlockState(groundLevel.north(i).east(len - 1).up(k),
              Blocks.COBBLESTONE.getDefaultState(), 3);
        } else if (wallBlockType.equals("whiteConcreate")) {
          world.setBlockState(groundLevel.north(i).up(k), Blocks.WHITE_CONCRETE.getDefaultState(),
              3);
          world.setBlockState(groundLevel.north(i).east(len - 1).up(k),
              Blocks.WHITE_CONCRETE.getDefaultState(), 3);
        } else {
          throw new IllegalStateException("Illegal type of blocks for walls");
        }
      }
    }
    return wallHeight;
  }
}
