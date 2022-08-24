package com.prunoideae.curios.behaviour;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class EmptyBehaviourBuilder extends AbstractBehaviourBuilder {

    public EmptyBehaviourBuilder() {
        super(new ResourceLocation("kubejs_curios:trinket_behaviour"));
    }

    @Override
    public Item createObject() {
        return null;
    }
}
