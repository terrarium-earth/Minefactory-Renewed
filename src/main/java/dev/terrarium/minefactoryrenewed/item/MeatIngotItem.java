package dev.terrarium.minefactoryrenewed.item;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class MeatIngotItem extends BaseItem {

    public MeatIngotItem(int hunger, float saturation) {
        super(new Item.Properties()
                .tab(MinefactoryRenewed.TAB)
                .food(new FoodProperties.Builder()
                        .nutrition(hunger)
                        .saturationMod(saturation)
                        .build()
                )
        );
    }
}
