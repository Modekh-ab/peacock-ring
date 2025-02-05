package net.modekh.peacocking.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.modekh.peacocking.init.ItemRegistry;
import org.jetbrains.annotations.NotNull;

public class PeacockingTab extends CreativeModeTab {
    public static final CreativeModeTab CKH_TAB = new PeacockingTab("peacocking_tab");

    public PeacockingTab(String label) {
        super(label);
    }
    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ItemRegistry.PEACOCK_RING.get());
    }
}
