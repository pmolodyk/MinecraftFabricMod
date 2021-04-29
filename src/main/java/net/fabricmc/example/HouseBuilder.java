package net.fabricmc.example;

public abstract class HouseBuilder {
    LayoutGenerator layoutGenerator;
    ExternalWallGenerator externalWallGenerator;
    CeilingGenerator ceilingGenerator;
    String configFile;

    HouseBuilder() {
        this.layoutGenerator = new LayoutGenerator("{}");
        this.externalWallGenerator = new ExternalWallGenerator("{}");
        this.ceilingGenerator = new CeilingGenerator("{}");
        this.configFile = null;
    }

    HouseBuilder(String configFile) {
        this.layoutGenerator = new LayoutGenerator("{}");
        this.externalWallGenerator = new ExternalWallGenerator(configFile);
        this.ceilingGenerator = new CeilingGenerator(configFile);
        this.configFile = configFile;
    }
    boolean build(MinecraftConfig minecraftConfig) {
        int[][] layout = layoutGenerator.generate(minecraftConfig);
        if (layout == null) {
            throw new NullPointerException();
        }
        int wallHeight = externalWallGenerator.generate(minecraftConfig, layout);
        if (wallHeight == -1) {
            throw new IllegalStateException();
        }
        return ceilingGenerator.generate(minecraftConfig, layout, wallHeight);
    }
}
