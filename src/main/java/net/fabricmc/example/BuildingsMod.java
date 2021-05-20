package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class BuildingsMod implements ModInitializer {

	private static final Feature<DefaultFeatureConfig> BUILDING_FEATURE = new BuildingFeature(
			DefaultFeatureConfig.CODEC);
	public static final ConfiguredFeature<?, ?> BUILDING_CONFIGURED = BUILDING_FEATURE
			.configure(FeatureConfig.DEFAULT)
			.decorate(Decorator.CHANCE.configure(new ChanceDecoratorConfig(80)));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry
				.register(Registry.FEATURE, new Identifier("buildings_mod", "buildings"), BUILDING_FEATURE);
		RegistryKey<ConfiguredFeature<?, ?>> buildingFeature = RegistryKey
				.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
						new Identifier("buildings_mod", "buildings"));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, buildingFeature.getValue(),
				BUILDING_CONFIGURED);
		BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.SURFACE_STRUCTURES,
				buildingFeature);
		System.out.println("Mod init");
	}
}