package com.prunoideae.curios.trinket;

import com.prunoideae.curios.CuriosItemRenderer;
import com.prunoideae.curios.behaviour.AbstractBehaviourBuilder;
import com.prunoideae.curios.behaviour.CuriosItemBehaviour;
import com.prunoideae.curios.fabric.KubeJSCuriosFabricClient;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class TrinketItemBuilder extends AbstractBehaviourBuilder {
    private boolean isSlotSet = false;

    public TrinketItemBuilder(ResourceLocation i) {
        super(i);
    }

    @HideFromJS
    public CuriosItemBehaviour getBehaviour() {
        return this.createBehaviour();
    }

    public TrinketItemBuilder slot(String bodyPart, String slot) {
        return this.slot(bodyPart + "/" + slot);
    }

    public TrinketItemBuilder slot(String slot) {
        isSlotSet = true;
        this.tag(new ResourceLocation("trinkets", slot));
        return this;
    }

    public TrinketItemBuilder render(CuriosItemRenderer renderer) {
        KubeJSCuriosFabricClient.ITEM_RENDERERS.put(this, () -> renderer);
        return this;
    }

    @Override
    public Item createObject() {
        if (!isSlotSet)
            slot("all");
        return new TrinketItemJS(this);
    }
}
