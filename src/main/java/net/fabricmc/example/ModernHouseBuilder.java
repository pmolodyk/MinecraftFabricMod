package net.fabricmc.example;

public class ModernHouseBuilder extends HouseBuilder{
    ModernHouseBuilder() {
        super();
    }

    ModernHouseBuilder(LayoutGenerator layoutGenerator, ExternalWallGenerator externalWallGenerator,
        CeilingGenerator ceilingGenerator, String configFile) {
        this.layoutGenerator = layoutGenerator;
        this.externalWallGenerator = externalWallGenerator;
        this.ceilingGenerator = ceilingGenerator;
        this.configFile = configFile;
    }

    ModernHouseBuilder(String configFile) {
        this.layoutGenerator = new LayoutGenerator(configFile);
        this.externalWallGenerator = new ExternalWallGenerator(configFile);
        this.ceilingGenerator = new CeilingGenerator(configFile);
        this.configFile = configFile;
    }
}