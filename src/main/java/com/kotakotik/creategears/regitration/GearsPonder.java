package com.kotakotik.creategears.regitration;

import com.kotakotik.creategears.Gears;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.content.ChainDriveScenes;
import com.simibubi.create.foundation.ponder.content.KineticsScenes;
import com.simibubi.create.foundation.ponder.content.PonderTag;

public class GearsPonder {
    public static void register() {
        PonderRegistry.startRegistration(Gears.modid);
        // i gotta go sleep
        // but what if i forget what i was doing when i wake up
        // nah better finish this


        // please kill me

        // restarting minecraft is so tedious
        // i just wanna sleep

        PonderRegistry.forComponents(GearsBlocks.GEAR, GearsBlocks.LARGE_GEAR)
                .addStoryBoard("cog/small", KineticsScenes::cogAsRelay)
                .addStoryBoard("cog/large", KineticsScenes::largeCogAsRelay);

        PonderRegistry.forComponents(GearsBlocks.FULLY_ENCASED_CHAIN_DRIVE)
                .addStoryBoard("chain_drive/relay", ChainDriveScenes::chainDriveAsRelay);

        PonderRegistry.forComponents(GearsBlocks.SIMPLE_GEARSHIFT)
                .addStoryBoard("gearshift", KineticsScenes::gearshift);

        PonderRegistry.TAGS.forTag(PonderTag.KINETIC_RELAYS)
                .add(GearsBlocks.GEAR)
                .add(GearsBlocks.LARGE_GEAR)
                .add(GearsBlocks.FULLY_ENCASED_CHAIN_DRIVE)
                .add(GearsBlocks.SIMPLE_GEARSHIFT);

        PonderRegistry.endRegistration();
    }
}
