package tls.autowardensubtitles.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoWardenSubtitlesFabric implements ModInitializer {

    private static final Logger LOGGER = LogManager.getLogger();
    private boolean subtitlesEnabled = false;

    @Override
    public void onInitialize() {

        //set the target biome to deep dark
        ResourceKey<Biome> targetBiome = Biomes.DEEP_DARK;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                Options gameOptions = Minecraft.getInstance().options;
                //get biome based off player block and then compare to the target biome
                Boolean inTargetBiome = client.player.clientLevel.getBiome(BlockPos.containing(client.player.position())).is(targetBiome);

                if(inTargetBiome && !subtitlesEnabled) {
                    subtitlesEnabled = true;
                    //LOGGER.info("Turn subtitles on");
                    gameOptions.showSubtitles().set(true);
                }



                if(!inTargetBiome && subtitlesEnabled) {
                    subtitlesEnabled = false;
                    gameOptions.showSubtitles().set(subtitlesEnabled);
                }
            }
        });
    }
}
