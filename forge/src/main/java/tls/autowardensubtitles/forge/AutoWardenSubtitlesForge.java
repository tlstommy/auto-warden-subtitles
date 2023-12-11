package tls.autowardensubtitles.forge;

import dev.architectury.platform.forge.EventBuses;
import tls.autowardensubtitles.AutoWardenSubtitles;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


import net.minecraft.client.Minecraft;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.client.Options;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


import com.mojang.logging.LogUtils;
import org.slf4j.Logger;


@Mod(AutoWardenSubtitles.MOD_ID)
public class AutoWardenSubtitlesForge {
    private static final Logger LOGGER = LogUtils.getLogger();

    public AutoWardenSubtitlesForge() {
        EventBuses.registerModEventBus(AutoWardenSubtitles.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AutoWardenSubtitles.init();

    }
    @Mod.EventBusSubscriber(modid = AutoWardenSubtitles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientModEvents {

        private static boolean subtitlesEnabled = false;
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
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