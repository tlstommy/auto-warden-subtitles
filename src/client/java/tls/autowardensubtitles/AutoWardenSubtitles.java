//TLS
package tls.autowardensubtitles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.client.option.GameOptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoWardenSubtitles implements ClientModInitializer {

	//private static final Logger LOGGER = LogManager.getLogger();
	private boolean subtitlesEnabled = false;

	@Override
	public void onInitializeClient() {

		//set the target biome to deep dark
		RegistryKey<Biome> targetBiome = BiomeKeys.DEEP_DARK;

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.world != null) {

				//get biome based off player block and then compare to the target biome
				Boolean inTargetBiome = client.world.getBiome(client.player.getBlockPos()).matchesKey(targetBiome);

				if(inTargetBiome && !subtitlesEnabled) {
					subtitlesEnabled = true;
					//LOGGER.info("Turn subtitles on");
					turnOnSubtitles();
				}

				if(!inTargetBiome && subtitlesEnabled) {
					subtitlesEnabled = false;
					turnOffSubtitles();
				}
			}
		});
	}

	private void turnOnSubtitles(){
		GameOptions gameOptions = MinecraftClient.getInstance().options;
		gameOptions.getShowSubtitles().setValue(true);
	}

	private void turnOffSubtitles(){
		GameOptions gameOptions = MinecraftClient.getInstance().options;
		gameOptions.getShowSubtitles().setValue(false);

	}
}
