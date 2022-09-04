package com.prunoideae.curios.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.prunoideae.curios.CuriosItemRenderer;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class KubeJSCuriosFabricClient extends KubeJSCuriosFabricCommon implements ClientModInitializer {
    public static final Map<Supplier<Item>, Supplier<CuriosItemRenderer>> ITEM_RENDERERS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        for (Map.Entry<Supplier<Item>, Supplier<CuriosItemRenderer>> entry : ITEM_RENDERERS.entrySet()) {
            Item key = entry.getKey().get();
            CuriosItemRenderer renderer = entry.getValue().get();
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

    @Override
    public void clientBindings(BindingsEvent event) {
        event.add("TrinketRenderer", TrinketRenderer.class);
    }
}
