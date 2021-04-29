package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.json.JSONObject;

public class CeilingGenerator {

  String configFile;

  public CeilingGenerator(String configFile) {
    this.configFile = configFile;
  }

  boolean generate(MinecraftConfig mConfig, int[][] layout, int wallHeight) {
    int len = layout.length;
    if (len == 0) {
      throw new IllegalStateException();
    }
    JSONObject obj = new JSONObject(configFile);
    String ceilingBlockType = obj.getString("ceiling_block_type");
    int wid = layout[0].length;
    StructureWorldAccess world = mConfig.world;
    BlockPos groundLevel = mConfig.pos;
    for (int i = 0; i < len; i++) {
      for (int j = 0; j < wid; j++) {
        if (ceilingBlockType.equals("cobblestone")) {
          world.setBlockState(groundLevel.east(i).north(j).up(wallHeight),
              Blocks.COBBLESTONE.getDefaultState(), 3);
        } else if (ceilingBlockType.equals("whiteConcreate")) {
          world.setBlockState(groundLevel.east(i).north(j).up(wallHeight),
              Blocks.WHITE_CONCRETE.getDefaultState(), 3);
        } else {
          throw new IllegalStateException("Illegal type of blocks for ceiling");
        }
      }
    }
    return true;
  }
}
