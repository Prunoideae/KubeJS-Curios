package com.prunoideae.curios.forge;

import com.google.common.collect.Multimap;
import com.prunoideae.curios.CuriosItemBehaviour;
import com.prunoideae.curios.CuriosItemRenderer;
import dev.architectury.platform.forge.EventBuses;
import com.prunoideae.curios.KubeJSCurios;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod(KubeJSCurios.MOD_ID)
public class KubeJSCuriosForge {
    public static Map<Item, CuriosItemBehaviour> behaviours = new HashMap<>();
    public static Map<Item, ICurioRenderer> renderers = new HashMap<>();

    public KubeJSCuriosForge() {
        EventBuses.registerModEventBus(KubeJSCurios.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        KubeJSCurios.init();
        eventBus.addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        for (Map.Entry<Item, ICurioRenderer> entry : renderers.entrySet()) {
            Item item = entry.getKey();
            ICurioRenderer renderer = entry.getValue();
            CuriosRendererRegistry.register(item, () -> renderer);
        }
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        CuriosItemBehaviour behaviour = behaviours.get(stack.getItem());
        if (behaviour == null)
            return;

        ICurio curioBehaviour = new ICurio() {
            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public boolean canEquip(SlotContext slotContext) {
                return behaviour.canEquip(stack, slotContext.entity());
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                behaviour.onEquipped(stack, slotContext.entity());
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                behaviour.onUnequipped(stack, slotContext.entity());
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                behaviour.onWornTick(stack, slotContext.entity());
            }

            @Override
            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
                return behaviour.getEquippedAttributeModifiers(stack, slotContext.entity());
            }
        };
        ICapabilityProvider provider = new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOptional = LazyOptional.of(() -> curioBehaviour);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                return CuriosCapability.ITEM.orEmpty(capability, curioOptional);
            }
        };
        event.addCapability(CuriosCapability.ID_ITEM, provider);
    }
}
