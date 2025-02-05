/*
package net.modekh.peacocking.items.relics;

import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NewGenBookItem extends RelicItem {
    @Override
    public @Nullable RelicData getRelicData() {
        return RelicData.builder()
                .abilityData(RelicAbilityData.builder()
                        .ability("domestication", RelicAbilityEntry.builder()
                                .build())
                        .ability("breeding", RelicAbilityEntry.builder()
                                .stat("radius", RelicAbilityStat.builder()
                                        .initialValue(2D, 10D)
                                        .upgradeModifier(RelicAbilityStat.Operation.MULTIPLY_BASE, 0.25D)
                                        .formatValue(value -> MathUtils.round(value, 1))
                                        .build())
                                .stat("delay", RelicAbilityStat.builder()
                                        .initialValue(1D, 12D)
                                        .upgradeModifier(RelicAbilityStat.Operation.MULTIPLY_BASE, 0.25D)
                                        .formatValue(value -> MathUtils.round(value, 1))
                                        .build())
                                .build())
                        .build())
                .levelingData(new RelicLevelingData(100, 10, 100))
                .styleData(RelicStyleData.builder()
                        .borders("#eed551", "#dcbe1d")
                        .build())
                .build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slot, isSelected);
    }
}
*/