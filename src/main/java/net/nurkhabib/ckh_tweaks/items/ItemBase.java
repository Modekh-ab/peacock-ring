package net.nurkhabib.ckh_tweaks.items;

import net.minecraft.world.item.Item;
import net.nurkhabib.ckh_tweaks.utils.CKhTab;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().tab(CKhTab.CKH_TAB));
    }
}
