package net.fabricmc.example;

public class ModernHouseBuilder extends HouseBuilder{
    ModernHouseBuilder() {
        this.layoutGenerator = new LayoutGenerator("modern");
        this.externalWallGenerator = new ExternalWallGenerator("modern");
        this.ceilingGenerator = new CeilingGenerator("modern");
    }
}