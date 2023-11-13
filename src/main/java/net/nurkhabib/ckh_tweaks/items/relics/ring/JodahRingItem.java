package net.nurkhabib.ckh_tweaks.items.relics.ring;

import it.hurts.sskirillss.relics.client.tooltip.base.RelicStyleData;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.base.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.AbilityCastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.AbilityCastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.RelicAbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.RelicAbilityEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.RelicAbilityStat;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.RelicLevelingData;
import it.hurts.sskirillss.relics.items.relics.base.utils.AbilityUtils;
import it.hurts.sskirillss.relics.items.relics.base.utils.LevelingUtils;
import it.hurts.sskirillss.relics.utils.DurabilityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.NBTUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class JodahRingItem extends RelicItem {
    public static final String TAG_TIME = "time";

    @Override
    public @Nullable RelicData getRelicData() {
        return RelicData.builder()
                .abilityData(RelicAbilityData.builder()
                        .ability("flight", RelicAbilityEntry.builder()
                                .stat("duration", RelicAbilityStat.builder()
                                        .initialValue(5, 30)
                                        .upgradeModifier(RelicAbilityStat.Operation.MULTIPLY_BASE, 0.25D)
                                        .formatValue(value -> MathUtils.round(value, 1))
                                        .build())
                                .stat("delay", RelicAbilityStat.builder()
                                        .initialValue(1, 12)
                                        .upgradeModifier(RelicAbilityStat.Operation.MULTIPLY_BASE, 0.25D)
                                        .formatValue(value -> MathUtils.round(value, 1))
                                        .build())
                                .build())
                        .ability("inf_flight", RelicAbilityEntry.builder()
                                .requiredLevel(5)
                                .stat("effect_lvl", RelicAbilityStat.builder()
                                        .initialValue(0, 3)
                                        .upgradeModifier(RelicAbilityStat.Operation.ADD, 1)
                                        .formatValue(value -> MathUtils.round(value, 0))
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
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();

        if (livingEntity instanceof Player player) {
            if (player.isCreative() || player.isSpectator())
                return;

            stopFlying(player);
            NBTUtils.getInt(stack, TAG_TIME, 0);
        }
    }

    @Override
    public void curioTick(@NotNull SlotContext slotContext, @NotNull ItemStack stack) {
        Optional<SlotResult> optStack = CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(), stack.getItem());

        if (optStack.isEmpty() || !(slotContext.entity() instanceof Player player)
                || DurabilityUtils.isBroken(stack) || player.tickCount % 20 != 0)
            return;

        Level world = player.getCommandSenderWorld();

        if (!AbilityUtils.canUseAbility(stack, "inf_flight")) {
            int time = NBTUtils.getInt(stack, TAG_TIME, 0);
            double delay = 60 / AbilityUtils.getAbilityValue(stack, "flight", "delay");
            double maxTime = AbilityUtils.getAbilityValue(stack, "flight", "duration");

            if (world.isClientSide() && !player.isCreative() && !player.isSpectator()) {
                if (time < maxTime && !player.getCooldowns().isOnCooldown(this)) {
                    startFlying(player);

                    if (player.getAbilities().flying) {
                        if (maxTime - time > 5)
                            player.displayClientMessage(Component
                                    .translatable("item.ckh_tweaks.jodah_ring.flight_time",
                                    Math.round(maxTime - time)).withStyle(ChatFormatting.GREEN), true);
                        else
                            player.displayClientMessage(Component
                                    .translatable("item.ckh_tweaks.jodah_ring.flight_time",
                                    Math.round(maxTime - time)).withStyle(ChatFormatting.RED), true);

                        NBTUtils.setInt(stack, TAG_TIME, time + 1);
                    } else {
                        NBTUtils.setInt(stack, TAG_TIME, 0);
                    }
                } else if (time >= maxTime) {
                    stopFlying(player);
                    player.getCooldowns().addCooldown(this, (int) delay * 20);

                    LevelingUtils.addExperience(player, stack, (int) Math.floor(time / 10F));
                    NBTUtils.setInt(stack, TAG_TIME, 0);
                }
            }
        } else if (AbilityUtils.canUseAbility(stack, "inf_flight")
                && world.isClientSide() && !player.isCreative() && !player.isSpectator()) {
            startFlying(player);
        }
    }

    private void startFlying(Player player) {
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();
    }

    private void stopFlying(Player player) {
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
    }
}
