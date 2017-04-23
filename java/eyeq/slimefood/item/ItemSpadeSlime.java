package eyeq.slimefood.item;

import eyeq.util.item.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSpadeSlime extends ItemSpade {
    public ItemSpadeSlime() {
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
        if(block == Blocks.SAND) {
            Block.spawnAsEntity(world, pos, new ItemStack(Blocks.CLAY));
            world.setBlockToAir(pos);
        }
        return true;
    }
}
