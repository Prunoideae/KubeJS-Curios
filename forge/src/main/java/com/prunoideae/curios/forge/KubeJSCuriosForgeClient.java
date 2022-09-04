package com.prunoideae.curios.forge;

import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class KubeJSCuriosForgeClient extends KubeJSCuriosForgeCommon {

    public static Map<Supplier<Item>, Supplier<ICurioRenderer>> renderers = new HashMap<>();

    @Override
    public void clientBindings(BindingsEvent event) {
        event.add("CuriosRenderer", ICurioRenderer.class);
    }
}
