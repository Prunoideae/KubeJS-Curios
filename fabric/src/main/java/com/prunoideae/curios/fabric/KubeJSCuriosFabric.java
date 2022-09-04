package com.prunoideae.curios.fabric;

import com.prunoideae.curios.KubeJSCurios;
import dev.architectury.utils.EnvExecutor;
import net.fabricmc.api.ModInitializer;

public class KubeJSCuriosFabric implements ModInitializer {
    public static KubeJSCuriosFabricCommon PROXY;

    @Override
    public void onInitialize() {
        KubeJSCurios.init();
        PROXY = EnvExecutor.getEnvSpecific(() -> KubeJSCuriosFabricClient::new, () -> KubeJSCuriosFabricCommon::new);
    }
}
