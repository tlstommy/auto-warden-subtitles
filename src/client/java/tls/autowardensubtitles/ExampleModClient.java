package tls.autowardensubtitles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleModClient implements ClientModInitializer {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitializeClient() {

		RegistryKey<Biome> targetBiome = BiomeKeys.RIVER;

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.world != null) {
				Biome biome = client.world.getBiome(client.player.getBlockPos()).value();
				Boolean inTargetBiome = client.world.getBiome(client.player.getBlockPos()).matchesKey(targetBiome);
				LOGGER.info("In biome [RIVER]: "+inTargetBiome);


			}
		});
	}
}
