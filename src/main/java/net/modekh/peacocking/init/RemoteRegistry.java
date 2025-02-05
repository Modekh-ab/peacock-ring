package net.modekh.peacocking.init;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.modekh.peacocking.Peacocking;

@Mod.EventBusSubscriber(modid = Peacocking.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RemoteRegistry {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
//            ItemProperties.register(ItemRegistry.JODAH_RING.get(), new ResourceLocation(Peacocking.MODID, "charging"),
//                    (stack, world, entity, id) -> NBTUtils.getBoolean(stack, PeacockRingItem.TAG_CHARGING, false) ? 1 : 0);
        });
    }
}
