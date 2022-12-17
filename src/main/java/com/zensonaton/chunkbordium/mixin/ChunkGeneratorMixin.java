package com.zensonaton.chunkbordium.mixin;

import com.zensonaton.chunkbordium.access.ChunkGeneratorAccess;
import com.zensonaton.chunkbordium.utils.GenerationUtils;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin implements ChunkGeneratorAccess {
    ServerWorld world;
    @Override
    public void setWorld(ServerWorld world) {
        this.world = world;
    }
    @Override
    public ServerWorld getWorld() {
        return this.world;
    }

    @Inject(method = "generateFeatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;method_37896(Lnet/minecraft/util/math/ChunkPos;)Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci, ChunkPos chunkPos) {
        setWorld(world.toServerWorld());

        if (!GenerationUtils.shouldChunkBeGenerated(world.toServerWorld(), chunkPos)) ci.cancel();
    }
}
