package net.modekh.peacocking.items.relics.ring;

import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastPredicate;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.NBTUtils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import top.theillusivec4.curios.api.SlotContext;

public class PeacockRingItem extends RelicItem {
    public static final String TAG_TICKS_COUNT = "ticks_count";
    public static final String TAG_HITTING = "hitting";

    @Override
    public RelicData constructDefaultRelicData() {
        return RelicData.builder()
                .abilities(AbilitiesData.builder()
                        .ability(AbilityData.builder("flight")
                                .stat(StatData.builder("duration")
                                        .initialValue(5D, 30D)
                                        .upgradeModifier(UpgradeOperation.ADD, 1D)
                                        .formatValue(value -> MathUtils.round(value, 0))
                                        .build())
                                .stat(StatData.builder("delay")
                                        .initialValue(1D, 12D)
                                        .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25D)
                                        .formatValue(value -> MathUtils.round(value, 1))
                                        .build())
                                .build())
                        .ability(AbilityData.builder("thousand_eyes")
                                .maxLevel(10)
                                .active(CastType.INSTANTANEOUS, CastPredicate.builder()
                                        .predicate("hitting", data -> {
                                            Player player = data.getPlayer();

                                            return player.getHealth() <= 10;
                                        })
                                        .build())
                                .stat(StatData.builder("damage")
                                        .initialValue(2D, 10D)
                                        .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25D)
                                        .formatValue(value -> MathUtils.round(value, 1))
                                        .build())
                                .build())
                        .ability(AbilityData.builder("inf_flight")
                                .requiredLevel(5)
                                .build())
                        .build())
                .leveling(new LevelingData(100, 10, 100))
//                .style(StyleData.builder()
//                        .borders(0xeed551, 0xdcbe1d)
//                        .build())
                .build();
    }

    @Override
    public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
        if (ability.equals("thousand_eyes")) {
            NBTUtils.setBoolean(stack, TAG_HITTING, true);
            player.sendSystemMessage(Component.literal("hitting"));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player) || !player.level.isClientSide)
            return;

        Level world = player.getLevel();
        Vec3 playerPos = player.position();

        if (!player.isCreative() && !player.isSpectator()) {
            if (!canUseAbility(stack, "inf_flight")) {
                int time = NBTUtils.getInt(stack, TAG_TICKS_COUNT, 0);
                int maxTime = (int) getAbilityValue(stack, "flight", "duration") * 20;

                if (!player.getCooldowns().isOnCooldown(this) && time < maxTime) {
                    startFlying(player);

                    if (player.getAbilities().flying) {
                        if (player.tickCount % 20 == 0)
                            player.displayClientMessage(Component
                                    .translatable("item.ckh_tweaks.jodah_ring.flight_time",
                                            (maxTime - time) / 20)
                                    .withStyle(maxTime - time > 100
                                            ? ChatFormatting.GREEN : ChatFormatting.RED), true);

//                        player.sendSystemMessage(Component.literal("hey, you're flying!"));
                        if (player.tickCount % 60 == 0) {
                            player.sendSystemMessage(Component.literal(String.valueOf(player.tickCount)));
                            dropExperience(world, playerPos, stack, 2);
                        }

                        NBTUtils.setInt(stack, TAG_TICKS_COUNT, time + 1);
                    } else
                        NBTUtils.setInt(stack, TAG_TICKS_COUNT, time > 0 ? time - 1 : 0);
                } else if (time >= maxTime) {
                    int delay = (int) (1200 / getAbilityValue(stack, "flight", "delay"));

                    stopFlying(player);
                    player.getCooldowns().addCooldown(this, delay);
//                    addPoints(stack, (player.tickCount > 0 && player.tickCount % 60 == 0) ? 2 : 0);

                    NBTUtils.setInt(stack, TAG_TICKS_COUNT, 0);
                }
            } else {
                startFlying(player);

                if ((player.getAbilities().flying || !player.isOnGround()) && player.tickCount % 60 == 0) {
                    player.sendSystemMessage(Component.literal("just fly bro"));
                    dropExperience(world, playerPos, stack, 2);
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();

        if (livingEntity instanceof Player player) {
            if (player.isCreative() || player.isSpectator())
                return;

            stopFlying(player);
        }
    }

    private void startFlying(@NotNull Player player) {
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();
    }

    private void stopFlying(@NotNull Player player) {
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
    }

//    @Mod.EventBusSubscriber
//    public static class Events {
//
//    }
}
