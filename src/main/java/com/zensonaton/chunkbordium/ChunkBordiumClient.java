package com.zensonaton.chunkbordium;

import draylar.omegaconfiggui.OmegaConfigGui;
import net.fabricmc.api.ClientModInitializer;

public class ChunkBordiumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OmegaConfigGui.registerConfigScreen(ChunkBordiumMod.CONFIG);
    }
}
