package com.klutzer.playersculksensor.block;

import com.klutzer.playersculksensor.config.PlayerSculkSensorCommonConfig;
import com.klutzer.playersculksensor.entity.AllEntities;
import com.klutzer.playersculksensor.entity.PlayerSculkSensorBlockEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PlayerSculkSensorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final Object2IntMap<GameEvent> VIBRATION_STRENGTH_FOR_EVENT =
            Object2IntMaps.unmodifiable(PlayerSculkSensorCommonConfig.convertConfigToGameEvents());



    public static final EnumProperty<SculkSensorPhase> PHASE;
    public static final IntegerProperty POWER;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape SHAPE;
    private final int listenerRange;

    public PlayerSculkSensorBlock(BlockBehaviour.Properties pProperties, int pListenerRange) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PHASE, SculkSensorPhase.INACTIVE).setValue(POWER, 0).setValue(WATERLOGGED, false));
        this.listenerRange = pListenerRange;
    }

    public int getListenerRange() {
        return this.listenerRange;
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos $$1 = pContext.getClickedPos();
        FluidState $$2 = pContext.getLevel().getFluidState($$1);
        return (BlockState)this.defaultBlockState().setValue(WATERLOGGED, $$2.getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState pState) {
        return (Boolean)pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {

        if (getPhase(pState) != SculkSensorPhase.ACTIVE) {
            if (getPhase(pState) == SculkSensorPhase.COOLDOWN) {
                pLevel.setBlock(pPos, (BlockState)pState.setValue(PHASE, SculkSensorPhase.INACTIVE), 3);
            }

        } else {
            deactivate(pLevel, pPos, pState);
        }
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pLevel.isClientSide() && !pState.is(pOldState.getBlock())) {
            if ((Integer)pState.getValue(POWER) > 0 && !pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
                pLevel.setBlock(pPos, (BlockState)pState.setValue(POWER, 0), 18);
            }

            pLevel.scheduleTick(new BlockPos(pPos), pState.getBlock(), 1);
        }
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            if (getPhase(pState) == SculkSensorPhase.ACTIVE) {
                updateNeighbours(pLevel, pPos);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if ((Boolean)pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    private static void updateNeighbours(Level pLevel, BlockPos pPos) {
        pLevel.updateNeighborsAt(pPos, Blocks.SCULK_SENSOR);
        pLevel.updateNeighborsAt(pPos.relative(Direction.UP.getOpposite()), Blocks.SCULK_SENSOR);
    }

    @javax.annotation.Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlayerSculkSensorBlockEntity(pPos, pState);
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> GameEventListener getListener(Level pLevel, T pBlockEntity) {
        return pBlockEntity instanceof PlayerSculkSensorBlockEntity ? ((PlayerSculkSensorBlockEntity)pBlockEntity).getListener() : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, AllEntities.PLAYERSCULKSENSOR_ENTITY.get(),
                PlayerSculkSensorBlockEntity::tick);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        return (Integer)pState.getValue(POWER);
    }

    public static SculkSensorPhase getPhase(BlockState pState) {
        return (SculkSensorPhase)pState.getValue(PHASE);
    }

    public static boolean canActivate(BlockState pState) {
        return getPhase(pState) == SculkSensorPhase.INACTIVE;
    }

    public static void deactivate(Level pLevel, BlockPos pPos, BlockState pState) {
        pLevel.setBlock(pPos, (BlockState)((BlockState)pState.setValue(PHASE, SculkSensorPhase.COOLDOWN)).setValue(POWER, 0), 3);
        pLevel.scheduleTick(new BlockPos(pPos), pState.getBlock(), 1);
        if (!(Boolean)pState.getValue(WATERLOGGED)) {
            pLevel.playSound((Player)null, pPos, SoundEvents.SCULK_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, pLevel.random.nextFloat() * 0.2F + 0.8F);
        }

        updateNeighbours(pLevel, pPos);
    }

    public static void activate(Level pLevel, BlockPos pPos, BlockState pState, int pPower) {
        pLevel.setBlock(pPos, (BlockState)((BlockState)pState.setValue(PHASE, SculkSensorPhase.ACTIVE)).setValue(POWER, pPower), 3);
        pLevel.scheduleTick(new BlockPos(pPos), pState.getBlock(), 40);
        updateNeighbours(pLevel, pPos);
        if (!(Boolean)pState.getValue(WATERLOGGED)) {
            pLevel.playSound((Player)null, (double)pPos.getX() + 0.5, (double)pPos.getY() + 0.5, (double)pPos.getZ() + 0.5, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, pLevel.random.nextFloat() * 0.2F + 0.8F);
        }

    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (getPhase(pState) == SculkSensorPhase.ACTIVE) {
            Direction $$4 = Direction.getRandom(pRandom);
            if ($$4 != Direction.UP && $$4 != Direction.DOWN) {
                double $$5 = (double)pPos.getX() + 0.5 + ($$4.getStepX() == 0 ? 0.5 - pRandom.nextDouble() : (double)$$4.getStepX() * 0.6);
                double $$6 = (double)pPos.getY() + 0.25;
                double $$7 = (double)pPos.getZ() + 0.5 + ($$4.getStepZ() == 0 ? 0.5 - pRandom.nextDouble() : (double)$$4.getStepZ() * 0.6);
                double $$8 = (double)pRandom.nextFloat() * 0.04;
                pLevel.addParticle(DustColorTransitionOptions.SCULK_TO_REDSTONE, $$5, $$6, $$7, 0.0, $$8, 0.0);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{PHASE, POWER, WATERLOGGED});
    }

    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity $$3 = pLevel.getBlockEntity(pPos);
        if ($$3 instanceof PlayerSculkSensorBlockEntity $$4) {
            return getPhase(pState) == SculkSensorPhase.ACTIVE ? $$4.getLastVibrationFrequency() : 0;
        } else {
            return 0;
        }
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    static {
        PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
        POWER = BlockStateProperties.POWER;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }
}
