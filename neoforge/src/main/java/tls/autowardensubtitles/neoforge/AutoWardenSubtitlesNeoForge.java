package tls.autowardensubtitles.neoforge;


//import dev.architectury.event.EventHandler;
import net.neoforged.neoforge.common.NeoForge;
import tls.autowardensubtitles.AutoWardenSubtitles;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;


import net.minecraft.client.Minecraft;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.neoforged.api.distmarker.Dist;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;




import com.mojang.logging.LogUtils;
import org.slf4j.Logger;


@Mod(AutoWardenSubtitles.MOD_ID)
public class AutoWardenSubtitlesNeoForge {
    private static final Logger LOGGER = LogUtils.getLogger();

    public AutoWardenSubtitlesNeoForge() {


        AutoWardenSubtitles.init();

    }
    //@Mod.EventBusSubscriber(modid = AutoWardenSubtitles.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        private static boolean subtitlesEnabled = false;
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Pre event) {
            //mc instance
            Minecraft mc = Minecraft.getInstance();

            if(mc.player != null){
                Options gameOptions = mc.options;
                ResourceKey<Biome> targetBiome = Biomes.DEEP_DARK;
                boolean inTargetBiome = mc.player.clientLevel.getBiome(BlockPos.containing(mc.player.position())).is(targetBiome);

                if(inTargetBiome && !subtitlesEnabled) {
                    subtitlesEnabled = true;
                    gameOptions.showSubtitles().set(subtitlesEnabled);
                }

                if(!inTargetBiome && subtitlesEnabled) {
                    subtitlesEnabled = false;
                    gameOptions.showSubtitles().set(subtitlesEnabled);
                }

            }
        }
    }

}