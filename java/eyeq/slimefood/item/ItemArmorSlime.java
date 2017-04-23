package eyeq.slimefood.item;

import com.google.common.collect.Multimap;
import eyeq.slimefood.SlimeFood;
import eyeq.util.UItemArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Collection;

public class ItemArmorSlime extends UItemArmor {
    public static final ArmorMaterial material = EnumHelper.addArmorMaterial("slime", "", 10, new int[]{2, 4, 6, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);

    private static final ResourceLocation armorName = new ResourceLocation(SlimeFood.MOD_ID, "slime");

    public ItemArmorSlime(EntityEquipmentSlot equipmentSlot) {
        super(material, 0, equipmentSlot, armorName);
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(type == null) {
            return super.getArmorTexture(itemStack, entity, slot, type);
        }
        return null;
    }

    private boolean isNextGround(World world, EntityPlayer player) {
        BlockPos pos = player.getPosition().add(0.5, 0, 0.5);
        for(int i = 0; i < (int) (-player.motionY + 2.5); i++) {
            if(!world.isAirBlock(pos.down(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onArmorTickHead(World world, EntityPlayer player, ItemStack itemStack) {
        if(player.fallDistance > 0.0F && isNextGround(world, player)) {
            for(Entity entity : player.getPassengers()) {
                entity.motionY = Math.abs(player.motionY) * -1;
                entity.dismountRidingEntity();
            }
        }}

    @Override
    protected void onArmorTickLegs(World world, EntityPlayer player, ItemStack itemStack) {
        player.fallDistance /= 2;
    }

    @Override
    protected void onArmorTickFeet(World world, EntityPlayer player, ItemStack itemStack) {
        if(!player.isRiding() && !player.isSneaking() && player.fallDistance > 0.0F && isNextGround(world, player)) {
            player.motionY *= -1;
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if(equipmentSlot == this.armorType && equipmentSlot == EntityEquipmentSlot.CHEST && itemRand.nextInt(20) == 0) {
            Collection<AttributeModifier> old = multimap.removeAll(SharedMonsterAttributes.ARMOR.getName());
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(old.iterator().next().getID(), "Armor modifier", 99, 0));
        }
        return multimap;
    }
}
