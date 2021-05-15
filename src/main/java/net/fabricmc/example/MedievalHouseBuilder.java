package net.fabricmc.example;

public class MedievalHouseBuilder extends HouseBuilder {
    MedievalHouseBuilder() {
        this.layoutGenerator = new LayoutGenerator("medieval");
        this.externalWallGenerator = new ExternalWallGenerator("medieval");
        this.ceilingGenerator = new CeilingGenerator("medieval");
    }
}