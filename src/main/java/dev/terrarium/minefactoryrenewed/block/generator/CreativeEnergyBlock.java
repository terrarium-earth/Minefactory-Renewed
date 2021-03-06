package dev.terrarium.minefactoryrenewed.block.generator;

import dev.terrarium.minefactoryrenewed.block.machine.MachineBlock;
import dev.terrarium.minefactoryrenewed.blockentity.generator.GeneratorBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.blockentity.generator.CreativeEnergyBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergyBlock extends GeneratorBlock {

    public CreativeEnergyBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(-1));
    }

    @Override
    public BlockEntityType<? extends GeneratorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.CREATIVE_ENERGY.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CreativeEnergyBlockEntity(pos, state);
    }
}
