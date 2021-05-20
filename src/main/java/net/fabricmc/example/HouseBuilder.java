package net.fabricmc.example;

import net.minecraft.util.Pair;
import net.minecraft.world.StructureWorldAccess;

public abstract class HouseBuilder {
    abstract Pair<Integer, Integer> generateLayout(MinecraftConfig mConfig);
    abstract int generateWalls(MinecraftConfig mConfig, int len, int wid);
    abstract boolean generateCeiling(MinecraftConfig mConfig, int len, int wid, int wallHeight);

    boolean build(MinecraftConfig minecraftConfig) {
        Pair<Integer, Integer> layout = generateLayout(minecraftConfig);
        if (layout == null) {
            throw new NullPointerException();
        }
        int wallHeight = generateWalls(minecraftConfig, layout.getLeft(), layout.getRight());
        if (wallHeight == -1) {
            throw new IllegalStateException();
        }
        return generateCeiling(minecraftConfig, layout.getLeft(), layout.getRight(), wallHeight);
    }
}