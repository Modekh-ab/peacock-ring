package net.modekh.peacocking.items;

import net.minecraft.world.item.Item;
import net.modekh.peacocking.utils.PeacockingTab;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Properties().tab(PeacockingTab.CKH_TAB));
    }
}
