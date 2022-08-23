package com.prunoideae.curios.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.prunoideae.curios.CuriosItemBehaviour;
import com.prunoideae.curios.CuriosItemRenderer;
import com.prunoideae.curios.forge.KubeJSCuriosForge;
import dev.latvian.mods.kubejs.entity.LivingEntityJS;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class CuriosItemBuilder extends ItemBuilder {
    private boolean isSlotSet = false;
    private CuriosItemRenderer itemRenderer = null;
    private final CuriosItemBehaviour.Builder behaviourBuilder = new CuriosItemBehaviour.Builder();

    public CuriosItemBuilder(ResourceLocation i) {
        super(i);
    }

    public CuriosItemBuilder slot(String slot) {
        isSlotSet = true;
        this.maxStackSize = 1;
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

    public CuriosItemBuilder render(CuriosItemRenderer renderer) {
        this.itemRenderer = renderer;
        return this;
    }

    @Override

    public Item createObject() {
        if (!isSlotSet)
            this.tag(new ResourceLocation("curios", "curio"));
        Item itemCreated = new CuriosItemJS(createItemProperties());
        KubeJSCuriosForge.behaviours.put(itemCreated, behaviourBuilder.create());
        if (this.itemRenderer != null)
            KubeJSCuriosForge.renderers.put(itemCreated, new ICurioRenderer() {
                private final CuriosItemRenderer renderer = itemRenderer;

                @Override
                public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                    this.renderer.render(
                            new CuriosItemRenderer.RenderContextJS(
                                    (HumanoidModel<?>) renderLayerParent.getModel(),
                                    stack, slotContext.entity(),
                                    matrixStack, renderTypeBuffer,
                                    light,
                                    limbSwing, limbSwingAmount,
                                    partialTicks, ageInTicks,
                                    netHeadYaw, headPitch
                            ));
                }
            });
        return itemCreated;
    }
}
