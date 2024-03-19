package com.klutzer.playersculksensor.entity;

import com.klutzer.playersculksensor.block.PlayerSculkSensorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;

import javax.annotation.Nullable;

public class PlayerSculkSensorBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
    private final VibrationListener listener;
    private int lastVibrationFrequency;

    public PlayerSculkSensorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AllEntities.PLAYERSCULKSENSOR_ENTITY.get(), pWorldPosition, pBlockState);
        this.listener = new VibrationListener(new BlockPositionSource(this.worldPosition), ((PlayerSculkSensorBlock)pBlockState.getBlock()).getListenerRange(), this);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.lastVibrationFrequency = pTag.getInt("last_vibration_frequency");
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("last_vibration_frequency", this.lastVibrationFrequency);
    }

    public VibrationListener getListener() {
        return this.listener;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }

    public boolean shouldListen(Level level, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity) {
        return entity instanceof Player && PlayerSculkSensorBlock.canActivate(this.getBlockState());
    }
    public static void tick(Level level, BlockPos pos, BlockState state, PlayerSculkSensorBlockEntity be) {
        be.listener.tick(level);
    }

    public void onSignalReceive(Level p_155638_, GameEventListener p_155639_, GameEvent p_155640_, int p_155641_) {
        BlockState $$4 = this.getBlockState();

        if (!p_155638_.isClientSide() && PlayerSculkSensorBlock.canActivate($$4)) {
            this.lastVibrationFrequency = PlayerSculkSensorBlock.VIBRATION_STRENGTH_FOR_EVENT.getInt(p_155640_);
            PlayerSculkSensorBlock.activate(p_155638_, this.worldPosition, $$4, getRedstoneStrengthForDistance(p_155641_, p_155639_.getListenerRadius()));
        }

    }

    public static int getRedstoneStrengthForDistance(int p_155651_, int p_155652_) {
        double $$2 = (double)p_155651_ / (double)p_155652_;
        return Math.max(1, 15 - Mth.floor($$2 * 15.0));
    }
}
