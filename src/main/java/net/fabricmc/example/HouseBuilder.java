package net.fabricmc.example;

public abstract class HouseBuilder {
    LayoutGenerator layoutGenerator;
    ExternalWallGenerator externalWallGenerator;
    CeilingGenerator ceilingGenerator;
    String configFile;

    boolean build(MinecraftConfig minecraftConfig) {
        return true;
    }
}
