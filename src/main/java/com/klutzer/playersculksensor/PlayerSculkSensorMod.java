package com.klutzer.playersculksensor;

import com.klutzer.playersculksensor.block.AllBlocks;
import com.klutzer.playersculksensor.entity.AllEntities;
import com.klutzer.playersculksensor.item.AllItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PlayerSculkSensorMod.MOD_ID)
public class PlayerSculkSensorMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "playersculksensormod";

    public PlayerSculkSensorMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AllItems.register(eventBus);
        AllBlocks.register(eventBus);
        AllEntities.register(eventBus);

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(AllBlocks.PLAYERSCULKSENSOR_BLOCK.get(), RenderType.translucent());
    }

}
