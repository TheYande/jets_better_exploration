package dev.hugeblank.jbe.mixin.block;

import dev.hugeblank.jbe.MainInit;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractBlock.class, IceBlock.class})
public class AbstractBlockMixin {

    @Inject(at = @At("HEAD"), method = "randomTick")
    private void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getDimension().ultrawarm() && (state.isOf(Blocks.BLUE_ICE) || state.isOf(Blocks.PACKED_ICE) || state.isOf(Blocks.ICE))) {
            Block next = MainInit.melt(state.getBlock());
            world.setBlockState(pos, next.getDefaultState());
            if (state.isOf(Blocks.ICE)) {
                world.syncWorldEvent(null, 1501, pos, 0);
            }
        }
    }

}