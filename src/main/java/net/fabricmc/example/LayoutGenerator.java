package net.fabricmc.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import org.json.JSONObject;

public class LayoutGenerator {

  String configFile;

  public LayoutGenerator(String configFile) {
    this.configFile = configFile;
  }

  int[][] generate(MinecraftConfig mConfig) {
    JSONObject obj = new JSONObject(configFile);
    String layoutType = obj.getString("layout_type");
    int len = 0;
    int wid = 0;
    if (layoutType.equals("rectangle")) {
      len = 20;
      wid = 10;
    } else if (layoutType.equals("square")) {
      len = 5;
      wid = 5;
    } else {
      throw new IllegalStateException("Illegal type of layout");
    }
    StructureWorldAccess world = mConfig.world;
    BlockPos groundLevel = mConfig.pos;
    for (int i = 0; i < len; i++) {
      for (int j = 0; j < wid; j++) {
        world.setBlockState(groundLevel.east(i).north(j), Blocks.COBBLESTONE.getDefaultState(), 3);
      }
    }
    return new int[len][wid];
  }
}
