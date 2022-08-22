package com.prunoideae.curios.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.prunoideae.curios.CuriosItemRenderer;
import com.prunoideae.curios.trinket.TrinketItemBuilder;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class KubeJSCuriosFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (Map.Entry<Item, CuriosItemRenderer> entry : TrinketItemBuilder.ITEM_RENDERERS.entrySet()) {
            Item key = entry.getKey();
            CuriosItemRenderer renderer = entry.getValue();
            TrinketRendererRegistry.registerRenderer(key,
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
}
