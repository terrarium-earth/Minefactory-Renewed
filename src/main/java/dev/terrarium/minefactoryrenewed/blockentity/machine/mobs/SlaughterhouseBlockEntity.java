package dev.terrarium.minefactoryrenewed.blockentity.machine.mobs;

import dev.terrarium.minefactoryrenewed.blockentity.container.machine.mobs.SlaughterhouseContainer;
import dev.terrarium.minefactoryrenewed.blockentity.machine.MachineBlockEntity;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import dev.terrarium.minefactoryrenewed.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SlaughterhouseBlockEntity extends MachineBlockEntity implements MenuProvider {

    private static final int SLIME_AMOUNT = 25;
    private static final int MEAT_AMOUNT = 50;

    private final FluidTank pinkSlimeTank = new FluidTank(8000, new FluidStack(ModBlocks.PINK_SLIME.get(), 1000)::isFluidEqual);
    private final LazyOptional<FluidTank> slimeTankHandler = LazyOptional.of(() -> pinkSlimeTank);

    public SlaughterhouseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLAUGHTERHOUSE.get(), pos, state);
        this.createEnergy(16000, 1000);
        this.createFluid(10000, new FluidStack(ModBlocks.MEAT.get(), 1000));
        this.setMaxWorkTime(4);
        this.setMaxIdleTime(80);

        this.createMachineArea(pos, Direction.NORTH);
        this.getMachineArea().setVerticalRange(3);
        this.getMachineArea().setUpgradeRadius(1);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("pinkSlimeTank", pinkSlimeTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        pinkSlimeTank.readFromNBT(tag.getCompound("pinkSlimeTank"));
    }

    @Override
    public boolean run() {
        if (level == null || pinkSlimeTank.getSpace() < SLIME_AMOUNT) return false;
        List<Entity> entities = level.getEntities((Entity) null, getMachineArea().getAabb(), ENTITY_PREDICATE);
        if (!entities.isEmpty()) {
            LivingEntity entity = (LivingEntity) entities.get(0);
            entity.hurt(NO_DROPS, 100);
            if (level.getRandom().nextFloat() < 0.15) {
                pinkSlimeTank.fill(new FluidStack(ModBlocks.PINK_SLIME.get(), SLIME_AMOUNT), IFluidHandler.FluidAction.EXECUTE);
            }

            getTank().fill(new FluidStack(ModBlocks.MEAT.get(), MEAT_AMOUNT), IFluidHandler.FluidAction.EXECUTE);
            useEnergy();
            return true;
        }

        return false;
    }

    public FluidTank getPinkSlimeTank() {
        return pinkSlimeTank;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return getTank().getFluidAmount() > 0 ? super.getCapability(cap, side) : slimeTankHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SlaughterhouseContainer(id, inventory, this);
    }
}
