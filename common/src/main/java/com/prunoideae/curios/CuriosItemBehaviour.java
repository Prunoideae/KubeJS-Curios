package com.prunoideae.curios;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class CuriosItemBehaviour {

    private final BiConsumer<ItemStackJS, LivingEntityJS> onWornTick;
    private final BiConsumer<ItemStackJS, LivingEntityJS> onEquipped;
    private final BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped;
    private final BiPredicate<ItemStackJS, LivingEntityJS> canEquip;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier;

    private final static Multimap<Attribute, AttributeModifier> EMPTY = ImmutableMultimap.of();


    public CuriosItemBehaviour(BiConsumer<ItemStackJS, LivingEntityJS> onWornTick, BiConsumer<ItemStackJS, LivingEntityJS> onEquipped, BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped, BiPredicate<ItemStackJS, LivingEntityJS> canEquip, Multimap<Attribute, AttributeModifier> attributeModifiers, BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier) {
        this.onWornTick = onWornTick;
        this.onEquipped = onEquipped;
        this.onUnequipped = onUnequipped;
        this.canEquip = canEquip;
        this.attributeModifiers = attributeModifiers;
        this.attributeApplyModifier = attributeApplyModifier;
    }

    public void onWornTick(ItemStack stack, LivingEntity entity) {
        if (onWornTick != null) onWornTick.accept(ItemStackJS.of(stack), new LivingEntityJS(entity));
    }

    public void onEquipped(ItemStack stack, LivingEntity entity) {
        if (onEquipped != null) onEquipped.accept(ItemStackJS.of(stack), new LivingEntityJS(entity));
    }

    public void onUnequipped(ItemStack stack, LivingEntity entity) {
        if (onUnequipped != null) onUnequipped.accept(ItemStackJS.of(stack), new LivingEntityJS(entity));
    }

    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return canEquip == null || canEquip.test(ItemStackJS.of(stack), new LivingEntityJS(entity));
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack, LivingEntity entity) {
        return (attributeApplyModifier == null || attributeApplyModifier.test(ItemStackJS.of(stack), new LivingEntityJS(entity))) ? attributeModifiers : EMPTY;
    }

    public static class Builder {
        private BiConsumer<ItemStackJS, LivingEntityJS> onWornTick = null;
        private BiConsumer<ItemStackJS, LivingEntityJS> onEquipped = null;
        private BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped = null;
        private BiPredicate<ItemStackJS, LivingEntityJS> canEquip = null;
        private final Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
        private BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier = null;

        public void onWornTick(BiConsumer<ItemStackJS, LivingEntityJS> onWornTick) {
            this.onWornTick = onWornTick;
        }

        public void onEquipped(BiConsumer<ItemStackJS, LivingEntityJS> onEquipped) {
            this.onEquipped = onEquipped;
        }

        public void onUnequipped(BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped) {
            this.onUnequipped = onUnequipped;
        }

        public void canEquip(BiPredicate<ItemStackJS, LivingEntityJS> canEquip) {
            this.canEquip = canEquip;
        }

        public void canAttributeApply(BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier) {
            this.attributeApplyModifier = attributeApplyModifier;
        }

        public void addAttribute(Attribute attribute, UUID uuid, float amount, AttributeModifier.Operation operation) {
            this.attributeModifiers.put(attribute, new AttributeModifier(uuid, uuid.toString(), amount, operation));
        }

        @HideFromJS
        public CuriosItemBehaviour create() {
            return new CuriosItemBehaviour(onWornTick, onEquipped, onUnequipped, canEquip, attributeModifiers, attributeApplyModifier);
        }
    }
}
