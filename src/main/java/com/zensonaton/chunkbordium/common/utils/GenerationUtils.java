package com.zensonaton.chunkbordium.common.utils;

import com.zensonaton.chunkbordium.common.ChunkBordiumMod;
import com.zensonaton.chunkbordium.common.config.ModConfig;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.border.WorldBorder;

import java.util.Objects;

public class GenerationUtils {
    /**
     * Returns true, if chunk is inside/outside the world border, and it should be generated, as specified in mod's config.
     * @param world
     * @param pos
     * @return true, if chunk should be generated.
     */
    public static boolean shouldChunkBeGenerated(ServerWorld world, ChunkPos pos) {
        int xBlockPos = pos.getStartX() + 1;
        int zBlockPos = pos.getStartZ() + 1;

        int xCenterBlockPos = ChunkBordiumMod.CONFIG.CenterX * 16;      // ChunkPos -> BlockPos.
        int zCenterBlockPos = ChunkBordiumMod.CONFIG.CenterZ * 16;      // ChunkPos -> BlockPos.
        int borderSize      = ChunkBordiumMod.CONFIG.ChunkRadius * 16;  // ChunkPos -> BlockPos.

        boolean generateInside = ChunkBordiumMod.CONFIG.GenerateInside;

        // If mod is disabled, then do nothing here.
        if (!ChunkBordiumMod.CONFIG.IsEnabled) return true;

        // Use world border positions and radius if config allows this.
        if (ChunkBordiumMod.CONFIG.UseWorldBorderAsChunkBorder && !Objects.isNull(world)) {
            WorldBorder border = world.getWorldBorder();

            xCenterBlockPos = (int) border.getCenterX();
            zCenterBlockPos = (int) border.getCenterZ();
            borderSize      = (int) border.getSize() / 2;
        }

        if (ChunkBordiumMod.CONFIG.BorderType == ModConfig.BorderTypes.SQUARE) {
            // Square border.

            if (generateInside) {
                // Chunks *inside* border should be generated.

                return Math.abs(xBlockPos - xCenterBlockPos) <= borderSize && Math.abs(zBlockPos - zCenterBlockPos) <= borderSize;
            } else {
                // Chunks *outside* border should be generated.

                return Math.abs(xBlockPos - xCenterBlockPos) >= borderSize || Math.abs(zBlockPos - zCenterBlockPos) >= borderSize;
            }
        } else {
            // Sphere border

            double distance = Math.sqrt((xBlockPos - xCenterBlockPos) * (xBlockPos - xCenterBlockPos) + (zBlockPos - zCenterBlockPos) * (zBlockPos - zCenterBlockPos));

            if (generateInside) {
                // Chunks *inside* border should be generated.

                return distance <= borderSize;
            } else {
                // Chunks *outside* border should be generated.

                return distance >= borderSize;
            }
        }
    }

}
