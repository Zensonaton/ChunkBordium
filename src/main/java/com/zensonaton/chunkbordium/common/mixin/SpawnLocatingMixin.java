package com.zensonaton.chunkbordium.common.mixin;

import net.minecraft.server.network.SpawnLocating;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpawnLocating.class)
public class SpawnLocatingMixin {
//    @Inject(method = "findServerSpawnPoint", at = @At(value = "HEAD"), cancellable = true)
//    private static void findServerSpawnPointMixin(ServerWorld world, ChunkPos chunkPos, CallbackInfoReturnable<BlockPos> cir) {
//        if (!GenerationUtils.shouldChunkBeGenerated(world, chunkPos)) cir.setReturnValue(null);
//    }
}
