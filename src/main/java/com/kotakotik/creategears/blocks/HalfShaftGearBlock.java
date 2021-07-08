package com.kotakotik.creategears.blocks;

import com.kotakotik.creategears.util.ShapeBuilder;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class HalfShaftGearBlock extends GearBlock {
    public VoxelShape shape = cuboid(2.0D, 6.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    public VoxelShaper shaper = new ShapeBuilder(shape).add(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D).forAxis();

    public VoxelShape shapeLarge = cuboid(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public VoxelShaper shaperLarge = new ShapeBuilder(shapeLarge).add(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D).forAxis();

    public HalfShaftGearBlock(boolean large, Properties p_i48440_1_) {
        super(large, p_i48440_1_);
        setDefaultState(this.getDefaultState().with(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.get(BlockStateProperties.FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return (isLargeCog() ? shaperLarge : shaper).get(state.get(BlockStateProperties.FACING));
    }
}
