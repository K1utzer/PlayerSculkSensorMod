package com.klutzer.playersculksensor.config;

import com.klutzer.playersculksensor.PlayerSculkSensorMod;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PlayerSculkSensorCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_SCULK_SENSOR_RANGE;

    public static final ForgeConfigSpec.ConfigValue<String> PLAYER_EVENTS;

    public static final String DEFAULT_EVENTS ="STEP, MINECART_MOVING, BLOCK_PLACE, BLOCK_DESTROY, FLUID_PLACE" +
            ", HIT_GROUND, SWIM, EAT, ELYTRA_FREE_FALL, SPLASH, RING_BELL, PROJECTILE_SHOOT, DRINKING_FINISH" +
            ", PRIME_FUSE, PROJECTILE_LAND, MOB_INTERACT, ENTITY_DAMAGED, EQUIP, SHEAR, BLOCK_CLOSE, BLOCK_UNSWITCH" +
            ", BLOCK_UNPRESS, BLOCK_DETACH, BLOCK_OPEN, BLOCK_SWITCH, BLOCK_PRESS, BLOCK_ATTACH, ENTITY_PLACE" +
            ", ENTITY_KILLED, FLUID_PICKUP, FISHING_ROD_REEL_IN, CONTAINER_CLOSE, CONTAINER_OPEN, FISHING_ROD_CAST" +
            ", EXPLODE, LIGHTNING_STRIKE, SHULKER_OPEN";


    static {
        BUILDER.push("Config for Player-Sculksensor Mod");

        PLAYER_SCULK_SENSOR_RANGE = BUILDER
                .comment("Detection Range for the Player-Sculksensor.")
                .defineInRange("Range", 7, 1, 15);

        PLAYER_EVENTS = BUILDER
                .comment("List of player events to detect.",
                        "This list defines which player-related events the Player Sculk Sensor will react to.",
                        "Events include:",
                        "  STEP: Triggered when a player steps on a block.",
                        "  MINECART_MOVING: Triggered when a player moves a minecart.",
                        "  BLOCK_PLACE: Triggered when a player places a block.",
                        "  BLOCK_DESTROY: Triggered when a player destroys a block.",
                        "  FLUID_PLACE: Triggered when a player places a fluid.",
                        "  HIT_GROUND: Triggered when a player hits the ground hard (from a fall).",
                        "  SWIM: Triggered when a player swims in water or lava.",
                        "  EAT: Triggered when a player eats something.",
                        "  ELYTRA_FREE_FALL: Triggered when a player free falls with an Elytra.",
                        "  SPLASH: Triggered when a player splashes in water.",
                        "  RING_BELL: Triggered when a player rings a bell.",
                        "  PROJECTILE_SHOOT: Triggered when a player shoots a projectile.",
                        "  DRINKING_FINISH: Triggered when a player finishes drinking a potion.",
                        "  PRIME_FUSE: Triggered when a player primes TNT.",
                        "  PROJECTILE_LAND: Triggered when a player's thrown projectile lands.",
                        "  MOB_INTERACT: Triggered when a player interacts with a mob.",
                        "  ENTITY_DAMAGED: Triggered when a player damages an entity.",
                        "  EQUIP: Triggered when a player equips an item.",
                        "  SHEAR: Triggered when a player shears a sheep or similar.",
                        "  BLOCK_CLOSE, BLOCK_UNSWITCH, BLOCK_UNPRESS, BLOCK_DETACH, BLOCK_OPEN,",
                        "  BLOCK_SWITCH, BLOCK_PRESS, BLOCK_ATTACH: Various block interaction events.",
                        "  ENTITY_PLACE: Triggered when a player places an entity like a boat or minecart.",
                        "  ENTITY_KILLED: Triggered when a player kills any entity.",
                        "  FLUID_PICKUP: Triggered when a player picks up fluid with a bucket.",
                        "  FISHING_ROD_REEL_IN: Triggered when a player reels in a fishing rod.",
                        "  CONTAINER_CLOSE, CONTAINER_OPEN: Triggered when a player opens or closes a container.",
                        "  FISHING_ROD_CAST: Triggered when a player casts a fishing rod.",
                        "  EXPLODE: Triggered when an explosion occurs near a player.",
                        "  LIGHTNING_STRIKE: Triggered when lightning strikes near a player.",
                        "  SHULKER_OPEN: Triggered when a player opens a Shulker Box.")
                .define("PlayerEvents", DEFAULT_EVENTS);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
    public static Object2IntMap<GameEvent> convertConfigToGameEvents() {
        Map<String, GameEvent> nameToEventMap = Map.ofEntries(
                Map.entry("STEP", GameEvent.STEP),
                Map.entry("MINECART_MOVING", GameEvent.MINECART_MOVING),
                Map.entry("BLOCK_PLACE", GameEvent.BLOCK_PLACE),
                Map.entry("BLOCK_DESTROY", GameEvent.BLOCK_DESTROY),
                Map.entry("FLUID_PLACE", GameEvent.FLUID_PLACE),
                Map.entry("HIT_GROUND", GameEvent.HIT_GROUND),
                Map.entry("SWIM", GameEvent.SWIM),
                Map.entry("EAT", GameEvent.EAT),
                Map.entry("ELYTRA_FREE_FALL", GameEvent.ELYTRA_FREE_FALL),
                Map.entry("SPLASH", GameEvent.SPLASH),
                Map.entry("RING_BELL", GameEvent.RING_BELL),
                Map.entry("PROJECTILE_SHOOT", GameEvent.PROJECTILE_SHOOT),
                Map.entry("DRINKING_FINISH", GameEvent.DRINKING_FINISH),
                Map.entry("PRIME_FUSE", GameEvent.PRIME_FUSE),
                Map.entry("PROJECTILE_LAND", GameEvent.PROJECTILE_LAND),
                Map.entry("MOB_INTERACT", GameEvent.MOB_INTERACT),
                Map.entry("ENTITY_DAMAGED", GameEvent.ENTITY_DAMAGED),
                Map.entry("EQUIP", GameEvent.EQUIP),
                Map.entry("SHEAR", GameEvent.SHEAR),
                Map.entry("BLOCK_CLOSE", GameEvent.BLOCK_CLOSE),
                Map.entry("BLOCK_UNSWITCH", GameEvent.BLOCK_UNSWITCH),
                Map.entry("BLOCK_UNPRESS", GameEvent.BLOCK_UNPRESS),
                Map.entry("BLOCK_DETACH", GameEvent.BLOCK_DETACH),
                Map.entry("BLOCK_OPEN", GameEvent.BLOCK_OPEN),
                Map.entry("BLOCK_SWITCH", GameEvent.BLOCK_SWITCH),
                Map.entry("BLOCK_PRESS", GameEvent.BLOCK_PRESS),
                Map.entry("BLOCK_ATTACH", GameEvent.BLOCK_ATTACH),
                Map.entry("ENTITY_PLACE", GameEvent.ENTITY_PLACE),
                Map.entry("ENTITY_KILLED", GameEvent.ENTITY_KILLED),
                Map.entry("FLUID_PICKUP", GameEvent.FLUID_PICKUP),
                Map.entry("FISHING_ROD_REEL_IN", GameEvent.FISHING_ROD_REEL_IN),
                Map.entry("CONTAINER_CLOSE", GameEvent.CONTAINER_CLOSE),
                Map.entry("CONTAINER_OPEN", GameEvent.CONTAINER_OPEN),
                Map.entry("FISHING_ROD_CAST", GameEvent.FISHING_ROD_CAST),
                Map.entry("EXPLODE", GameEvent.EXPLODE),
                Map.entry("LIGHTNING_STRIKE", GameEvent.LIGHTNING_STRIKE),
                Map.entry("SHULKER_OPEN", GameEvent.SHULKER_OPEN)
        );

        Object2IntMap<GameEvent> allowedEvents = new Object2IntOpenHashMap<>();
        String[] configEvents = PLAYER_EVENTS.get().split(", ");
        for (String eventName : configEvents) {
            GameEvent event = nameToEventMap.get(eventName);
            PlayerSculkSensorMod.getLogger().debug(eventName);
            if (event != null) {
                int eventStrength = getPredefinedEventStrength(eventName);
                allowedEvents.put(event, eventStrength);
            }
        }
        return allowedEvents;

    }

    private static int getPredefinedEventStrength(String eventName) {
        return switch (eventName) {
            case "STEP" -> 1;
            case "FLAP" -> 2;
            case "SWIM" -> 3;
            case "ELYTRA_FREE_FALL" -> 4;
            case "HIT_GROUND" -> 5;
            case "SPLASH", "WOLF_SHAKING", "MINECART_MOVING", "RING_BELL", "BLOCK_CHANGE" -> 6;
            case "PROJECTILE_SHOOT", "DRINKING_FINISH", "PRIME_FUSE" -> 7;
            case "PROJECTILE_LAND", "EAT", "MOB_INTERACT", "ENTITY_DAMAGED" -> 8;
            case "EQUIP", "SHEAR", "RAVAGER_ROAR" -> 9;
            case "BLOCK_CLOSE", "BLOCK_UNSWITCH", "BLOCK_UNPRESS", "BLOCK_DETACH", "DISPENSE_FAIL" -> 10;
            case "BLOCK_OPEN", "BLOCK_SWITCH", "BLOCK_PRESS", "BLOCK_ATTACH" -> 11;
            case "ENTITY_PLACE", "BLOCK_PLACE", "FLUID_PLACE" -> 12;
            case "ENTITY_KILLED", "BLOCK_DESTROY", "FLUID_PICKUP" -> 13;
            case "FISHING_ROD_REEL_IN", "CONTAINER_CLOSE", "PISTON_CONTRACT", "SHULKER_CLOSE" -> 14;
            case "PISTON_EXTEND", "CONTAINER_OPEN", "FISHING_ROD_CAST", "EXPLODE", "LIGHTNING_STRIKE", "SHULKER_OPEN" ->
                    15;
            default -> 0;
        };
    }



}

