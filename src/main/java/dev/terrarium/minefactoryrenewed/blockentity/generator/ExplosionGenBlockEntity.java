package dev.terrarium.minefactoryrenewed.blockentity.generator;

import dev.terrarium.minefactoryrenewed.data.generator.GeneratorItemManager;
import dev.terrarium.minefactoryrenewed.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosionGenBlockEntity extends BurnableGenBlockEntity {

    private int cooldown;

    public ExplosionGenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXPLOSION_GENERATOR.get(), pos, state, 256000, 0, 100);
    }

    @Override
    public void burnItem(ItemStack stack) {
        setEnergyGen(GeneratorItemManager.getExplosive().getEnergyGen(stack));
        setBurnTime(GeneratorItemManager.getExplosive().getBurnTime(stack));
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GeneratorItemManager.getExplosive().isValid(stack);
    }

    @Override
    public void onBurn() {
        super.onBurn();
        if(level == null) return;
        cooldown++;
        if (cooldown >= 40) {
            this.level.explode(null, getBlockPos().getX(), getBlockPos().getY() + 0.0625f, getBlockPos().getZ(), 4.0F, Explosion.BlockInteraction.NONE);
            cooldown = 0;
        }
    }
}
