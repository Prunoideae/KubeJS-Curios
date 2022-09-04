package com.prunoideae.curios.curios;

import com.prunoideae.curios.CuriosItemRenderer;
import com.prunoideae.curios.behaviour.AbstractBehaviourBuilder;
import com.prunoideae.curios.forge.KubeJSCuriosForge;
import com.prunoideae.curios.forge.KubeJSCuriosForgeClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosItemBuilder extends AbstractBehaviourBuilder {
    private boolean isSlotSet = false;

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

    public CuriosItemBuilder render(CuriosItemRenderer itemRenderer) {
        KubeJSCuriosForgeClient.renderers.put(this, () -> new CuriosRendererImpl(itemRenderer));
        return this;
    }

    @Override
    public Item createObject() {
        if (!isSlotSet)
            this.tag(new ResourceLocation("curios", "curio"));
        Item itemCreated = new CuriosItemJS(createItemProperties());
        KubeJSCuriosForge.behaviours.put(itemCreated, this.createBehaviour());
        return itemCreated;
    }
}
