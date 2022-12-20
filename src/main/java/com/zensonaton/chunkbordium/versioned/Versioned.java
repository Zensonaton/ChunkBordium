package com.zensonaton.chunkbordium.versioned;

import net.fabricmc.loader.api.FabricLoader;

public class Versioned {
    public static void load() {
        VersionedEntryPoint entryPoint = FabricLoader.getInstance()
                .getEntrypoints("chunkbordium-versioned", VersionedEntryPoint.class).get(0);
        if (entryPoint == null) {
            throw new RuntimeException("Could not find versioned entrypoint, are you using an unsupported version of Minecraft?");
        }

        // Something?
    }
}