package eyeq.slimefood.item;

import eyeq.util.item.ItemUtils;
import eyeq.util.item.UItemAxe;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemAxeSlime extends UItemAxe {
    private static final List<IBlockState> list = new ArrayList<>();

    static {
        list.add(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK));
        list.add(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE));
        list.add(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH));
        list.add(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE));
        list.add(Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA));
        list.add(Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK));
    }

    public ItemAxeSlime() {
        super(ItemUtils.TOOL_MATERIAL_DUMMY);
        this.setMaxDamage(160);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        super.onBlockDestroyed(itemStack, world, state, pos, entity);
        if(world.isRemote) {
            return true;
        }
        Block block = state.getBlock();
        if(block == Blocks.LOG || block == Blocks.LOG2) {
            state = getRandomLog(itemRand);
            block = state.getBlock();

            Block.spawnAsEntity(world, pos, new ItemStack(block, 1, block.getMetaFromState(state)));
            world.setBlockToAir(pos);
        }
        return true;
    }

    public static IBlockState getRandomLog(Random rand) {
        return list.get(rand.nextInt(list.size()));
    }
}
