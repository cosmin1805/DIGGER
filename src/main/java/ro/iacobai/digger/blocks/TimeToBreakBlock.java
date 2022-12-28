package ro.iacobai.digger.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import ro.iacobai.digger.DIGGER;

public class TimeToBreakBlock {
    DIGGER digger = DIGGER.getPlugin();
    public int check_inventory(Hopper hopper_data , Location current_pos,int use_chest,Location chest_pos){
        for(int i = 0;i<5;i++)
        {
            ItemStack tool = hopper_data.getInventory().getItem(i);
            if(tool != null)
            {

                Damageable meta = (Damageable) tool.getItemMeta();
                Material material = tool.getData().getItemType();
                if(material.getMaxDurability()  < digger.getConfig().getInt("Damage_taken_item") + meta.getDamage())
                {
                    hopper_data.getInventory().remove(tool);
                    return true;
                }
                else {
                    meta.setDamage(meta.getDamage() + digger.getConfig().getInt("Damage_taken_item"));
                    tool.setItemMeta(meta);
                }
                return false;
            }
        }
        return true;
    }
}
