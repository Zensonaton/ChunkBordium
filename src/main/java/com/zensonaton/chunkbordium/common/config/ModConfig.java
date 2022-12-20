package com.zensonaton.chunkbordium.common.config;

import com.zensonaton.chunkbordium.common.ChunkBordiumMod;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.Nullable;

public class ModConfig implements Config {
    @Override
    public String getName() {
        return ChunkBordiumMod.MOD_ID;
    }
    @Override
    public @Nullable String getModid() {
        return ChunkBordiumMod.MOD_ID;
    }

    @Comment(value = "This setting enables or disables the mod. If it is set to true, chunk generation will be limited to the values configured below.")
    public boolean IsEnabled = true;
    @Comment(value = "Determines the shape of the generator.")
    public BorderTypes BorderType = BorderTypes.SQUARE;
    public enum BorderTypes {SQUARE, SPHERE}
    @Comment(value = "This setting controls the direction of chunk generation. If it is enabled, new chunks will be generated inside the border. If it is disabled, new chunks will be generated outside of the border. This setting is purely for fun.")
    public Boolean GenerateInside = true;
    @Comment(value = "This setting determines whether the World Border will limit chunk generation. If it is enabled, the CenterX, CenterZ, and ChunkRadius settings will not be used.")
    public boolean UseWorldBorderAsChunkBorder = true;
    @Comment(value = "This setting specifies the X coordinate of the center point. You can view the chunk position in the debug (F3) menu.")
    public Integer CenterX = 0;
    @Comment(value = "This setting specifies the Z coordinate of the center point. You can view the chunk position in the debug (F3) menu.")
    public Integer CenterZ = 0;
    @Comment(value = "This setting determines the radius of the generator, specified in chunks. To convert a block radius to a chunk radius, divide the block radius by 16.")
    public Integer ChunkRadius = 30;
}
