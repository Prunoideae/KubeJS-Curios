package com.prunoideae.curios;

import com.prunoideae.curios.curios.CuriosItemBuilder;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class KubeJSCuriosForgePlugin extends KubeJSPlugin {

    @Override
    public void addBindings(BindingsEvent event) {
        event.add("Curios", CuriosApi.class);
        event.add("CuriosRenderer", ICurioRenderer.class);
    }

    @Override
    public void init() {
        RegistryObjectBuilderTypes.ITEM.addType("curios:trinket", CuriosItemBuilder.class, CuriosItemBuilder::new);
    }

    @Override
    public void afterInit() {
        new CuriosAdditionEvent().post(ScriptType.STARTUP, CuriosAdditionEvent.ID);
    }
}
