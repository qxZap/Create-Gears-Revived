package com.kotakotik.creategears.blocks;

import com.simibubi.create.foundation.utility.VoxelShaper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class HalfShaftGearBlock extends GearBlock {
    public VoxelShape shape = cuboid(2.0D, 6.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    public VoxelShaper shaper = shape(shape).add(6.0D, 8, 6.0D, 10.0D, 16, 10.0D).forDirectional();

    public VoxelShape shapeLarge = cuboid(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);
    public VoxelShaper shaperLarge = shape(shapeLarge).add(6.0D, 8, 6.0D, 10.0D, 16.0D, 10.0D).forDirectional();

    public static final BooleanProperty AXIS_DIRECTION = BooleanProperty.create("axis_direction");

    public HalfShaftGearBlock(boolean large, Properties p_i48440_1_) {
        super(large, p_i48440_1_);
        registerDefaultState(this.defaultBlockState().setValue(AXIS_DIRECTION, axisDirectionToBool(Direction.AxisDirection.POSITIVE)));
    }

    public static boolean axisDirectionToBool(Direction.AxisDirection dir) {
        return dir == Direction.AxisDirection.POSITIVE;
    }

    public static Direction.AxisDirection boolToAxisDirection(boolean bool) {
        return bool ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS_DIRECTION);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public boolean hasShaftTowards(IWorldReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(AXIS) && face.getAxisDirection() == boolToAxisDirection(state.getValue(AXIS_DIRECTION));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction dir = Direction.fromAxisAndDirection(state.getValue(AXIS), boolToAxisDirection(state.getValue(AXIS_DIRECTION)));
        return isLargeCog() ? shaperLarge.get(dir) : shaper.get(dir);
//        return (isLargeCog() ? shaperLarge : shaper).get(state.get(BlockStateProperties.FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        // shut up
        Direction dir = context.getClickedFace().getOpposite();
        boolean b = axisDirectionToBool(dir.getAxisDirection());
        Direction.Axis a = dir.getAxis();
        return super.getStateForPlacement(context).setValue(AXIS_DIRECTION, context.getPlayer().isShiftKeyDown() != b).setValue(AXIS, a);
    }
}
