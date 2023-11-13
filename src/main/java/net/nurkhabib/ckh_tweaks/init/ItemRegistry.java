package net.nurkhabib.ckh_tweaks.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nurkhabib.ckh_tweaks.CKhTweaks;
import net.nurkhabib.ckh_tweaks.items.relics.ring.JodahRingItem;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CKhTweaks.MODID);

    public static final RegistryObject<Item> JODAH_RING = ITEMS.register("jodah_ring",
            JodahRingItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
