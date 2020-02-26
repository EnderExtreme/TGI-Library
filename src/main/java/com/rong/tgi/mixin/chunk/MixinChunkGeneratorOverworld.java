package com.rong.tgi.mixin.chunk;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGeneratorOverworld.class)
public class MixinChunkGeneratorOverworld {

    @Shadow private IBlockState oceanBlock;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(CallbackInfo info) {
        this.oceanBlock = Blocks.LAVA.getDefaultState();
    }

}
