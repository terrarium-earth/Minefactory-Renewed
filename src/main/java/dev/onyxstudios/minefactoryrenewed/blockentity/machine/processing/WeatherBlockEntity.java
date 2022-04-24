package dev.onyxstudios.minefactoryrenewed.blockentity.machine.processing;

import dev.onyxstudios.minefactoryrenewed.blockentity.container.processing.WeatherContainer;
import dev.onyxstudios.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.onyxstudios.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeatherBlockEntity extends MachineBlockEntity implements MenuProvider {

    public WeatherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WEATHER.get(), pos, state);
        this.createEnergy(16000, 300);
        this.createFluid(2000, new FluidStack(Fluids.WATER, 1000));
        this.setMaxIdleTime(10);
        this.setMaxWorkTime(80);
    }

    @Override
    public boolean run() {
        if (level == null || !level.isRaining()) return false;
        Biome biome = level.getBiome(getBlockPos()).value();
        if (biome.getPrecipitation() == Biome.Precipitation.SNOW) {
            insertOrDropItems(List.of(new ItemStack(Items.SNOWBALL)));
        } else {
            getTank().fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
        }

        useEnergy();
        return true;
    }

    @Override
    protected void tick() {
        super.tick();
        transferFluid(100, getTank());
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.minefactoryrenewed.weather");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new WeatherContainer(id, inventory, this);
    }
}
