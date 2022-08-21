package com.prunoideae.curios.curios;

import com.prunoideae.curios.CuriosItemBehaviour;
import com.prunoideae.curios.forge.KubeJSCuriosForge;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class CuriosItemBuilder extends ItemBuilder {
    private boolean isSlotSet = false;
    private final CuriosItemBehaviour.Builder behaviourBuilder = new CuriosItemBehaviour.Builder();

    public CuriosItemBuilder(ResourceLocation i) {
        super(i);
    }

    public CuriosItemBuilder slot(String slot) {
        isSlotSet = true;
        this.tag(new ResourceLocation("curios", slot));
        return this;
    }

    //Adds something for Probe
    public CuriosItemBuilder presetSlot(SlotTypePreset preset) {
        this.slot(preset.getIdentifier());
        return this;
    }

    public CuriosItemBuilder onWornTick(BiConsumer<ItemStackJS, LivingEntityJS> onWornTick) {
        behaviourBuilder.onWornTick(onWornTick);
        return this;
    }

    public CuriosItemBuilder onEquipped(BiConsumer<ItemStackJS, LivingEntityJS> onEquipped) {
        behaviourBuilder.onEquipped(onEquipped);
        return this;
    }

    public CuriosItemBuilder onUnequipped(BiConsumer<ItemStackJS, LivingEntityJS> onUnequipped) {
        behaviourBuilder.onUnequipped(onUnequipped);
        return this;
    }

    public CuriosItemBuilder canEquip(BiPredicate<ItemStackJS, LivingEntityJS> canEquip) {
        behaviourBuilder.canEquip(canEquip);
        return this;
    }

    public CuriosItemBuilder canAttributeApply(BiPredicate<ItemStackJS, LivingEntityJS> attributeApplyModifier) {
        behaviourBuilder.canAttributeApply(attributeApplyModifier);
        return this;
    }

    public CuriosItemBuilder addAttribute(Attribute attribute, UUID uuid, float amount, AttributeModifier.Operation operation) {
        behaviourBuilder.addAttribute(attribute, uuid, amount, operation);
        return this;
    }

    @Override
    public Item createObject() {
        if (!isSlotSet)
            this.tag(new ResourceLocation("curios", "curios"));
        Item itemCreated = new CuriosItemJS(createItemProperties());
        KubeJSCuriosForge.behaviours.put(itemCreated, behaviourBuilder.create());
        return itemCreated;
    }
}
