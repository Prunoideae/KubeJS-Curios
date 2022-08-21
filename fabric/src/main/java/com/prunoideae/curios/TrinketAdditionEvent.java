package com.prunoideae.curios;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class TrinketAdditionEvent extends EventJS {
    public static final String ID = "trinket.add";

    public void attachTrinket(Item item, Consumer<CuriosItemBehaviour.Builder> builder) {
        CuriosItemBehaviour.Builder behaviourBuilder = new CuriosItemBehaviour.Builder();
        builder.accept(behaviourBuilder);
        TrinketsApi.registerTrinket(item, new Trinket() {
            private final CuriosItemBehaviour behaviour = behaviourBuilder.create();

            @Override
            public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
                behaviour.onWornTick(stack, entity);
            }

            @Override
            public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                behaviour.onEquipped(stack, entity);
            }

            @Override
            public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                behaviour.onUnequipped(stack, entity);
            }

            @Override
            public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                return behaviour.canEquip(stack, entity);
            }

            @Override
            public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
                return behaviour.getEquippedAttributeModifiers(stack, entity);
            }
        });
    }
}
