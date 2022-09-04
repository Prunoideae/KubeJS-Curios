package com.prunoideae.curios.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.prunoideae.curios.CuriosItemRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@OnlyIn(Dist.CLIENT)
public class CuriosRendererImpl implements ICurioRenderer {
    private final CuriosItemRenderer renderer;

    public CuriosRendererImpl(CuriosItemRenderer renderer) {
        this.renderer = renderer;
    }

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
}
