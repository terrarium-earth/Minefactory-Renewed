package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.item.MachineUpgradeItem;
import dev.onyxstudios.minefactoryrenewed.item.SafariNetItem;
import dev.onyxstudios.minefactoryrenewed.item.SafariNetLauncherItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinefactoryRenewed.MODID);
    public static final Item.Properties PROPERTIES = new Item.Properties().tab(MinefactoryRenewed.TAB);

    //Upgrades
    public static final RegistryObject<Item> IRON_UPGRADE = ITEMS.register("iron_upgrade", () -> new MachineUpgradeItem(1));
    public static final RegistryObject<Item> COPPER_UPGRADE = ITEMS.register("copper_upgrade", () -> new MachineUpgradeItem(2));
    public static final RegistryObject<Item> GOLD_UPGRADE = ITEMS.register("gold_upgrade", () -> new MachineUpgradeItem(3));
    public static final RegistryObject<Item> OBSIDIAN_UPGRADE = ITEMS.register("obsidian_upgrade", () -> new MachineUpgradeItem(5));
    public static final RegistryObject<Item> QUARTZ_UPGRADE = ITEMS.register("quartz_upgrade", () -> new MachineUpgradeItem(4));
    public static final RegistryObject<Item> DIAMOND_UPGRADE = ITEMS.register("diamond_upgrade", () -> new MachineUpgradeItem(6));
    public static final RegistryObject<Item> EMERALD_UPGRADE = ITEMS.register("emerald_upgrade", () -> new MachineUpgradeItem(7));
    public static final RegistryObject<Item> NETHERITE_UPGRADE = ITEMS.register("netherite_upgrade", () -> new MachineUpgradeItem(8));

    //Rubber Items
    public static final RegistryObject<Item> RAW_RUBBER = ITEMS.register("raw_rubber", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> RAW_PLASTIC = ITEMS.register("raw_plastic", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> PLASTIC_SHEETS = ITEMS.register("plastic_sheets", () -> new Item(PROPERTIES));

    public static final RegistryObject<Item> SAFARI_NET = ITEMS.register("safari_net", () -> new SafariNetItem(false));
    public static final RegistryObject<Item> SAFARI_NET_SINGLE = ITEMS.register("safari_net_single", () -> new SafariNetItem(true));
    public static final RegistryObject<Item> SAFARI_NET_LAUNCHER = ITEMS.register("safari_net_launcher", SafariNetLauncherItem::new);
}
