package com.zensonaton.chunkbordium.mixin;

import com.zensonaton.chunkbordium.access.ConfiguredCarverAccess;
import com.zensonaton.chunkbordium.utils.GenerationUtils;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Function;

@Mixin(ConfiguredCarver.class)
public class ConfiguredCarverMixin implements ConfiguredCarverAccess {
    ServerWorld serverWorld;

    @Override
    public void setWorld(ServerWorld world) {
        this.serverWorld = world;
    }

    @Override
    public ServerWorld getWorld() {
        return this.serverWorld;
    }

    @Inject(method = "carve", at = @At(value = "HEAD"), cancellable = true)
    private void carveMixin(CarverContext context, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome, Random random, AquiferSampler aquiferSampler, ChunkPos pos, CarvingMask mask, CallbackInfoReturnable<Boolean> cir) {
        if (!GenerationUtils.shouldChunkBeGenerated(getWorld(), pos)) cir.setReturnValue(false);
    }
}
