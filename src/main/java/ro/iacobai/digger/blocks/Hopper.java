package ro.iacobai.digger.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import ro.iacobai.digger.DIGGER;

public class Hopper {
    DIGGER digger = DIGGER.getPlugin();
    public ItemStack check_inventory_tools_durability(org.bukkit.block.Hopper hopper_data, int use_break){
        for(int i = 0;i<5;i++)
        {
            ItemStack tool = hopper_data.getInventory().getItem(i);
            if(tool != null)
            {
                Damageable meta = (Damageable) tool.getItemMeta();
                Material material = tool.getData().getItemType();
                if(material.getCreativeCategory().name() != "TOOLS")
                {
                    return null;
                }
                if(material.getMaxDurability()  < digger.getConfig().getInt("Damage_taken_item") + meta.getDamage())
                {
                    if(use_break == 1){
                        hopper_data.getInventory().remove(tool);
                    }
                    else {
                        return null;
                    }
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
    public ItemStack check_most_efficient(org.bukkit.block.Hopper hopper_data, Location current_pos, int use_break){
        for(int i = 0;i<5;i++)
        {
            ItemStack tool = hopper_data.getInventory().getItem(i);
            if(tool != null)
            {
                if(current_pos.getBlock().isPreferredTool(tool))
                {
                    Damageable meta = (Damageable) tool.getItemMeta();
                    Material material = tool.getData().getItemType();
                    if(material.getCreativeCategory().name() != "TOOLS")
                    {

                    }
                    else if(material.getMaxDurability()  < digger.getConfig().getInt("Damage_taken_item") + meta.getDamage())
                    {
                        if(use_break == 1){
                            hopper_data.getInventory().remove(tool);
                            return tool;
                        }
                    }
                    else {
                        meta.setDamage(meta.getDamage() + digger.getConfig().getInt("Damage_taken_item"));
                        tool.setItemMeta(meta);
                        return tool;
                    }
                }
            }
        }
        return null;
    }
}
