package com.example.railgunfishingrod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class RailgunFishingRodMod implements ModInitializer {
    public static final String MOD_ID = "railgun_fishing_rod";

    private static final Identifier RAILGUN_ROD_ID =
            Identifier.fromNamespaceAndPath(MOD_ID, "railgun_fishing_rod");
    private static final ResourceKey<Item> RAILGUN_ROD_KEY =
            ResourceKey.create(Registries.ITEM, RAILGUN_ROD_ID);

    public static final Item RAILGUN_FISHING_ROD = registerItem();

    private static Item registerItem() {
        Item.Properties properties = new Item.Properties()
                .setId(RAILGUN_ROD_KEY)
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON);

        return Registry.register(
                BuiltInRegistries.ITEM,
                RAILGUN_ROD_KEY,
                new RailgunFishingRodItem(properties)
        );
    }

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries ->
                entries.accept(RAILGUN_FISHING_ROD)
        );
    }
}
