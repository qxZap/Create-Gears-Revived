package com.kotakotik.creategears.tiles;

import com.simibubi.create.content.contraptions.relays.elementary.SimpleKineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.tileentity.TileEntityType;

import java.util.List;

public class GearTile extends SimpleKineticTileEntity {
    public GearTile(TileEntityType<? extends SimpleKineticTileEntity> type) {
        super(type);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        // dont add bracket behavior
    }
}
