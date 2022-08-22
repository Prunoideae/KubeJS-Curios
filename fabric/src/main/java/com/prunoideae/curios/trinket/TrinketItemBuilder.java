package com.prunoideae.curios.trinket;

import com.prunoideae.curios.CuriosItemBehaviour;
import com.prunoideae.curios.CuriosItemRenderer;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class TrinketItemBuilder extends ItemBuilder {
    public static final Map<Item, CuriosItemRenderer> ITEM_RENDERERS = new HashMap<>();

    private final CuriosItemBehaviour.Builder behaviorBuilder = new CuriosItemBehaviour.Builder();
    private CuriosItemRenderer renderer = null;
    private boolean isSlotSet = false;

    public TrinketItemBuilder(ResourceLocation i) {
        super(i);
    }

    @HideFromJS
    public CuriosItemBehaviour getBehaviour() {
        return behaviorBuilder.create();
    }

    public TrinketItemBuilder slot(String bodyPart, String slot) {
        return this.slot(bodyPart + "/" + slot);
    }

    public TrinketItemBuilder slot(String slot) {
        isSlotSet = true;
        this.tag(new ResourceLocation("trinkets", slot));
        return this;
    }

    public TrinketItemBuilder onWornTick(BiConsumer<ItemStackJS, LivingEntityJS> onWornTick) {
        behaviorBuilder.onWornTick(onWornTick);
        return this;
    }

    public TrinketItemBuilder onEquipped(BiConsumer<ItemStackJS, LivingEntityJS> onEquipped) {
        behaviorBuilder.onEquipped(onEquipped);
        return this;
    }

    public TrinketItemBuilder onUnequipped(BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped) {
        behaviorBuilder.onUnequipped(onUnequipped);
        return this;
    }

    public TrinketItemBuilder canEquip(BiPredicate<ItemStackJS, LivingEntityJS> canEquip) {
        behaviorBuilder.canEquip(canEquip);
        return this;
    }

    public TrinketItemBuilder canAttributeApply(BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier) {
        behaviorBuilder.canAttributeApply(attributeApplyModifier);
        return this;
    }

    public TrinketItemBuilder addAttribute(Attribute attribute, UUID uuid, float amount, AttributeModifier.Operation operation) {
        behaviorBuilder.addAttribute(attribute, uuid, amount, operation);
        return this;
    }

    public TrinketItemBuilder render(CuriosItemRenderer renderer) {
        this.renderer = renderer;
        return this;
    }

    @Override
    public Item createObject() {
        if (!isSlotSet)
            slot("all");
        Item itemCreated = new TrinketItemJS(this);
        if (this.renderer != null) {
            ITEM_RENDERERS.put(itemCreated, this.renderer);
        }
        return itemCreated;
    }
}
