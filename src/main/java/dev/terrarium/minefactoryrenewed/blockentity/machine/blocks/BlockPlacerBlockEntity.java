package dev.terrarium.minefactoryrenewed.blockentity.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.blocks.BlockPlacerContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockPlacerBlockEntity extends MachineBlockEntity implements MenuProvider {

    public BlockPlacerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOCK_PLACER.get(), pos, state);
        this.createInventory(1, false);
        this.createEnergy(16000, 20);
        this.setMaxWorkTime(2);
        this.setMaxIdleTime(20);

        this.createMachineArea(pos, Direction.NORTH, 0);
        this.getMachineArea().setOneBlock(true);
    }

    @Override
    public boolean run() {
        if (level == null) return false;
        ItemStack stack = getInventory().getStackInSlot(0);

        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            Direction direction = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
            BlockPos pos = getBlockPos().relative(direction);
            if (level.isEmptyBlock(pos)) {
                level.setBlockAndUpdate(pos, blockItem.getBlock().defaultBlockState());
                getInventory().extractItem(0, 1, false);
                useEnergy();
                return true;
            }
        }

        return false;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BlockPlacerContainer(id, inventory, this);
    }
}
