package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarGenBlockEntity extends GeneratorBlockEntity {

    public SolarGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOLAR_GENERATOR.get(), pos, state, 100000, 4, 20);
    }

    @Override
    protected void tick() {
        super.tick();
        if (level == null) return;
        if (level.isDay() && level.canSeeSky(getBlockPos().above()) && canGenerate()) {
            this.generateEnergy();
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.solar_generator");
    }

    @Override
    public Component getDisplayText() {
        return level != null && level.isDay() && level.canSeeSky(getBlockPos().above()) && canGenerate() ?
                new TranslatableComponent("tooltip." + MinefactoryRenewed.MODID + ".generator.generating", String.valueOf(getEnergyGen())) :
                new TranslatableComponent("tooltip." + MinefactoryRenewed.MODID + ".generator.idle");
    }

    @Override
    public boolean hasMenu() {
        return false;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return null;
    }
}
