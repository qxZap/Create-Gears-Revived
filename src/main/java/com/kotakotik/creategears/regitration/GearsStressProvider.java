package com.kotakotik.creategears.regitration;

import com.simibubi.create.foundation.block.BlockStressValues;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.repack.registrate.util.entry.BlockEntry;
import com.simibubi.create.repack.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.function.Supplier;

public class GearsStressProvider implements BlockStressValues.IStressValueProvider {
    protected static HashMap<ResourceLocation, Supplier<Double>> CAPACITIES = new HashMap<>();
    protected static HashMap<ResourceLocation, Supplier<Double>> IMPACTS = new HashMap<>();

    public static <B extends Block, P> NonNullUnaryOperator<com.simibubi.create.repack.registrate.builders.BlockBuilder<B, P>> registerCapacityCopying(BlockEntry<? extends Block> toCopy) {
        return registerCapacityCopying(toCopy.getId(), toCopy);
    }

    public static <B extends Block, P> NonNullUnaryOperator<com.simibubi.create.repack.registrate.builders.BlockBuilder<B, P>>
    registerCapacityCopying(ResourceLocation id, Supplier<? extends Block> sup) {
        return b -> {
            CAPACITIES.put(id, () -> AllConfigs.SERVER.kinetics.stressValues.getCapacity(sup.get()));
            return b;
        };
    }

    public static <B extends Block, P> NonNullUnaryOperator<com.simibubi.create.repack.registrate.builders.BlockBuilder<B, P>> registerImpactCopying(BlockEntry<? extends Block> toCopy) {
        return registerImpactCopying(toCopy.getId(), toCopy);
    }

    public static <B extends Block, P> NonNullUnaryOperator<com.simibubi.create.repack.registrate.builders.BlockBuilder<B, P>>
    registerImpactCopying(ResourceLocation id, Supplier<? extends Block> sup) {
        return b -> {
            IMPACTS.put(id, () -> AllConfigs.SERVER.kinetics.stressValues.getImpact(sup.get()));
            return b;
        };
    }

    @Override
    public double getImpact(Block block) {
        return IMPACTS.getOrDefault(block.getRegistryName(), () -> 0d).get();
    }

    @Override
    public double getCapacity(Block block) {
        return CAPACITIES.getOrDefault(block.getRegistryName(), () -> 0d).get();
    }

    @Override
    public boolean hasImpact(Block block) {
        return IMPACTS.containsKey(block.getRegistryName());
    }

    @Override
    public boolean hasCapacity(Block block) {
        return CAPACITIES.containsKey(block.getRegistryName());
    }
}
