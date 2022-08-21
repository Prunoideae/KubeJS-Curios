package com.prunoideae.curios.fabric;

import com.prunoideae.curios.KubeJSCurios;
import net.fabricmc.api.ModInitializer;

public class KubeJSCuriosFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        KubeJSCurios.init();
    }
}
