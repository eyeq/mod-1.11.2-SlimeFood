package eyeq.slimefood.item;

import eyeq.slimefood.SlimeFood;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSlimeBowl extends Item {
    public ItemSlimeBowl() {
        this.setMaxDamage(900);
        this.setNoRepair();
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(world.isRemote) {
            return;
        }
        itemStack.setItemDamage(itemStack.getItemDamage() + 1);
        if(itemStack.getMaxDamage() < itemStack.getItemDamage()) {
            entity.replaceItemInInventory(itemSlot, new ItemStack(SlimeFood.slimeStew));
        }
    }
}
