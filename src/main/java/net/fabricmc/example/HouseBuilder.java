package net.fabricmc.example;

import net.minecraft.util.Pair;

public abstract class HouseBuilder {
    LayoutGenerator layoutGenerator;
    ExternalWallGenerator externalWallGenerator;
    CeilingGenerator ceilingGenerator;

    HouseBuilder() {
        this.layoutGenerator = new LayoutGenerator("");
        this.externalWallGenerator = new ExternalWallGenerator("");
        this.ceilingGenerator = new CeilingGenerator("");
    }

    boolean build(MinecraftConfig minecraftConfig) {
        Pair<Integer, Integer> layout = layoutGenerator.generate(minecraftConfig);
        if (layout == null) {
            throw new NullPointerException();
        }
        int wallHeight = externalWallGenerator.generate(minecraftConfig, layout.getLeft(), layout.getRight());
        if (wallHeight == -1) {
            throw new IllegalStateException();
        }
        return ceilingGenerator.generate(minecraftConfig, layout.getLeft(), layout.getRight(), wallHeight);
    }
}