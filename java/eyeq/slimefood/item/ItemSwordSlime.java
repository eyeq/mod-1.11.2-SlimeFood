package eyeq.slimefood.item;

import com.google.common.collect.Multimap;
import eyeq.util.item.ItemUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSwordSlime extends ItemSword {
    public ItemSwordSlime() {
        super(ItemUtils.TOOL_MATERIAL_DUMMY);
        this.setMaxDamage(160);
    }

    @Override
    public float getDamageVsEntity() {
        float f = itemRand.nextFloat();
        if(f < 0.3) {
            return 2.0F;
        }
        if(f < 0.6) {
            return 5.0F;
        }
        if(f < 0.9) {
            return 8.0F;
        }
        return 14.0F;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker) {
        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1400, 2));
        return super.hitEntity(itemStack, target, attacker);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.onUpdate(itemStack, world, entity, itemSlot, isSelected);
        if(!isSelected) {
            return;
        }
        if(!(entity instanceof EntityLivingBase)) {
            return;
        }
        if(((EntityLivingBase) entity).isActiveItemStackBlocking()) {
            if(!entity.onGround) {
                double r = entity.motionX * entity.motionX + entity.motionZ * entity.motionZ;
                if(r < 0.5) {
                    entity.motionX *= 2;
                    entity.motionZ *= 2;
                }
            } else {
                entity.motionX *= 0.1;
                entity.motionZ *= 0.1;
            }
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if(equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.0F + this.getDamageVsEntity(), 0));
        }
        return multimap;
    }
}
