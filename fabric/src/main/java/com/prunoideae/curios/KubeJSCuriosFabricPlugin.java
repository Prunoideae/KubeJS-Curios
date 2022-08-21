package com.prunoideae.curios;

import com.prunoideae.curios.trinket.TrinketItemBuilder;
import dev.emi.trinkets.api.TrinketsApi;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;

public class KubeJSCuriosFabricPlugin extends KubeJSPlugin {

    @Override
    public void addBindings(BindingsEvent event) {
        event.add("Trinket", TrinketsApi.class);
    }

    @Override
    public void init() {
        RegistryObjectBuilderTypes.ITEM.addType("trinket:accessory", TrinketItemBuilder.class, TrinketItemBuilder::new);
    }

    @Override
    public void afterInit() {
        new TrinketAdditionEvent().post(ScriptType.STARTUP, TrinketAdditionEvent.ID);
    }
}
