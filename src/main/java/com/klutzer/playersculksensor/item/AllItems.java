package com.klutzer.playersculksensor.item;

import com.klutzer.playersculksensor.PlayerSculkSensorMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AllItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PlayerSculkSensorMod.MOD_ID);
    public static void register(IEventBus event){
        ITEMS.register(event);
    }
}
