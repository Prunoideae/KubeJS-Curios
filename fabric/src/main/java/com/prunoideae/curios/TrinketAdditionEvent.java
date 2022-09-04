package com.prunoideae.curios;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.prunoideae.curios.behaviour.AbstractBehaviourBuilder;
import com.prunoideae.curios.behaviour.CuriosItemBehaviour;
import com.prunoideae.curios.behaviour.EmptyBehaviourBuilder;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import dev.latvian.mods.kubejs.event.EventJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class TrinketAdditionEvent extends EventJS {
    public static final String ID = "trinket.add";

    public void attachTrinket(Item item, Consumer<AbstractBehaviourBuilder> builder) {
        AbstractBehaviourBuilder behaviourBuilder = new EmptyBehaviourBuilder();
        builder.accept(behaviourBuilder);
        TrinketsApi.registerTrinket(item, new Trinket() {
            private final CuriosItemBehaviour behaviour = behaviourBuilder.createBehaviour();

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
            public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                return behaviour.canUnequip(stack, entity);
            }

            @Override
            public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
                return switch (behaviour.canDrop(stack, entity)) {
                    case DROP -> TrinketEnums.DropRule.DROP;
                    case KEEP -> TrinketEnums.DropRule.KEEP;
                    case DESTROY -> TrinketEnums.DropRule.DESTROY;
                    case DEFAULT -> TrinketEnums.DropRule.DEFAULT;
                };
            }

            @Override
            public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
                return behaviour.getEquippedAttributeModifiers(stack, entity);
            }
        });
    }


    @Environment(EnvType.CLIENT)
    public void attachRenderer(Item item, CuriosItemRenderer renderer) {
        TrinketRendererRegistry.registerRenderer(item,
                (ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
                 PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity livingEntity,
                 float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) -> renderer.render(
                        new CuriosItemRenderer.RenderContextJS((HumanoidModel<?>) contextModel,
                                stack, livingEntity,
                                matrices, vertexConsumers,
                                light, limbAngle, limbDistance, tickDelta,
                                animationProgress, headYaw, headPitch
                        )
                ));
    }
}
