package dev.terrarium.minefactoryrenewed.block.machine.mobs;

import dev.terrarium.minefactoryrenewed.block.machine.RotatableMachineBlock;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.mobs.SlaughterhouseBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

public class SlaughterhouseBlock extends RotatableMachineBlock {

    public SlaughterhouseBlock() {
        super();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        LazyOptional<IFluidHandlerItem> handler = FluidUtil.getFluidHandler(stack);
        if (handler.isPresent() && level.getBlockEntity(pos) instanceof SlaughterhouseBlockEntity slaughterhouse) {
            if (FluidUtil.interactWithFluidHandler(player, hand, slaughterhouse.getTank()) ||
                    FluidUtil.interactWithFluidHandler(player, hand, slaughterhouse.getPinkSlimeTank())) {
                level.sendBlockUpdated(pos, state, level.getBlockState(pos), Block.UPDATE_ALL);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public BlockEntityType<? extends MachineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SLAUGHTERHOUSE.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SlaughterhouseBlockEntity(pos, state);
    }
}
