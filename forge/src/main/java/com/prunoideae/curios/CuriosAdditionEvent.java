package com.prunoideae.curios;

import com.prunoideae.curios.behaviour.AbstractBehaviourBuilder;
import com.prunoideae.curios.behaviour.EmptyBehaviourBuilder;
import com.prunoideae.curios.curios.CuriosRendererImpl;
import com.prunoideae.curios.forge.KubeJSCuriosForge;
import com.prunoideae.curios.forge.KubeJSCuriosForgeClient;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public class CuriosAdditionEvent extends EventJS {
    public static final String ID = "curios.add";

    public void attachCurios(Item item, Consumer<AbstractBehaviourBuilder> builder) {
        AbstractBehaviourBuilder behaviour = new EmptyBehaviourBuilder();
        builder.accept(behaviour);
        KubeJSCuriosForge.behaviours.put(item, behaviour.createBehaviour());
    }

    @OnlyIn(Dist.CLIENT)
    public void attachRenderer(Item item, CuriosItemRenderer itemRenderer) {
        KubeJSCuriosForgeClient.renderers.put(() -> item, () -> new CuriosRendererImpl(itemRenderer));
    }
}
