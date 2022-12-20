package com.zensonaton.chunkbordium.common;

import com.zensonaton.chunkbordium.common.config.ModConfig;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChunkBordiumMod implements ModInitializer {
    public static final String MOD_ID = "chunkbordium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ModConfig CONFIG = OmegaConfig.register(ModConfig.class);

    @Override
    public void onInitialize() {
    }
}
