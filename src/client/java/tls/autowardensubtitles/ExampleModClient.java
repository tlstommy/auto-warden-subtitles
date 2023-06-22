package tls.autowardensubtitles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.client.option.GameOptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleModClient implements ClientModInitializer {

	private static final Logger LOGGER = LogManager.getLogger();

	private boolean subtitlesEnabled = false;

	@Override
	public void onInitializeClient() {


		//set the target biome to deep dark
		RegistryKey<Biome> targetBiome = BiomeKeys.DEEP_DARK;

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.world != null) {
				Biome biome = client.world.getBiome(client.player.getBlockPos()).value();
				Boolean inTargetBiome = client.world.getBiome(client.player.getBlockPos()).matchesKey(targetBiome);

				if(inTargetBiome && !subtitlesEnabled) {
					LOGGER.info("Turn subtitles on");
					subtitlesEnabled = true;
					client.player.sendMessage(Text.literal("Now entering The Deep Dark, turning on subtitles"));
					turnOnSubtitles();

				}

				if(!inTargetBiome && subtitlesEnabled) {
					LOGGER.info("Turn subtitles off");
					client.player.sendMessage(Text.literal("Leaving The Deep Dark, turning off subtitles"));
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
