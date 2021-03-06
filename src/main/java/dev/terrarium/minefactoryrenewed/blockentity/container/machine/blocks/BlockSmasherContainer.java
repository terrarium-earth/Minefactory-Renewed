package dev.terrarium.minefactoryrenewed.blockentity.container.machine.blocks;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.MachineContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.blocks.BlockSmasherBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class BlockSmasherContainer extends MachineContainer {

    public BlockSmasherContainer(int id, Inventory inventory, BlockSmasherBlockEntity blockEntity) {
        super(ModBlockEntities.BLOCK_SMASHER_CONTAINER.get(), id, inventory, blockEntity, false);
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 56, 35));
        this.addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 98, 35));
        addPlayerSlots(inventory, false);
    }
}
