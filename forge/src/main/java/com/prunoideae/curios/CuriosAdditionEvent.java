package com.prunoideae.curios;

import com.prunoideae.curios.forge.KubeJSCuriosForge;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class CuriosAdditionEvent extends EventJS {
    public static final String ID = "curios.add";

    public void attachCurios(Item item, Consumer<CuriosItemBehaviour.Builder> builder) {
        CuriosItemBehaviour.Builder behaviour = new CuriosItemBehaviour.Builder();
        builder.accept(behaviour);
        KubeJSCuriosForge.behaviours.put(item, behaviour.create());
    }
}
