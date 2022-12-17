package com.zensonaton.chunkbordium.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.zensonaton.chunkbordium.access.ChunkGeneratorAccess;
import com.zensonaton.chunkbordium.access.ConfiguredCarverAccess;
import com.zensonaton.chunkbordium.utils.GenerationUtils;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.random.ChunkRandom;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Random;

@Mixin(NoiseChunkGenerator.class)
@Debug(export = true)
public class NoiseChunkGeneratorMixin {
    private ServerWorld world;

    @Inject(method = "carve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/random/ChunkRandom;setCarverSeed(JII)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void carveMixin(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep, CallbackInfo ci, BiomeAccess biomeAccess2, ChunkRandom chunkRandom, int i, ChunkPos chunkPos, ChunkNoiseSampler chunkNoiseSampler, AquiferSampler aquiferSampler, CarverContext carverContext, CarvingMask carvingMask, int j, int k, ChunkPos chunkPos2, Chunk chunk2, GenerationSettings generationSettings, Iterable iterable, int l, Iterator var23, RegistryEntry registryEntry, ConfiguredCarver configuredCarver) {
        this.world = chunkRegion.toServerWorld();
        ((ConfiguredCarverAccess) (Object) configuredCarver).setWorld(chunkRegion.toServerWorld());
    }

    @WrapOperation(method = "carve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/carver/ConfiguredCarver;shouldCarve(Ljava/util/Random;)Z"))
    private boolean carveConditionMixin(ConfiguredCarver<?> carver, Random random, Operation<Boolean> original, ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {
        return (GenerationUtils.shouldChunkBeGenerated(this.world, chunk.getPos()) ? original.call(carver, random) : false);
    }

//    @WrapWithCondition(method = "carve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/carver/ConfiguredCarver;shouldCarve(Ljava/util/Random;)Z"))
//    private boolean carveConditionMixin(ConfiguredCarver<?> instance, CarverContext context, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome, Random random, AquiferSampler aquiferSampler, ChunkPos pos, CarvingMask mask) {
//        return (GenerationUtils.shouldChunkBeGenerated(this.world, pos));
//    }

    @Inject(method = "buildSurface", at = @At("HEAD"), cancellable = true)
    private void buildSurfaceMixin(ChunkRegion region, StructureAccessor structures, Chunk chunk, CallbackInfo ci) {
        this.world = region.toServerWorld();

        if (!GenerationUtils.shouldChunkBeGenerated(region.toServerWorld(), chunk.getPos())) ci.cancel();
    }

    @WrapOperation(method = "populateNoise(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/chunk/Chunk;II)Lnet/minecraft/world/chunk/Chunk;", at = @At(value = "INVOKE", target = "Lnet/minecraft/SharedConstants;method_37896(Lnet/minecraft/util/math/ChunkPos;)Z"))
    private boolean isOutsideGenerationAreaMixin(ChunkPos chunkPos, Operation<Boolean> original, Blender blender, StructureAccessor structureAccessor, Chunk chunk, int i, int j) {
        return (GenerationUtils.shouldChunkBeGenerated(
                ((ChunkGeneratorAccess) (Object) this).getWorld(),
                chunkPos
        ) ? original.call(chunkPos) : true);
    }
}