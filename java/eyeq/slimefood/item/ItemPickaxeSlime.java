package eyeq.slimefood.item;

import eyeq.util.item.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemPickaxeSlime extends ItemPickaxe {
    private static final List<IBlockState> list = new ArrayList<>();

    static {
        list.add(Blocks.COBBLESTONE.getDefaultState());
        list.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE));
        list.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE));
        list.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE));
        list.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
    }

    public ItemPickaxeSlime() {
        super(ItemUtils.TOOL_MATERIAL_DUMMY);
        this.setMaxDamage(160);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        super.onBlockDestroyed(itemStack, world, state, pos, entity);
        if(world.isRemote) {
            return true;
        }
        if(isStone(state)) {
            state = getRandomStone(itemRand);
            Block block = state.getBlock();

            Block.spawnAsEntity(world, pos, new ItemStack(block, 1, block.getMetaFromState(state)));
            world.setBlockToAir(pos);
        }
        return true;
    }

    private static boolean isStone(IBlockState state) {
        Block block = state.getBlock();
        if(block == Blocks.COBBLESTONE) {
            return true;
        }
        if(block == Blocks.STONE) {
            BlockStone.EnumType type = state.getValue(BlockStone.VARIANT);
            return type == BlockStone.EnumType.STONE || type == BlockStone.EnumType.GRANITE || type == BlockStone.EnumType.DIORITE || type == BlockStone.EnumType.ANDESITE;
        }
        return false;
    }

    public static IBlockState getRandomStone(Random rand) {
        return list.get(rand.nextInt(list.size()));
    }
}
