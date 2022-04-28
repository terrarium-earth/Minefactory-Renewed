package dev.onyxstudios.minefactoryrenewed.mixin.common;

import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.SpawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BaseSpawner.class)
public interface BaseSpawnerAccessor {

    @Accessor("nextSpawnData")
    SpawnData getSpawnData();
}
