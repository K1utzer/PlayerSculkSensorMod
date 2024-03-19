package com.klutzer.playersculksensor.block;

import com.klutzer.playersculksensor.PlayerSculkSensorMod;
import com.klutzer.playersculksensor.config.PlayerSculkSensorCommonConfig;
import com.klutzer.playersculksensor.item.AllItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AllBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PlayerSculkSensorMod.MOD_ID);

    public static final RegistryObject<Block> PLAYERSCULKSENSOR_BLOCK =
            registerBlock(
                    "playersculksensor_block",
                    () -> new PlayerSculkSensorBlock(
                            BlockBehaviour
                                    .Properties.copy(Blocks.SCULK_SENSOR), PlayerSculkSensorCommonConfig.PLAYER_SCULK_SENSOR_RANGE.get()),
                    CreativeModeTab.TAB_REDSTONE);


    private static <T extends Block> RegistryObject<T> registerBlock(
            String name,
            Supplier<T> block,
            CreativeModeTab tab)
    {

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(
            String name,
            RegistryObject<T> block,
            CreativeModeTab tab)
    {

        return AllItems.ITEMS.register(
                name,
                () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus event){
        BLOCKS.register(event);
    }
}
