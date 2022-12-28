package ro.iacobai.digger.blocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import ro.iacobai.digger.DIGGER;

public class Hopper {
    DIGGER digger = DIGGER.getPlugin();
    public ItemStack check_inventory_tools_durability(org.bukkit.block.Hopper hopper_data){
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
                }
                else {
                    meta.setDamage(meta.getDamage() + digger.getConfig().getInt("Damage_taken_item"));
                    tool.setItemMeta(meta);
                }
                return tool;
            }
        }
        return null;
    }
}
