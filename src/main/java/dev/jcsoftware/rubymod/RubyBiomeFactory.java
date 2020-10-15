package dev.jcsoftware.rubymod;

import dev.jcsoftware.rubymod.mixin.BuiltInBiomesAccessor;
import dev.jcsoftware.rubymod.mixin.SetBaseBiomesLayerAccessor;
import dev.jcsoftware.rubymod.mixin.VanillaLayeredBiomeSourceAccessor;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class RubyBiomeFactory {

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> RUBY_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(
                    RubyMod.RUBY_BLOCK.getDefaultState(),
                    Blocks.STONE.getDefaultState(),
                    Blocks.OBSIDIAN.getDefaultState()));

    public static final RegistryKey<Biome> RUBY_BIOME_KEY =
            RegistryKey.of(Registry.BIOME_KEY, new Identifier(RubyMod.MOD_NAMESPACE, "ruby_biome"));

    public static final Biome RUBY_BIOME = makeRubyBiome();

    public static void register() {
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(RubyMod.MOD_NAMESPACE, "ruby"), RUBY_SURFACE_BUILDER);
        Registry.register(BuiltinRegistries.BIOME, RUBY_BIOME_KEY.getValue(), RUBY_BIOME);
        BuiltInBiomesAccessor.getRawIdMap().put(BuiltinRegistries.BIOME.getRawId(RUBY_BIOME), RUBY_BIOME_KEY);

        List<RegistryKey<Biome>> biomes = new ArrayList<>(VanillaLayeredBiomeSourceAccessor.getBiomes());
        biomes.add(RUBY_BIOME_KEY);
        VanillaLayeredBiomeSourceAccessor.setBiomes(biomes);

        SetBaseBiomesLayerAccessor.setTemperateBiomes(
                ArrayUtils.add(SetBaseBiomesLayerAccessor.getTemperateBiomes(), BuiltinRegistries.BIOME.getRawId(RUBY_BIOME)));
    }

    private static Biome makeRubyBiome() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 95, 5, 100);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(RUBY_SURFACE_BUILDER);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAncientDebris(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.NONE)
                .depth(0.025F)
                .scale(0.25F)
                .temperature(0.8F)
                .downfall(0.4F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0xfc2808)
                        .waterFogColor(0xf26c57)
                        .fogColor(0x781607)
                        .skyColor(0x781607)
                        .moodSound(BiomeMoodSound.CAVE)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

}
