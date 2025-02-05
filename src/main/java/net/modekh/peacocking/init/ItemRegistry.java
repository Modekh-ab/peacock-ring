package net.modekh.peacocking.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.modekh.peacocking.items.relics.ring.PeacockRingItem;
import net.modekh.peacocking.utils.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> PEACOCK_RING = ITEMS.register("peacock_ring", PeacockRingItem::new);
//    public static final RegistryObject<Item> NEW_GEN_BOOK = ITEMS.register("new_gen_book", NewGenBookItem::new);

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
