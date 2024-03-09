package com.klutzer.playersculksensor.entity;

import com.klutzer.playersculksensor.PlayerSculkSensorMod;
import com.klutzer.playersculksensor.block.AllBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class AllEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PlayerSculkSensorMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PlayerSculkSensorBlockEntity>> PLAYERSCULKSENSOR_ENTITY =
            BLOCK_ENTITIES.register("playersculksensorentity", () ->
                    BlockEntityType.Builder.of(PlayerSculkSensorBlockEntity::new, AllBlocks.PLAYERSCULKSENSOR_BLOCK.get()).build(null));
    public static void register(IEventBus event){
        BLOCK_ENTITIES.register(event);
    }
}
