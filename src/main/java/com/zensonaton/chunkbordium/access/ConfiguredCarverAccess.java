package com.zensonaton.chunkbordium.access;

import net.minecraft.server.world.ServerWorld;

public interface ConfiguredCarverAccess {
    void setWorld(ServerWorld world);

    ServerWorld getWorld();
}
