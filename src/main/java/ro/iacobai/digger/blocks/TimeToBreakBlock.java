package ro.iacobai.digger.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import ro.iacobai.digger.DIGGER;

public class TimeToBreakBlock {
    DIGGER digger = DIGGER.getPlugin();
    public int calculate(ItemStack tool, Location current_pos){
        float speed = current_pos.getBlock().getDestroySpeed(tool);
        Damageable meta = (Damageable) tool.getItemMeta();
        if(meta.hasEnchant((Enchantment.DIG_SPEED))){
            speed += meta.getEnchantLevel(Enchantment.DIG_SPEED) ^ 2 + 1;
        }
        float damage = speed / current_pos.getBlock().getBlockData().getMaterial().getHardness();
        if(current_pos.getBlock().isPreferredTool(tool))
        {
            damage/=30;
        }
        else {
            damage/=100;
        }
        if(damage > 1){
            return  0;
        }
        return  Math.round(1/damage);
    }
}
