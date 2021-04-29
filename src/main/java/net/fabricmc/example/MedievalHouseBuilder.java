package net.fabricmc.example;

public class MedievalHouseBuilder extends HouseBuilder{
    MedievalHouseBuilder() {
        super();
    }
    MedievalHouseBuilder(LayoutGenerator layoutGenerator, ExternalWallGenerator externalWallGenerator,
                       CeilingGenerator ceilingGenerator, String configFile) {
        this.layoutGenerator = layoutGenerator;
        this.externalWallGenerator = externalWallGenerator;
        this.ceilingGenerator = ceilingGenerator;
        this.configFile = configFile;
    }

    MedievalHouseBuilder(String configFile) {
        this.layoutGenerator = new LayoutGenerator(configFile);
        this.externalWallGenerator = new ExternalWallGenerator(configFile);
        this.ceilingGenerator = new CeilingGenerator(configFile);
        this.configFile = configFile;
    }
}
