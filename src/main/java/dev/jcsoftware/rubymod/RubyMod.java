package dev.jcsoftware.rubymod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class RubyMod implements ModInitializer {

	public static final String MOD_NAMESPACE = "rubymod";

	public static final Block RUBY_ORE = new Block(
			FabricBlockSettings.of(Material.METAL)
					.breakByHand(false)
					.sounds(BlockSoundGroup.METAL)
					.hardness(6.9f)
	);

	public static final Block RUBY_BLOCK = new Block(
			FabricBlockSettings.of(Material.METAL)
					.breakByHand(false)
					.lightLevel(15)
					.emissiveLighting((state, world, pos) -> true)
					.sounds(BlockSoundGroup.METAL)
					.hardness(6.9f)
	);

	public static final Item RUBY_ITEM = new Item(
			new FabricItemSettings()
				.maxCount(64)
				.group(ItemGroup.MATERIALS)
				.rarity(Rarity.EPIC)
	);

	public static ConfiguredFeature<?, ?> RUBY_ORE_OVERWORLD = Feature.ORE
			.configure(new OreFeatureConfig(
								OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
								RUBY_ORE.getDefaultState(),
								4
							))
					.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
							0, 0, 52
					)))
					.spreadHorizontally()
					.repeat(4);

	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier(MOD_NAMESPACE, "ruby_block"), RUBY_BLOCK);
		Registry.register(Registry.ITEM,
				new Identifier(MOD_NAMESPACE, "ruby_block"),
				new BlockItem(RUBY_BLOCK, new Item.Settings().group(ItemGroup.MISC))
		);

		Registry.register(Registry.BLOCK, new Identifier(MOD_NAMESPACE, "ruby_ore"), RUBY_ORE);
		Registry.register(Registry.ITEM,
				new Identifier(MOD_NAMESPACE, "ruby_ore"),
				new BlockItem(RUBY_ORE, new Item.Settings().group(ItemGroup.MATERIALS))
		);

		Registry.register(Registry.ITEM,
				new Identifier(MOD_NAMESPACE, "ruby"),
				RUBY_ITEM);

		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MOD_NAMESPACE, "ruby_ore_overworld"), RUBY_ORE_OVERWORLD);

		RubyBiomeFactory.register();
	}
}
