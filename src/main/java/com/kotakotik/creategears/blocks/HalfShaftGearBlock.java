package com.kotakotik.creategears.blocks;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.content.contraptions.relays.advanced.SpeedControllerBlock;
import com.simibubi.create.content.contraptions.relays.elementary.ICogWheel;
import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class HalfShaftGearBlock extends GearBlock {
    public VoxelShape shape = cuboid(2.0D, 6.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    public VoxelShaper shaper = shape(shape).add(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D).forAxis();

    public VoxelShape shapeLarge = cuboid(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public VoxelShaper shaperLarge = shape(shapeLarge).add(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D).forAxis();

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
        return super.getShape(state, worldIn, pos, context);
//        return (isLargeCog() ? shaperLarge : shaper).get(state.get(BlockStateProperties.FACING));
    }

    public Direction rotateFaceTo(Direction.Axis axis, Direction dir) {
        // TODO: this sucks
        if(axis == dir.getAxis()) return dir;
        switch (axis) {
            case X: return Direction.NORTH;
            case Y: return Direction.UP;
            case Z: return Direction.EAST;
        }
        return dir;
    }

    public Direction getDirectionForPlacement(BlockItemUseContext context) {
        if (context.getPlayer() != null && context.getPlayer().isSneaking()) {
            return context.getFace();
        } else {
            World world = context.getWorld();
            BlockState stateBelow = world.getBlockState(context.getPos().down());
            Direction f = context.getFace().getOpposite();
            if (AllBlocks.ROTATION_SPEED_CONTROLLER.has(stateBelow) && this.isLargeCog()) {
                return rotateFaceTo(stateBelow.get(SpeedControllerBlock.HORIZONTAL_AXIS), f);
            } else {
                BlockPos placedOnPos = context.getPos().offset(context.getFace().getOpposite());
                BlockState placedAgainst = world.getBlockState(placedOnPos);
                Block block = placedAgainst.getBlock();
                if (ICogWheel.isSmallCog(placedAgainst)) {
                    return rotateFaceTo(((IRotate)block).getRotationAxis(placedAgainst), f);
                } else {
                    Direction.Axis preferredAxis = getPreferredAxis(context);
                    return rotateFaceTo(preferredAxis != null ? preferredAxis : context.getFace().getAxis(), f);
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getStateForPlacement(context).with(BlockStateProperties.FACING, this.getDirectionForPlacement(context));
    }
}
