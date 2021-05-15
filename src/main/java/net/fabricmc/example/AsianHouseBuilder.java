package net.fabricmc.example;

public class AsianHouseBuilder extends HouseBuilder {
    AsianHouseBuilder() {
        this.layoutGenerator = new LayoutGenerator("asian");
        this.externalWallGenerator = new ExternalWallGenerator("asian");
        this.ceilingGenerator = new CeilingGenerator("asian");
    }
}