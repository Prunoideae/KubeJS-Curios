package com.prunoideae.curios.behaviour;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class CuriosItemBehaviour {

    @FunctionalInterface
    public interface CanDrop {
        DropRuleJS canDrop(ItemStackJS stack, LivingEntityJS entity);
    }

    private final BiConsumer<ItemStackJS, LivingEntityJS> onWornTick;
    private final BiConsumer<ItemStackJS, LivingEntityJS> onEquipped;
    private final BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped;
    private final BiPredicate<ItemStackJS, LivingEntityJS> canEquip;
    private final BiPredicate<ItemStackJS, LivingEntityJS> canUnequip;
    private final CanDrop canDrop;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;
    private final BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier;

    private final static Multimap<Attribute, AttributeModifier> EMPTY = ImmutableMultimap.of();


    public CuriosItemBehaviour(BiConsumer<ItemStackJS, LivingEntityJS> onWornTick, BiConsumer<ItemStackJS, LivingEntityJS> onEquipped, BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped, BiPredicate<ItemStackJS, LivingEntityJS> canEquip, BiPredicate<ItemStackJS, LivingEntityJS> canUnequip, CanDrop canDrop, Multimap<Attribute, AttributeModifier> attributeModifiers, BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier) {
        this.onWornTick = onWornTick;
        this.onEquipped = onEquipped;
        this.onUnequipped = onUnequipped;
        this.canEquip = canEquip;
        this.canUnequip = canUnequip;
        this.canDrop = canDrop;
        this.attributeModifiers = attributeModifiers;
        this.attributeApplyModifier = attributeApplyModifier;
    }

    private static LivingEntityJS getEntityJS(LivingEntity entity) {
        if (entity == null)
            return null;
        return (LivingEntityJS) UtilsJS.getLevel(entity.level).getEntity(entity);
    }

    public void onWornTick(ItemStack stack, LivingEntity entity) {
        if (onWornTick != null) onWornTick.accept(ItemStackJS.of(stack), getEntityJS(entity));
    }

    public void onEquipped(ItemStack stack, LivingEntity entity) {
        if (onEquipped != null) onEquipped.accept(ItemStackJS.of(stack), getEntityJS(entity));
    }

    public void onUnequipped(ItemStack stack, LivingEntity entity) {
        if (onUnequipped != null) onUnequipped.accept(ItemStackJS.of(stack), getEntityJS(entity));
    }

    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return canEquip == null || canEquip.test(ItemStackJS.of(stack), getEntityJS(entity));
    }

    public boolean canUnequip(ItemStack stack, LivingEntity entity) {
        return canUnequip == null || canUnequip.test(ItemStackJS.of(stack), getEntityJS(entity));
    }

    public DropRuleJS canDrop(ItemStack stack, LivingEntity entity) {
        return canDrop == null ? DropRuleJS.DEFAULT : canDrop.canDrop(ItemStackJS.of(stack), getEntityJS(entity));
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack, LivingEntity entity) {
        return (attributeApplyModifier == null || attributeApplyModifier.test(ItemStackJS.of(stack), getEntityJS(entity))) ? attributeModifiers : EMPTY;
    }

}
