package dev.terrarium.minefactoryrenewed.blockentity.machine.farming;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.farming.PlanterContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.data.machine.PlantManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlanterBlockEntity extends MachineBlockEntity implements MenuProvider {

    private final ItemStackHandler filterInventory = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            PlanterBlockEntity.this.setChanged();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public PlanterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTER.get(), pos, state);
        this.createInventory(16);
        this.createEnergy(10000, 160);
        this.setMaxWorkTime(5);
        this.setMaxIdleTime(5);

        this.createMachineArea(pos, Direction.UP);
        getMachineArea().setOriginOffset(0, 2, 0);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("filterInventory", filterInventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("filterInventory"))
            filterInventory.deserializeNBT(tag.getCompound("filterInventory"));
    }

    @Override
    public boolean run() {
        BlockPos workPos = getMachineArea().getNextBlock();
        if (level == null || !level.isEmptyBlock(workPos))
            return false;

        int slot = getFilterSlot(workPos);
        ItemStack filterStack = filterInventory.getStackInSlot(slot);

        for (int i = 1; i < getInventory().getSlots(); i++) {
            ItemStack stack = getInventory().getStackInSlot(i);
            Item seeds = stack.getItem();

            if (!PlantManager.getInstance().isPlant(stack) ||
                    (!filterStack.isEmpty() && filterStack.getItem() != seeds) ||
                    !PlantManager.getInstance().canPlantSeeds(level, seeds, workPos)) continue;

            level.setBlock(workPos, ((BlockItem) seeds).getBlock().defaultBlockState(), Block.UPDATE_CLIENTS);
            getInventory().extractItem(i, 1, false);
            useEnergy();
            return true;
        }

        return false;
    }

    public int getFilterSlot(BlockPos pos) {
        int radius = getMachineArea().getRadius();
        int x = Math.round(1.49F * (pos.getX() - getBlockPos().getX()) / radius);
        int z = Math.round(1.49F * (pos.getZ() - getBlockPos().getZ()) / radius);
        return 4 + x + 3 * z;
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new PlanterContainer(id, inventory, this);
    }

    public ItemStackHandler getFilterInventory() {
        return filterInventory;
    }
}
