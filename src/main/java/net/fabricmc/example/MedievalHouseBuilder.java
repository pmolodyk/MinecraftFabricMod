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
}
