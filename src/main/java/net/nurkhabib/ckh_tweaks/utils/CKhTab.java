package net.nurkhabib.ckh_tweaks.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.nurkhabib.ckh_tweaks.init.ItemRegistry;

public class CKhTab {
    public static final CreativeModeTab CKH_TAB = new CreativeModeTab("ckh_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.JODAH_RING.get());
        }
    };
}
