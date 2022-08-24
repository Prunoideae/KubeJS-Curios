package com.prunoideae.curios.behaviour;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public abstract class AbstractBehaviourBuilder extends ItemBuilder {
    private BiConsumer<ItemStackJS, LivingEntityJS> onWornTick = null;
    private BiConsumer<ItemStackJS, LivingEntityJS> onEquipped = null;
    private BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped = null;
    private BiPredicate<ItemStackJS, LivingEntityJS> canEquip = null;
    private BiPredicate<ItemStackJS, LivingEntityJS> canUnequip = null;
    private CuriosItemBehaviour.CanDrop canDrop = null;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers = ArrayListMultimap.create();
    private BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier = null;

    public AbstractBehaviourBuilder(ResourceLocation i) {
        super(i);
    }

    public AbstractBehaviourBuilder onWornTick(BiConsumer<ItemStackJS, LivingEntityJS> onWornTick) {
        this.onWornTick = onWornTick;
        return this;
    }

    public AbstractBehaviourBuilder onEquipped(BiConsumer<ItemStackJS, LivingEntityJS> onEquipped) {
        this.onEquipped = onEquipped;
        return this;
    }

    public AbstractBehaviourBuilder onUnequipped(BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped) {
        this.onUnequipped = onUnequipped;
        return this;
    }

    public AbstractBehaviourBuilder canEquip(BiPredicate<ItemStackJS, LivingEntityJS> canEquip) {
        this.canEquip = canEquip;
        return this;
    }

    public AbstractBehaviourBuilder canUnequip(BiPredicate<ItemStackJS, LivingEntityJS> canEquip) {
        this.canUnequip = canEquip;
        return this;
    }

    public AbstractBehaviourBuilder canDrop(CuriosItemBehaviour.CanDrop canDrop) {
        this.canDrop = canDrop;
        return this;
    }


    public AbstractBehaviourBuilder canAttributeApply(BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier) {
        this.attributeApplyModifier = attributeApplyModifier;
        return this;
    }

    public AbstractBehaviourBuilder addAttribute(Attribute attribute, UUID uuid, float amount, AttributeModifier.Operation operation) {
        this.attributeModifiers.put(attribute, new AttributeModifier(uuid, uuid.toString(), amount, operation));
        return this;
    }

    @HideFromJS
    public CuriosItemBehaviour createBehaviour() {
        return new CuriosItemBehaviour(onWornTick, onEquipped, onUnequipped, canEquip, canUnequip, canDrop, attributeModifiers, attributeApplyModifier);
    }
}
