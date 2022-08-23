package com.prunoideae.curios.curios;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;

import java.util.Map;

public class CuriosItemJS extends Item {
    public CuriosItemJS(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ICuriosHelper helper = CuriosApi.getCuriosHelper();
        ItemStack stack = player.getItemInHand(hand);
        ICuriosItemHandler handler = helper.getCuriosHandler(player).resolve().orElse(null);
        if (handler != null) {
            Map<String, ICurioStacksHandler> curioSlots = handler.getCurios();
            for (String slot : helper.getCurioTags(stack.getItem())) {
                ICurioStacksHandler stacksHandler = curioSlots.get(slot);
                if (stacksHandler == null)
                    continue;
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    if (stacks.insertItem(i, stack.copy(), false).isEmpty()) {
                        stack.setCount(0);
                        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                    }
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }
}
