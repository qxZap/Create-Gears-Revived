package com.kotakotik.creategears.regitration;

import com.kotakotik.creategears.Gears;
import com.kotakotik.creategears.tiles.GearTile;
import com.kotakotik.creategears.util.Registration;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.relays.encased.EncasedShaftTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.SplitShaftInstance;
import com.simibubi.create.content.contraptions.relays.encased.SplitShaftRenderer;
import com.simibubi.create.content.contraptions.relays.gearbox.GearshiftTileEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.repack.registrate.util.entry.TileEntityEntry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= Gears.modid, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class GearsTiles extends Registration {
    public static TileEntityEntry<GearTile> GEAR;
    public static TileEntityEntry<EncasedShaftTileEntity> FULLY_ENCASED_BELT;
    public static TileEntityEntry<GearshiftTileEntity> SIMPLE_GEARSHIFT;

    public GearsTiles(CreateRegistrate r) {
        super(r);
    }

    @Override
    public void register() {
        GEAR = r.tileEntity("gear", GearTile::new)
                .instance(() -> SingleRotatingInstance::new)
                .validBlocks(GearsBlocks.GEAR, GearsBlocks.LARGE_GEAR)
                .renderer(() -> KineticTileEntityRenderer::new)
                .register();

        FULLY_ENCASED_BELT = r.tileEntity("fully_encased_shaft", EncasedShaftTileEntity::new)
                .validBlock(GearsBlocks.FULLY_ENCASED_CHAIN_DRIVE)
                .renderer(() -> KineticTileEntityRenderer::new)
                .register();

        SIMPLE_GEARSHIFT = r.tileEntity("simple_gearshift", GearshiftTileEntity::new)
                .instance(() -> SplitShaftInstance::new)
                .validBlock(GearsBlocks.SIMPLE_GEARSHIFT)
                .renderer(() -> SplitShaftRenderer::new)
                .register();
    }
}
