package com.prunoideae.curios.trinket;

import com.google.common.collect.Multimap;
import com.prunoideae.curios.behaviour.CuriosItemBehaviour;
import com.prunoideae.curios.behaviour.DropRuleJS;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class TrinketItemJS extends TrinketItem {
    private final CuriosItemBehaviour behaviour;

    public TrinketItemJS(TrinketItemBuilder builder) {
        super(builder.createItemProperties());
        this.behaviour = builder.getBehaviour();
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        this.behaviour.onWornTick(stack, entity);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        this.behaviour.onEquipped(stack, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        this.behaviour.onUnequipped(stack, entity);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return this.behaviour.canEquip(stack, entity);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return this.behaviour.canUnequip(stack, entity);
    }

    @Override
    public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
        DropRuleJS dropRuleJS = behaviour.canDrop(stack, entity);
        return switch (dropRuleJS) {
            case DROP -> TrinketEnums.DropRule.DROP;
            case KEEP -> TrinketEnums.DropRule.KEEP;
            case DESTROY -> TrinketEnums.DropRule.DESTROY;
            case DEFAULT -> TrinketEnums.DropRule.DEFAULT;
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        return this.behaviour.getEquippedAttributeModifiers(stack, entity);
    }


}
