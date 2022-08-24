package com.prunoideae.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.prunoideae.curios.behaviour.EmptyBehaviourBuilder;
import com.prunoideae.curios.behaviour.AbstractBehaviourBuilder;
import com.prunoideae.curios.forge.KubeJSCuriosForge;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.function.Consumer;

public class CuriosAdditionEvent extends EventJS {
    public static final String ID = "curios.add";

    public void attachCurios(Item item, Consumer<AbstractBehaviourBuilder> builder) {
        AbstractBehaviourBuilder behaviour = new EmptyBehaviourBuilder();
        builder.accept(behaviour);
        KubeJSCuriosForge.behaviours.put(item, behaviour.createBehaviour());
    }

    public void attachRenderer(Item item, CuriosItemRenderer itemRenderer) {
        KubeJSCuriosForge.renderers.put(item, new ICurioRenderer() {
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
    }
}
