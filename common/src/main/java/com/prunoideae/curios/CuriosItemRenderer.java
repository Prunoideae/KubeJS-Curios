package com.prunoideae.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface CuriosItemRenderer {
    void render(RenderContextJS context);

    class RenderContextJS {
        public final HumanoidModel<?> bipedModel;
        public final ItemStack stack;
        public final LivingEntity entity;
        public final PoseStack poseStack;
        public final MultiBufferSource buffers;
        public final int light;
        public final float limbSwing;
        public final float limbSwingAmount;
        public final float partialTicks;
        public final float ageInTicks;
        public final float netHeadYaw;
        public final float headPitch;

        public RenderContextJS(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity entity, PoseStack poseStack, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            this.bipedModel = bipedModel;
            this.stack = stack;
            this.entity = entity;
            this.poseStack = poseStack;
            this.buffers = buffers;
            this.light = light;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.partialTicks = partialTicks;
            this.ageInTicks = ageInTicks;

            this.netHeadYaw = netHeadYaw;
            this.headPitch = headPitch;
        }
    }
}
